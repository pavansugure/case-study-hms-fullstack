import axios from "axios";

const URL = "http://localhost:8084/staff";

const getAuthHeader = () => {
    const token = localStorage.getItem("token");
    return{
        headers: {
            Authorization : `Bearer ${token}`,
        },
    };
};

export const addStaff = (staff) => axios.post(`${URL}/add`, staff, getAuthHeader());
export const updateStaff = (staff) => axios.put(URL, staff, getAuthHeader());
export const deleteStaff = (id) => axios.delete(`${URL}/${id}`, getAuthHeader());
export const getStaffById = (id) => axios.get(`${URL}/${id}`, getAuthHeader());
export const getAllStaff = () => axios.get(URL, getAuthHeader());