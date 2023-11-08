import { useState } from "react";
import { Form, useNavigate } from "react-router-dom";
import InputForm from "../../ui/InputForm";
import Button from "../../ui/Button";
import axios from "axios";

function Login() {
  const navigate = useNavigate();

  return (
    <div className="flex flex-col items-center gap-5">
      <div className="flex">
        <h1 className="text-2xl font-semibold tracking-widest">login</h1>
      </div>

      <Form method="POST" className="flex flex-col gap-5">
        <InputForm type="text" placeholder="email" name="email" />

        <InputForm type="password" placeholder="password" name="password" />

        <Button type="submit" />
      </Form>
    </div>
  );
}

export async function action({ request }) {
  const formData = await request.formData();
  const email = formData.get("email");
  const token = formData.get("password");

  const data = {
    email_login: email,
    password: token,
  };

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

  console.log(JSON.stringify(response.data, null, 2));

  return null;
}

export default Login;
