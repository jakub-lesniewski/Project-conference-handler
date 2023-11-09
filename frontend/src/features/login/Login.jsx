import InputForm from "../../ui/InputForm";
import Button from "../../ui/Button";
import { useState } from "react";
import { loginUser } from "../../services/api";
import { useAuth } from "../../utils/auth";
import { useNavigate } from "react-router-dom";

function Login() {
  const [userCredentials, setUserCredentials] = useState({
    email: "",
    password: "",
  });

  const navigate = useNavigate();
  const { email, password } = userCredentials;
  const { user, login } = useAuth();

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
    console.log("api response", response);

    if (response.id) {
      await login({
        id: response.id,
        name: response.name,
        surname: response.surname,
        email_login: response.email_login,
        affilation: response.affilation,
      });

      console.log("logged user", user);
      navigate(`/user/${user.id}`);
    }
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

        <Button type="submit" />
      </form>
    </div>
  );
}

export default Login;
