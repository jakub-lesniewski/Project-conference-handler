/* eslint-disable no-useless-catch */
import axios from "axios";

const baseURL = "http://localhost:8080";

const axiosInstance = axios.create({
  baseURL,
  headers: {
    "Content-Type": "application/json",
  },
});

export async function loginUser(email, password) {
  try {
    const response = await axiosInstance.post("/login", {
      email: email,
      password,
    });
    return response.data;
  } catch (error) {
    throw error;
  }
}

export async function getUser(id) {
  try {
    const response = await axios.get(
      `http://localhost:8080/admin/participant/${id}`,
    );
    return response.data;
  } catch (error) {
    throw error;
  }
}
