import { useNavigate } from "react-router-dom";
import "../styles/Navbar.css";

export default function Navbar() {
  const navigate = useNavigate();

  return (
    <div className="navbar">
      <div className="logo">OMNI HOTELS</div>
      <div className="nav-buttons">
        <button onClick={() => navigate("/")}>Home</button>
      </div>
    </div>
  );
}
