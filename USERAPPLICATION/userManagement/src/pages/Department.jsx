import { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar";
import Topbar from "../components/Topbar";
import { Search, Plus, Pencil, Trash2, X } from "lucide-react";
import "../css/Department.css";

import {
  createDepartment,
  getAllDepartments,
  getDepartmentById,
  updateDepartment,
  deleteDepartment,
} from "../api/departmentApi";

import { getAllOrganizations } from "../api/organizationApi";

function Department() {
  const [departments, setDepartments] = useState([]);
  const [organizations, setOrganizations] = useState([]);
  const [search, setSearch] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [editId, setEditId] = useState(null);

  const [formData, setFormData] = useState({
    departmentName: "",
    numberOfTrainingsGoingOn: "",
    description: "",
    organizationId: "",
  });

  useEffect(() => {
    fetchDepartments();
    fetchOrganizations();
  }, []);

  const fetchDepartments = async () => {
    try {
      const data = await getAllDepartments();
      setDepartments(data);
    } catch (error) {
      console.error("Error fetching departments:", error);
      alert("Failed to fetch departments");
    }
  };

  const fetchOrganizations = async () => {
    try {
      const data = await getAllOrganizations();
      setOrganizations(data);
    } catch (error) {
      console.error("Error fetching organizations:", error);
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
      departmentName: "",
      numberOfTrainingsGoingOn: "",
      description: "",
      organizationId: "",
    });
    setEditId(null);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (
      !formData.departmentName ||
      !formData.numberOfTrainingsGoingOn ||
      !formData.description ||
      !formData.organizationId
    ) {
      alert("Please fill all fields");
      return;
    }

    const payload = {
      ...formData,
      numberOfTrainingsGoingOn: Number(formData.numberOfTrainingsGoingOn),
      organizationId: Number(formData.organizationId),
    };

    try {
      if (editId) {
        await updateDepartment(editId, payload);
        alert("Department updated successfully");
      } else {
        await createDepartment(payload);
        alert("Department created successfully");
      }

      await fetchDepartments();
      resetForm();
      setShowModal(false);
    } catch (error) {
      console.error("Error saving department:", error);
      alert("Failed to save department");
    }
  };

  const handleEdit = async (id) => {
    try {
      const dept = await getDepartmentById(id);

      setFormData({
        departmentName: dept.departmentName,
        numberOfTrainingsGoingOn: dept.numberOfTrainingsGoingOn,
        description: dept.description,
        organizationId: dept.organizationId,
      });

      setEditId(dept.id);
      setShowModal(true);
    } catch (error) {
      console.error("Error fetching department by id:", error);
      alert("Failed to fetch department details");
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteDepartment(id);
      alert("Department deleted successfully");
      fetchDepartments();
    } catch (error) {
      console.error("Error deleting department:", error);
      alert("Failed to delete department");
    }
  };

  const filteredDepartments = departments.filter((dept) =>
    `${dept.id} ${dept.departmentName}`
      .toLowerCase()
      .includes(search.toLowerCase())
  );

  const getOrganizationName = (orgId) => {
    const org = organizations.find((o) => o.orgId === orgId);
    return org ? org.orgName : orgId;
  };

  return (
    <div className="department-layout">
      <Sidebar />

      <div className="department-main">
        <Topbar title="Departments" />

        <div className="department-content">
          <div className="department-toolbar">
            <div className="department-search">
              <Search size={22} />
              <input
                type="text"
                placeholder="Search departments..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}
              />
            </div>

            <button
              className="department-add-btn"
              onClick={() => {
                resetForm();
                setShowModal(true);
              }}
            >
              <Plus size={20} />
              Add Department
            </button>
          </div>

          <div className="department-table-wrap">
            <table className="department-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Department Name</th>
                  <th>No. of Trainings</th>
                  <th>Description</th>
                  <th>Organization</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>
                {filteredDepartments.length > 0 ? (
                  filteredDepartments.map((dept) => (
                    <tr key={dept.id}>
                      <td>{dept.id}</td>
                      <td>{dept.departmentName}</td>
                      <td>{dept.numberOfTrainingsGoingOn}</td>
                      <td>{dept.description}</td>
                      <td>{getOrganizationName(dept.organizationId)}</td>

                      <td>
                        <div className="department-actions">
                          <Pencil
                            size={19}
                            className="edit-icon"
                            onClick={() => handleEdit(dept.id)}
                          />
                          <Trash2
                            size={19}
                            className="delete-icon"
                            onClick={() => handleDelete(dept.id)}
                          />
                        </div>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="6" className="no-data">
                      No departments found
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>

        {showModal && (
          <div className="dept-modal-overlay">
            <div className="dept-modal">
              <div className="dept-modal-header">
                <h2>{editId ? "Edit Department" : "Add Department"}</h2>

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

              <form className="dept-form" onSubmit={handleSubmit}>
                <div className="dept-form-grid">
                       <div>
                           <label>Department</label>
                           <input
                              type="text"
                              name="department"
                              value={formData.department}
                              onChange={handleChange}
                           />
                  </div>

                  <div>
                    <label>Number Of Trainings Going On</label>
                    <input
                      type="number"
                      name="numberOfTrainingsGoingOn"
                      value={formData.numberOfTrainingsGoingOn}
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
                </div>

                <div className="dept-form-actions">
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
                    {editId ? "Update Department" : "Save Department"}
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

export default Department;