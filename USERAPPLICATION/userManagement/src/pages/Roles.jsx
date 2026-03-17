import { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar";
import Topbar from "../components/Topbar";
import { Search, Plus, Pencil, Trash2, X } from "lucide-react";
import "../css/Roles.css";

import {
  createRole,
  getAllRoles,
  getRoleById,
  updateRole,
  deleteRole,
} from "../api/roleApi";

import { getPermissions } from "../api/permissionApi";

function Role() {
  const [roles, setRoles] = useState([]);
  const [permissions, setPermissions] = useState([]);
  const [search, setSearch] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [editId, setEditId] = useState(null);

  const [formData, setFormData] = useState({
    roleName: "",
    description: "",
    status: "ACTIVE",
    createdBy: "",
    permissionIds: [],
  });

  useEffect(() => {
    fetchRoles();
    fetchPermissions();
  }, []);

  const fetchRoles = async () => {
    try {
      const data = await getAllRoles();
      setRoles(data);
    } catch (error) {
      console.error("Error fetching roles:", error);
      alert("Failed to fetch roles");
    }
  };

  const fetchPermissions = async () => {
    try {
      const res = await getPermissions(0, 100);
      setPermissions(res.data.data || []);
    } catch (error) {
      console.error("Error fetching permissions:", error);
      alert("Failed to fetch permissions");
    }
  };

  const handleChange = (e) => {
    const { name, value, options } = e.target;

    if (name === "permissionIds") {
      const selectedValues = Array.from(options)
        .filter((option) => option.selected)
        .map((option) => Number(option.value));

      setFormData((prev) => ({
        ...prev,
        permissionIds: selectedValues,
      }));
    } else {
      setFormData((prev) => ({
        ...prev,
        [e.target.name]: e.target.value,
      }));
    }
  };

  const resetForm = () => {
    setFormData({
      roleName: "",
      description: "",
      status: "ACTIVE",
      createdBy: "",
      permissionIds: [],
    });
    setEditId(null);
  };

  const getPermissionNames = (permissionIds) => {
    if (!permissionIds || permissionIds.length === 0) return [];

    return permissionIds.map((id) => {
      const permission = permissions.find((item) => item.id === id);
      return permission ? permission.name : id;
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (
      !formData.roleName ||
      !formData.description ||
      !formData.status ||
      !formData.createdBy
    ) {
      alert("Please fill all required fields");
      return;
    }

    const payload = {
      roleName: formData.roleName,
      description: formData.description,
      status: formData.status,
      createdBy: formData.createdBy,
      permissionIds: formData.permissionIds,
    };

    try {
      if (editId) {
        await updateRole(editId, payload);
        alert("Role updated successfully");
      } else {
        await createRole(payload);
        alert("Role created successfully");
      }

      await fetchRoles();
      resetForm();
      setShowModal(false);
    } catch (error) {
      console.error("Error saving role:", error);
      alert("Failed to save role");
    }
  };

  const handleEdit = async (id) => {
    try {
      const role = await getRoleById(id);

      setFormData({
        roleName: role.roleName || "",
        description: role.description || "",
        status: role.status || "ACTIVE",
        createdBy: role.createdBy || "",
        permissionIds: role.permissionIds || [],
      });

      setEditId(role.roleId);
      setShowModal(true);
    } catch (error) {
      console.error("Error fetching role by id:", error);
      alert("Failed to fetch role details");
    }
  };

const handleDelete = async (id) => {
  try {
    const data = await getAllRoles();
    const activeRoles = data.filter(
      (role) => String(role.status).toUpperCase() !== "DELETED"
    );
    setRoles(activeRoles);
  } catch (error) {
    console.error("Error fetching roles:", error);
    alert("Failed to fetch roles");
  }
};

  const filteredRoles = roles.filter((role) => {
    const permissionNames = getPermissionNames(role.permissionIds || []).join(" ");
    return `${role.roleId} ${role.roleName} ${role.description} ${role.status} ${permissionNames}`
      .toLowerCase()
      .includes(search.toLowerCase());
  });

  return (
    <div className="role-layout">
      <Sidebar />

      <div className="role-main">
        <Topbar title="Roles" />

        <div className="role-content">
          <div className="role-toolbar">
            <div className="role-search">
              <Search size={22} />
              <input
                type="text"
                placeholder="Search roles..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}
              />
            </div>

            <button
              className="role-add-btn"
              onClick={() => {
                resetForm();
                setShowModal(true);
              }}
            >
              <Plus size={20} />
              Add Role
            </button>
          </div>

          <div className="role-table-wrap">
            <table className="role-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Role Name</th>
                  <th>Description</th>
                  <th>Status</th>
                  <th>Permissions</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>
                {filteredRoles.length > 0 ? (
                  filteredRoles.map((role) => (
                    <tr key={role.roleId}>
                      <td>{role.roleId}</td>
                      <td>{role.roleName}</td>
                      <td>{role.description}</td>
                      <td>
                        <span className={`role-status ${String(role.status).toLowerCase()}`}>
                          {role.status}
                        </span>
                      </td>
                      <td>
                        {getPermissionNames(role.permissionIds || []).length > 0
                          ? getPermissionNames(role.permissionIds || []).join(", ")
                          : "No Permissions"}
                      </td>
                      <td>
                        <div className="role-actions">
                          <Pencil
                            size={19}
                            className="edit-icon"
                            onClick={() => handleEdit(role.roleId)}
                          />
                          <Trash2
                            size={19}
                            className="delete-icon"
                            onClick={() => handleDelete(role.roleId)}
                          />
                        </div>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="6" className="no-data">
                      No roles found
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>

        {showModal && (
          <div className="role-modal-overlay">
            <div className="role-modal">
              <div className="role-modal-header">
                <h2>{editId ? "Edit Role" : "Add Role"}</h2>

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

              <form className="role-form" onSubmit={handleSubmit}>
                <div className="role-form-grid">
                  <div>
                    <label>Role Name</label>
                    <input
                      type="text"
                      name="roleName"
                      value={formData.roleName}
                      onChange={handleChange}
                    />
                  </div>

                  <div>
                    <label>Description</label>
                    <input
                      type="text"
                      name="description"
                      value={formData.description}
                      onChange={handleChange}
                    />
                  </div>

                  <div>
                    <label>Status</label>
                    <select
                      name="status"
                      value={formData.status}
                      onChange={handleChange}
                    >
                      <option value="ACTIVE">ACTIVE</option>
                      <option value="INACTIVE">INACTIVE</option>
                    </select>
                  </div>

                  <div>
                    <label>Created By</label>
                    <input
                      type="text"
                      name="createdBy"
                      value={formData.createdBy}
                      onChange={handleChange}
                    />
                  </div>

                  <div className="full-width">
                    <label>Permissions</label>
                    <select
                      name="permissionIds"
                      multiple
                      value={formData.permissionIds}
                      onChange={handleChange}
                    >
                      {permissions.map((permission) => (
                        <option key={permission.id} value={permission.id}>
                          {permission.name}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>

                <div className="role-form-actions">
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
                    {editId ? "Update Role" : "Save Role"}
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

export default Role;