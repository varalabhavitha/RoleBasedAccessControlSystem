import { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar";
import Topbar from "../components/Topbar";
import { Search, Plus, Pencil, Trash2, X } from "lucide-react";
import "../css/Users.css";

import { getAllOrganizations } from "../api/organizationApi";
import { getAllRoles } from "../api/roleApi";
import {
  registerUser,
  getAllUsers,
  getUser,
  updateUser,
  deleteUser,
} from "../api/userApi";

function Users() {
  const [users, setUsers] = useState([]);
  const [organizations, setOrganizations] = useState([]);
  const [roles, setRoles] = useState([]);

  const [search, setSearch] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [editId, setEditId] = useState(null);

  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    mobile: "",
    roleId: "",
    organizationId: "",
  });

  useEffect(() => {
    fetchUsers();
    fetchOrganizations();
    fetchRoles();
  }, []);

  const fetchUsers = async () => {
    try {
      const data = await getAllUsers();
      setUsers(data || []);
    } catch (error) {
      console.error("Error fetching users:", error);
      alert("Failed to fetch users");
    }
  };

  const fetchOrganizations = async () => {
    try {
      const data = await getAllOrganizations();
      setOrganizations(data || []);
    } catch (error) {
      console.error("Error fetching organizations:", error);
      alert("Failed to fetch organizations");
    }
  };

  const fetchRoles = async () => {
    try {
      const data = await getAllRoles();
      const activeRoles = (data || []).filter(
        (role) => String(role.status).toUpperCase() !== "DELETED"
      );
      setRoles(activeRoles);
    } catch (error) {
      console.error("Error fetching roles:", error);
      alert("Failed to fetch roles");
    }
  };

  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const resetForm = () => {
    setFormData({
      firstName: "",
      lastName: "",
      email: "",
      mobile: "",
      roleId: "",
      organizationId: "",
    });
    setEditId(null);
  };

  const getOrganizationName = (organizationId) => {
    const org = organizations.find(
      (item) => String(item.orgId) === String(organizationId)
    );
    return org ? org.orgName : "";
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (
      !formData.firstName ||
      !formData.lastName ||
      !formData.email ||
      !formData.mobile ||
      !formData.roleId ||
      !formData.organizationId
    ) {
      alert("Please fill all required fields");
      return;
    }

    const payload = {
      firstName: formData.firstName,
      lastName: formData.lastName,
      username: formData.lastName,
      email: formData.email,
      mobile: formData.mobile,
      roleId: Number(formData.roleId),
      organizationId: Number(formData.organizationId),
    };

    try {
      if (editId) {
        await updateUser(editId, payload);
        alert("User updated successfully");
      } else {
        await registerUser(payload);
        alert("User created successfully");
      }

      await fetchUsers();
      resetForm();
      setShowModal(false);
    } catch (error) {
      console.error("Error saving user:", error);
      alert(error.response?.data || "Failed to save user");
    }
  };

  const handleEdit = async (id) => {
    try {
      const user = await getUserById(id);

      setFormData({
        firstName: user.firstName || "",
        lastName: user.lastName || user.username || "",
        email: user.email || "",
        mobile: user.mobile || "",
        roleId: user.roleId || "",
        organizationId: user.organizationId || "",
      });

      setEditId(user.id);
      setShowModal(true);
    } catch (error) {
      console.error("Error fetching user:", error);
      alert("Failed to fetch user");
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteUser(id);
      alert("User deleted successfully");
      await fetchUsers();
    } catch (error) {
      console.error("Error deleting user:", error);
      alert("Failed to delete user");
    }
  };

  const filteredUsers = users.filter((user) =>
    `${user.id} ${user.firstName || ""} ${user.lastName || ""} ${
      user.email
    } ${user.roleName || ""} ${user.organizationName || ""}`
      .toLowerCase()
      .includes(search.toLowerCase())
  );

  return (
    <div className="users-layout">
      <Sidebar />

      <div className="users-main">
        <Topbar title="Users" />

        <div className="users-content">
          <div className="users-toolbar">
            <div className="users-search">
              <Search size={22} />
              <input
                type="text"
                placeholder="Search users..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}
              />
            </div>

            <button
              className="users-add-btn"
              onClick={() => {
                resetForm();
                setShowModal(true);
              }}
            >
              <Plus size={20} />
              Add New User
            </button>
          </div>

          <div className="users-table-wrap">
            <table className="users-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Username</th>
                  <th>Email</th>
                  <th>Mobile</th>
                  <th>Role</th>
                  <th>Organization</th>
                  <th>Status</th>
                  <th>Created At</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>
                {filteredUsers.length > 0 ? (
                  filteredUsers.map((user) => (
                    <tr key={user.id}>
                      <td>{user.id}</td>
                      <td>{user.lastName || user.username || "-"}</td>
                      <td>{user.email}</td>
                      <td>{user.mobile || "-"}</td>
                      <td>{user.roleName || "-"}</td>
                      <td>
                        {user.organizationName ||
                          getOrganizationName(user.organizationId) ||
                          "-"}
                      </td>
                      <td>{user.status ? "Active" : "Inactive"}</td>
                      <td>
                        {user.createdAt
                          ? user.createdAt.replace("T", " ").slice(0, 19)
                          : "-"}
                      </td>
                      <td>
                        <div className="table-actions">
                          <Pencil
                            size={19}
                            className="edit-icon"
                            onClick={() => handleEdit(user.id)}
                          />
                          <Trash2
                            size={19}
                            className="delete-icon"
                            onClick={() => handleDelete(user.id)}
                          />
                        </div>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="9" className="no-data">
                      No users found
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>

        {showModal && (
          <div className="user-modal-overlay">
            <div className="user-modal">
              <div className="user-modal-header">
                <h2>{editId ? "Edit User" : "Add New User"}</h2>
                <button
                  className="close-btn"
                  onClick={() => {
                    setShowModal(false);
                    resetForm();
                  }}
                >
                  <X size={24} />
                </button>
              </div>

              <form className="user-form" onSubmit={handleSubmit}>
                <div className="form-grid">
                  <div>
                    <label>First Name</label>
                    <input
                      type="text"
                      name="firstName"
                      value={formData.firstName}
                      onChange={handleChange}
                    />
                  </div>

                  <div>
                    <label>Last Name</label>
                    <input
                      type="text"
                      name="lastName"
                      value={formData.lastName}
                      onChange={handleChange}
                    />
                  </div>

                  <div>
                    <label>Email</label>
                    <input
                      type="email"
                      name="email"
                      value={formData.email}
                      onChange={handleChange}
                    />
                  </div>

                  <div>
                    <label>Mobile</label>
                    <input
                      type="text"
                      name="mobile"
                      value={formData.mobile}
                      onChange={handleChange}
                    />
                  </div>

                  <div>
                    <label>Organization</label>
                    <select
                      name="organizationId"
                      value={formData.organizationId}
                      onChange={handleChange}
                    >
                      <option value="">Select Organization</option>
                      {organizations.map((org) => (
                        <option key={org.orgId} value={org.orgId}>
                          {org.orgName}
                        </option>
                      ))}
                    </select>
                  </div>

                  <div>
                    <label>Role</label>
                    <select
                      name="roleId"
                      value={formData.roleId}
                      onChange={handleChange}
                    >
                      <option value="">Select Role</option>
                      {roles.map((role) => (
                        <option key={role.roleId} value={role.roleId}>
                          {role.roleName}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>

                <div className="user-form-actions">
                  <button
                    type="button"
                    className="cancel-btn"
                    onClick={() => {
                      setShowModal(false);
                      resetForm();
                    }}
                  >
                    Cancel
                  </button>

                  <button type="submit" className="save-btn">
                    {editId ? "Update User" : "Save User"}
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default Users;