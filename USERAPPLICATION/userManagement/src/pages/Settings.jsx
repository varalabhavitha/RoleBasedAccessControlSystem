import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { X } from "lucide-react";
import Sidebar from "../components/Sidebar";
import Topbar from "../components/Topbar";
import "../css/Settings.css";

function SettingsPage() {
  const [theme, setTheme] = useState("light");
  const navigate = useNavigate();

  useEffect(() => {
    const savedTheme = localStorage.getItem("theme") || "light";
    setTheme(savedTheme);

    if (savedTheme === "dark") {
      document.body.classList.add("dark-theme");
    } else {
      document.body.classList.remove("dark-theme");
    }
  }, []);

  const changeTheme = (selectedTheme) => {
    setTheme(selectedTheme);
    localStorage.setItem("theme", selectedTheme);

    if (selectedTheme === "dark") {
      document.body.classList.add("dark-theme");
    } else {
      document.body.classList.remove("dark-theme");
    }
  };

  const handleClose = () => {
    navigate("/dashboard");
  };

  return (
    <div className="settings-layout">
      <Sidebar />

      <main className="settings-main">
        <Topbar title="Settings" />

        <div className="settings-content">
          <div className="settings-card">

            {/* Close Button */}
            <div className="settings-header">
              <h3>Appearance</h3>
              <button className="settings-close" onClick={handleClose}>
                <X size={22} />
              </button>
            </div>

            <p>Choose your preferred application theme.</p>

            <div className="theme-options">
              <button
                className={theme === "light" ? "theme-btn active-theme" : "theme-btn"}
                onClick={() => changeTheme("light")}
              >
                Light Mode
              </button>

              <button
                className={theme === "dark" ? "theme-btn active-theme" : "theme-btn"}
                onClick={() => changeTheme("dark")}
              >
                Dark Mode
              </button>
            </div>

          </div>
        </div>
      </main>
    </div>
  );
}

export default SettingsPage;