import axios from "axios";

const BASE_URL = "http://localhost:8085/inv";

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

export const addInventory = (data) => axios.post(`${BASE_URL}/add`, data, getAuthHeaders());
export const getAllInventory = () => axios.get(BASE_URL, getAuthHeaders());
export const getInventoryByCode = (code) => axios.get(`${BASE_URL}/${code}`, getAuthHeaders());
export const updateInventory = (data) => axios.put(`${BASE_URL}/${data.code}`, data, getAuthHeaders());
export const deleteInventory = (code) => axios.delete(`${BASE_URL}/${code}`, getAuthHeaders());
