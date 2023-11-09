import { Form } from "react-router-dom";
import InputForm from "../../ui/InputForm";
import Button from "../../ui/Button";
import axios from "axios";
import { useState } from "react";
import { loginUser } from "./api";

function Login() {
  const [userCredentials, setUserCredentials] = useState({
    email: "",
    password: "",
  });

  const { email, password } = userCredentials;

  function handleInputChange(e) {
    const { name, value } = e.target;
    setUserCredentials({
      ...userCredentials,
      [name]: value,
    });
  }

  async function handleSubmit(e) {
    e.preventDefault();

    console.log(userCredentials);

    const response = await loginUser(email, password);
    console.log(response);
  }

  return (
    <div className="flex flex-col items-center gap-5">
      <div className="flex">
        <h1 className="text-2xl font-semibold tracking-widest">login</h1>
      </div>

      <form className="flex flex-col gap-5" onSubmit={handleSubmit}>
        <InputForm
          type="text"
          placeholder="email"
          value={email}
          name="email"
          onChange={handleInputChange}
        />

        <InputForm
          type="password"
          placeholder="password"
          value={password}
          name="password"
          onChange={handleInputChange}
        />

        <Button onClick={handleSubmit} type="submit" />
      </form>
    </div>
  );
}

export async function action({ request }) {
  // TODO make it a util function
  const config = {
    method: "post",
    maxBodyLength: Infinity,
    url: "http://localhost:8080/login",
    headers: {
      "Content-Type": "application/json",
    },
    data: JSON.stringify(data),
  };

  const response = await axios.request(config);
  const responseData = response.data;

  console.log(responseData);
}

export default Login;
