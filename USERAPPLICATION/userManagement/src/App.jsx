import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
// import ForgotPassword from "./pages/ForgotPassword";
// import VerifyEmail from "./pages/VerifyEmail";
// import SetPassword from "./pages/SetPassword";
import Dashboard from "./pages/Dashboard";
import SettingsPage from "./pages/Settings";
import Attendance from "./pages/Attendance";

import Users from "./pages/Users";
import Roles from "./pages/Roles";
import Permissions from "./pages/Permissions";
import Organizations from "./pages/Organizations";
import Department from "./pages/Department";
import Training from "./pages/Trainings";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        {/* <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/verify-email" element={<VerifyEmail />} />
        <Route path="/set-password" element={<SetPassword />} /> */}
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/users" element={<Users />} />
        <Route path="/roles" element={<Roles />} />
        <Route path="/permissions" element={<Permissions />} />
        <Route path="/organizations" element= {<Organizations />}/>
        <Route path="/department" element={<Department />} />
        <Route path="/training" element={<Training />} />
        <Route path="/settings" element={<SettingsPage />}/>
        <Route path="/attendance" element={<Attendance/>}/>
      </Routes>
    </BrowserRouter>
  );
}

export default App;



// import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
// import Login from "./pages/Login";
// import Register from "./pages/Register";
// // import ForgotPassword from "./pages/ForgotPassword";
// // import VerifyEmail from "./pages/VerifyEmail";
// // import SetPassword from "./pages/SetPassword";
// import Dashboard from "./pages/Dashboard";
// import SettingsPage from "./pages/Settings";
// import Attendance from "./pages/Attendance";
// import ProtectedRoute from "./pages/ProtectedRoute";
// import PublicRoute from "./pages/PublicRoute";
// import NotFound from "./pages/NotFound";
// import Users from "./pages/Users";
// import Roles from "./pages/Roles";
// import Permissions from "./pages/Permissions";
// import Organizations from "./pages/Organizations";
// import Department from "./pages/Department";
// import Training from "./pages/Trainings";
// import { Settings } from "lucide-react";
//
// function App() {
//   return (
//     <BrowserRouter>
//       <Routes>
//         <Route path="/" element={<Navigate to="/login" replace />} />
//         <Route path="/login" element={<PublicRoute>
//                       <Login />
//                     </PublicRoute>} />
//         <Route path="/register" element={<PublicRoute>
//                       <Register />
//                     </PublicRoute>} />
//         {/* <Route path="/forgot-password" element={<ForgotPassword />} />
//         <Route path="/verify-email" element={<VerifyEmail />} />
//         <Route path="/set-password" element={<SetPassword />} /> */}
//         <Route path="/dashboard" element={<ProtectedRoute>
//                       <Dashboard />
//                     </ProtectedRoute>} />
//         <Route path="/users" element={<ProtectedRoute>
//                       <Users />
//                     </ProtectedRoute>} />
//         <Route path="/roles" element={<ProtectedRoute>
//                       <Roles />
//                     </ProtectedRoute>} />
//         <Route path="/permissions" element={<ProtectedRoute>
//                       <Permissions />
//                     </ProtectedRoute>} />
//         <Route path="/organizations" element= {<ProtectedRoute>
//                       <Organizations />
//                     </ProtectedRoute>}/>
//         <Route path="/department" element={<ProtectedRoute>
//                       <Department />
//                     </ProtectedRoute>} />
//         <Route path="/training" element={<ProtectedRoute>
//                       <Training />
//                     </ProtectedRoute>} />
//         <Route path="/settings" element={<ProtectedRoute>
//                       <Settings />
//                     </ProtectedRoute>}/>
//         <Route path="/attendance" element={<ProtectedRoute>
//                       <Attendance />
//                     </ProtectedRoute>}/>
//
//          <Route path="*" element={<NotFound />} />
//       </Routes>
//     </BrowserRouter>
//   );
// }
//
// export default App;