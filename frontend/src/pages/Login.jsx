import { useState } from "react";
import { loginUser } from "../api/authService";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";
import { jwtDecode } from "jwt-decode";
import { toast } from "react-toastify";
import "../styles/Login.css";
import Navbar from "./Navbar";

export default function Login() {
  const [credentials, setCredentials] = useState({ username: "", password: "" });
  const navigate = useNavigate();
  const { login } = useAuth();

  const handleChange = (e) => {
    setCredentials({ ...credentials, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();  // Prevent page reload
    try {
      const data = await loginUser(credentials);
      if (!data.startsWith("eyJ")) {
        toast.error(data);
        return;
      }
      login(data);
      const role = jwtDecode(data).role?.toLowerCase();
      toast.success("Login Successful!");
      navigate(`/${role}`);
    } catch (err) {
      console.error("Login failed:", err);
      toast.error("Invalid credentials or server error");
    }
  };

  const goHome = () => navigate("/");

  return (
    <div className="login-container">
      <Navbar />
      <form onSubmit={handleSubmit}>
        <h2>Login</h2>
        <input
          type="text"
          name="username"
          autoComplete="username"
          placeholder="Username"
          onChange={handleChange}
        />
        <input
          type="password"
          name="password"
          autoComplete="current-password"
          placeholder="Password"
          onChange={handleChange}
        />
        <button type="submit">Login</button>
        <p>
          Don't have an account? <a href="/register">Register here</a>
        </p>
      </form>
    </div>
  );
}
