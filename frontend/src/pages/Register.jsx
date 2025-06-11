import { useState } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import "../styles/Register.css";
import { useNavigate } from "react-router-dom";
import Navbar from "./Navbar";

export default function Register() {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    role: "OWNER",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };
  
  const handleSubmit = async (e) => {

    e.preventDefault();

    if (formData.username.trim().length < 4) {
        toast.error("Username must be at least 4 characters.");
        return;
      }
      if (formData.password.length < 6) {
        toast.error("Password must be at least 6 characters.");
        return;
      }

    try {
      await axios.post(
        `http://localhost:8090/auth/register?role=${formData.role}`,
        {
          username: formData.username,
          password: formData.password,
        }
      );
      toast.success("Registration successful! Please login.");
      setFormData({ username: "", password: "", role: "OWNER" });
      navigate("/login");
    } catch (error) {
      console.error("Registration error:", error);
      if (error.response && error.response.status === 409) {
        toast.error("Username already exists.");
      } else {
        toast.error("Registration failed.");
      }
    }
  };
  

  return (
    <div className="registration-container">
    <Navbar />
    <form onSubmit={handleSubmit}>
      <h2>Register</h2>
      <input
        name="username"
        placeholder="Username"
        onChange={handleChange}
        required
      />
      <input
        name="password"
        type="password"
        placeholder="Password"
        onChange={handleChange}
        required
      />
      <select name="role" value={formData.role} onChange={handleChange}>
        <option value="OWNER">OWNER</option>
        <option value="MANAGER">MANAGER</option>
        <option value="RECEP">RECEP</option>
      </select>
      <button type="submit">Register</button>
      <p>Already have an account? <a href="/login">Login here</a></p>
    </form>
    </div>
  );
}
