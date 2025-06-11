import { useNavigate } from "react-router-dom";
import "../styles/Home.css";

export default function Home() {
  const navigate = useNavigate();
  

  return (
    <div className="home-container">
      <div className="navbar">
        <div className="logo">OMNI HOTELS</div>
        <div className="nav-buttons">
          <button onClick={() => navigate("/login")}>Login</button>
          <button onClick={() => navigate("/register")}>Register</button>
        </div>
      </div>
      <div className="content">
        <h1>Welcome to Hotel Management System</h1>
        <p>Manage. Book. Relax. All in one place.</p>
      </div>
    </div>
  );
}
