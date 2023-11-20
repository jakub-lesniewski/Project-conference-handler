import InputForm from "../../ui/InputForm";
import Button from "../../ui/Button";
import { useState } from "react";
import { loginUser } from "../../services/api";
import { useAuth } from "../../utils/auth";
import { useNavigate } from "react-router-dom";
import { isValidEmail } from "./helpers";

function Login() {
  const [userCredentials, setUserCredentials] = useState({
    email: "",
    password: "",
  });

  const navigate = useNavigate();
  const { email, password } = userCredentials;
  const { user, login } = useAuth();
  const errors = {};

  function handleInputChange(e) {
    const { name, value } = e.target;
    setUserCredentials({
      ...userCredentials,
      [name]: value,
    });
  }

  // TODO implement loader element
  async function handleSubmit(e) {
    e.preventDefault();
    console.log(userCredentials);

    if (!isValidEmail(email)) {
      errors.email = "Invalid email format";
      console.log("Invalid email format");
    }

    const response = await loginUser(email, password);
    console.log("api response", response);

    if (response.id) {
      login({
        id: response.id,
        name: response.name,
        surname: response.surname,
        email_login: response.email_login,
        affilation: response.affilation,
      });

      // temporary solution
      // needs to take from user, not response
      // need to make sure user exist when accessing
      console.log("logged user", user);
      navigate(`/user/${response.id}`);
    }
  }

  return (
    <div className="flex flex-col items-center gap-5">
      <div className="flex">
        <h1 className="text-2xl font-semibold tracking-widest">login</h1>
      </div>

      <form className="flex flex-col gap-7" onSubmit={handleSubmit}>
        <InputForm
          type="text"
          value={email}
          name="email"
          onChange={handleInputChange}
          error={errors?.email}
        />

        <InputForm
          type="password"
          value={password}
          name="password"
          onChange={handleInputChange}
        />

        <Button type="submit" />
      </form>
    </div>
  );
}

export default Login;
