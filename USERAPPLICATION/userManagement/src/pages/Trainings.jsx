import { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar";
import Topbar from "../components/Topbar";
import { Search, Plus, Pencil, Trash2, X } from "lucide-react";
import "../css/Trainings.css";

import {
  createTraining,
  getAllTrainings,
  getTrainingById,
  updateTraining,
  deleteTraining,
} from "../api/trainingApi";

import { getAllDepartments } from "../api/departmentApi";

function Training() {
  const [trainings, setTrainings] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [search, setSearch] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [editId, setEditId] = useState(null);

  const [formData, setFormData] = useState({
    trainingName: "",
    batchNo: "",
    trainerName: "",
    startDate: "",
    endDate: "",
    noOfTrainees: "",
    departmentId: "",
  });

  useEffect(() => {
    fetchTrainings();
    fetchDepartments();
  }, []);

  const fetchTrainings = async () => {
    try {
      const data = await getAllTrainings();
      setTrainings(data);
    } catch (error) {
      console.error("Error fetching trainings:", error);
      alert("Failed to fetch trainings");
    }
  };

  const fetchDepartments = async () => {
    try {
      const data = await getAllDepartments();
      setDepartments(data);
    } catch (error) {
      console.error("Error fetching departments:", error);
      alert("Failed to fetch departments");
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
      trainingName: "",
      batchNo: "",
      trainerName: "",
      startDate: "",
      endDate: "",
      noOfTrainees: "",
      departmentId: "",
    });
    setEditId(null);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (
      !formData.trainingName ||
      !formData.batchNo ||
      !formData.trainerName ||
      !formData.startDate ||
      !formData.endDate ||
      !formData.noOfTrainees ||
      !formData.departmentId
    ) {
      alert("Please fill all fields");
      return;
    }

    const payload = {
      ...formData,
      noOfTrainees: Number(formData.noOfTrainees),
      departmentId: Number(formData.departmentId),
    };

    try {
      if (editId) {
        await updateTraining(editId, payload);
        alert("Training updated successfully");
      } else {
        await createTraining(payload);
        alert("Training created successfully");
      }

      await fetchTrainings();
      resetForm();
      setShowModal(false);
    } catch (error) {
      console.error("Error saving training:", error);
      alert("Failed to save training");
    }
  };

  const handleEdit = async (id) => {
    try {
      const training = await getTrainingById(id);

      setFormData({
        trainingName: training.trainingName,
        batchNo: training.batchNo,
        trainerName: training.trainerName,
        startDate: training.startDate,
        endDate: training.endDate,
        noOfTrainees: training.noOfTrainees,
        departmentId: training.departmentId,
      });

      setEditId(training.trainingId);
      setShowModal(true);
    } catch (error) {
      console.error("Error fetching training by id:", error);
      alert("Failed to fetch training details");
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteTraining(id);
      alert("Training deleted successfully");
      fetchTrainings();
    } catch (error) {
      console.error("Error deleting training:", error);
      alert("Failed to delete training");
    }
  };

  const getDepartmentName = (deptId) => {
    const dept = departments.find((d) => d.id === deptId);
    return dept ? dept.departmentName : deptId;
  };

  const filteredTrainings = trainings.filter((training) =>
    `${training.trainingId} ${training.trainingName} ${getDepartmentName(
      training.departmentId
    )}`
      .toLowerCase()
      .includes(search.toLowerCase())
  );

  return (
    <div className="training-layout">
      <Sidebar />

      <div className="training-main">
        <Topbar title="Trainings" />

        <div className="training-content">
          <div className="training-toolbar">
            <div className="training-search">
              <Search size={22} />
              <input
                type="text"
                placeholder="Search trainings..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}
              />
            </div>

            <button
              className="training-add-btn"
              onClick={() => {
                resetForm();
                setShowModal(true);
              }}
            >
              <Plus size={20} />
              Add Training
            </button>
          </div>

          <div className="training-table-wrap">
            <table className="training-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Training Name</th>
                  <th>Batch No</th>
                  <th>Trainer Name</th>
                  <th>Start Date</th>
                  <th>End Date</th>
                  <th>No. Of Trainees</th>
                  <th>Department Name</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>
                {filteredTrainings.length > 0 ? (
                  filteredTrainings.map((training) => (
                    <tr key={training.trainingId}>
                      <td>{training.trainingId}</td>
                      <td>{training.trainingName}</td>
                      <td>{training.batchNo}</td>
                      <td>{training.trainerName}</td>
                      <td>{training.startDate}</td>
                      <td>{training.endDate}</td>
                      <td>{training.noOfTrainees}</td>
                      <td>{getDepartmentName(training.departmentId)}</td>
                      <td>
                        <div className="training-actions">
                          <Pencil
                            size={19}
                            className="edit-icon"
                            onClick={() => handleEdit(training.trainingId)}
                          />
                          <Trash2
                            size={19}
                            className="delete-icon"
                            onClick={() => handleDelete(training.trainingId)}
                          />
                        </div>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="9" className="no-data">
                      No trainings found
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>

        {showModal && (
          <div className="training-modal-overlay">
            <div className="training-modal">
              <div className="training-modal-header">
                <h2>{editId ? "Edit Training" : "Add Training"}</h2>

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

              <form className="training-form" onSubmit={handleSubmit}>
                <div className="training-form-grid">
                  <div>
                    <label>Training Name</label>
                    <input
                      type="text"
                      name="trainingName"
                      value={formData.trainingName}
                      onChange={handleChange}
                    />
                  </div>

                  <div>
                    <label>Batch No</label>
                    <input
                      type="text"
                      name="batchNo"
                      value={formData.batchNo}
                      onChange={handleChange}
                    />
                  </div>

                  <div>
                    <label>Trainer Name</label>
                    <input
                      type="text"
                      name="trainerName"
                      value={formData.trainerName}
                      onChange={handleChange}
                    />
                  </div>

                  <div>
                    <label>Start Date</label>
                    <input
                      type="date"
                      name="startDate"
                      value={formData.startDate}
                      onChange={handleChange}
                    />
                  </div>

                  <div>
                    <label>End Date</label>
                    <input
                      type="date"
                      name="endDate"
                      value={formData.endDate}
                      onChange={handleChange}
                    />
                  </div>

                  <div>
                    <label>No. Of Trainees</label>
                    <input
                      type="number"
                      name="noOfTrainees"
                      value={formData.noOfTrainees}
                      onChange={handleChange}
                    />
                  </div>

                  <div>
                    <label>Department</label>
                    <select
                      name="departmentId"
                      value={formData.departmentId}
                      onChange={handleChange}
                    >
                      <option value="">Select Department</option>
                      {departments.map((dept) => (
                        <option key={dept.id} value={dept.id}>
                          {dept.departmentName}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>

                <div className="training-form-actions">
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
                    {editId ? "Update Training" : "Save Training"}
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

export default Training;