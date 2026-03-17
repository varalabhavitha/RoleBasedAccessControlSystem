import { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar";
import Topbar from "../components/Topbar";
import { Search, Plus, Pencil, Trash2, X } from "lucide-react";
import "../css/Organizations.css";

import {
  createOrganization,
  getAllOrganizations,
  getOrganizationById,
  updateOrganization,
  deleteOrganization,
} from "../api/organizationApi";

function Organization() {
  const [organizations, setOrganizations] = useState([]);
  const [search, setSearch] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [editId, setEditId] = useState(null);

  const [formData, setFormData] = useState({
    orgName: "",
    address: "",
    code: "",
  });

  useEffect(() => {
    fetchOrganizations();
  }, []);

  const fetchOrganizations = async () => {
    try {
      const data = await getAllOrganizations();
      setOrganizations(data);
    } catch (error) {
      console.error("Error fetching organizations:", error);
      alert("Failed to fetch organizations");
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
      orgName: "",
      address: "",
      code: "",
    });
    setEditId(null);
  };

  const handleAddOrUpdateOrganization = async (e) => {
    e.preventDefault();

    if (!formData.orgName || !formData.address || !formData.code) {
      alert("Please fill all fields");
      return;
    }

    try {
      if (editId) {
        await updateOrganization(editId, formData);
        alert("Organization updated successfully");
      } else {
        await createOrganization(formData);
        alert("Organization created successfully");
      }

      await fetchOrganizations();
      resetForm();
      setShowModal(false);
    } catch (error) {
      console.error("Error saving organization:", error);
      alert("Failed to save organization");
    }
  };

  const handleEdit = async (id) => {
    try {
      const org = await getOrganizationById(id);

      setFormData({
        orgName: org.orgName,
        address: org.address,
        code: org.code,
      });

      setEditId(org.orgId);
      setShowModal(true);
    } catch (error) {
      console.error("Error fetching organization:", error);
      alert("Failed to fetch organization");
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteOrganization(id);
      alert("Organization deleted successfully");
      await fetchOrganizations();
    } catch (error) {
      console.error("Error deleting organization:", error);
      alert("Failed to delete organization");
    }
  };

  const filteredOrganizations = organizations.filter((org) =>
    `${org.orgId} ${org.orgName} ${org.address} ${org.code}`
      .toLowerCase()
      .includes(search.toLowerCase())
  );

  return (
    <div className="organization-layout">
      <Sidebar />

      <div className="organization-main">
        <Topbar title="Organizations" />

        <div className="organization-content">
          <div className="organization-toolbar">
            <div className="organization-search">
              <Search size={22} />
              <input
                type="text"
                placeholder="Search organizations..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}
              />
            </div>

            <button
              className="organization-add-btn"
              onClick={() => {
                resetForm();
                setShowModal(true);
              }}
            >
              <Plus size={20} />
              Add Organization
            </button>
          </div>

          <div className="organization-table-wrap">
            <table className="organization-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Organization Name</th>
                  <th>Address</th>
                  <th>Code</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>
                {filteredOrganizations.length > 0 ? (
                  filteredOrganizations.map((org) => (
                    <tr key={org.orgId}>
                      <td>{org.orgId}</td>
                      <td>{org.orgName}</td>
                      <td>{org.address}</td>
                      <td>{org.code}</td>
                      <td>
                        <div className="organization-actions">
                          <Pencil
                            size={19}
                            className="edit-icon"
                            onClick={() => handleEdit(org.orgId)}
                          />
                          <Trash2
                            size={19}
                            className="delete-icon"
                            onClick={() => handleDelete(org.orgId)}
                          />
                        </div>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="5" className="no-data">
                      No organizations found
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>

        {showModal && (
          <div className="org-modal-overlay">
            <div className="org-modal">
              <div className="org-modal-header">
                <h2>{editId ? "Edit Organization" : "Add Organization"}</h2>

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

              <form className="org-form" onSubmit={handleAddOrUpdateOrganization}>
                <div className="org-form-grid">
                  <div>
                    <label>Organization Name</label>
                    <input
                      type="text"
                      name="orgName"
                      value={formData.orgName}
                      onChange={handleChange}
                    />
                  </div>

                  <div>
                    <label>Address</label>
                    <input
                      type="text"
                      name="address"
                      value={formData.address}
                      onChange={handleChange}
                    />
                  </div>

                  <div>
                    <label>Code</label>
                    <input
                      type="text"
                      name="code"
                      value={formData.code}
                      onChange={handleChange}
                    />
                  </div>
                </div>

                <div className="org-form-actions">
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
                    {editId ? "Update Organization" : "Save Organization"}
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

export default Organization;