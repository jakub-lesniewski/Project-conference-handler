import { useForm } from "react-hook-form";
import { emailPattern } from "./helpers";
import Button from "../../ui/Button";
import warningIcon from "../../assets/warning-icon.svg";

function Login() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm("OnSubmit");

  function onSubmit(data) {
    console.log(data);
  }

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-7">
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
          <p className="absolute pl-1 text-sm text-fmcsRed">
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
    </form>
  );
}

export default Login;
