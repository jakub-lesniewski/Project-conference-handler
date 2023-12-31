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

export function getUser(id) {
  const url = `http://localhost:8080/admin/participant/${id}`;

  return axios
    .get(url)
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      throw error;
    });
}
