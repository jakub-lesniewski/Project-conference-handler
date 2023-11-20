import { Form, useForm } from "react-hook-form";
import { emailPattern } from "./helpers";
import Button from "../../ui/Button";
import warningIcon from "../../assets/warning-icon.svg";
import { useState } from "react";
import axios from "axios";

function Login() {
  const [isLoading, setIsLoading] = useState(false);

  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
  } = useForm("OnSubmit");

  async function onSubmit(data) {
    const response = await axios.post;
  }

  return (
    <Form
      control={control}
      action="http://localhost:8080/login"
      method="post"
      headers={{
        "Content-Type": "application/json",
      }}
      onSubmit={handleSubmit(onSubmit)}
      onSuccess={() => {
        alert("Success");
      }}
      // onError={() => alert("Error")}
      className="flex flex-col gap-5"
    >
      <div>
        <input
          {...register("email", {
            required: "Email is required",
            pattern: {
              value: emailPattern,
              message: "Invalid email format",
            },
          })}
          placeholder="email"
          type="email"
          className="relative rounded-md border-2 p-1 pl-3 text-lg transition-all duration-300 focus:border-fmcsGreen focus:outline-none focus:ring-fmcsGreen"
        />
        {errors.email && (
          <p className="absolute flex items-center gap-1 pl-1 text-sm text-fmcsRed">
            <img
              src={warningIcon}
              alt="error icon"
              className="h-3 text-fmcsRed"
            />
            {errors.email.message}
          </p>
        )}
      </div>

      <div>
        <input
          {...register("password", { required: "Password is required" })}
          placeholder="password"
          type="password"
          className="relative rounded-md border-2 p-1 pl-3 text-lg transition-all duration-300 focus:border-fmcsGreen focus:outline-none focus:ring-fmcsGreen"
        />
        {errors.password && (
          <p className="absolute flex items-center gap-1 pl-1 text-sm text-fmcsRed">
            <img
              src={warningIcon}
              alt="error icon"
              className="h-3 text-fmcsRed"
            />
            {errors.password.message}
          </p>
        )}
      </div>

      <Button type="submit">submit</Button>
    </Form>
  );
}

export default Login;
