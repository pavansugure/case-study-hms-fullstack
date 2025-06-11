import axios from "axios";

const API_BASE = "http://localhost:8081/guests";

// Get token from localStorage (assumes token is stored after login)
const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

export const addGuest = (guest) => axios.post(`${API_BASE}/add`, guest, getAuthHeaders());
export const updateGuest = (guest) => axios.put(API_BASE, guest, getAuthHeaders());
export const deleteGuest = (id) => axios.delete(`${API_BASE}/${id}`, getAuthHeaders());
export const getGuestById = (id) => axios.get(`${API_BASE}/${id}`, getAuthHeaders());
export const getAllGuests = () => axios.get(API_BASE, getAuthHeaders());
