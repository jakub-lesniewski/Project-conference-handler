import { useState } from "react";
import { useNavigate } from "react-router-dom";
import InputForm from "../../ui/InputForm";
import Button from "../../ui/Button";

function Login() {
  const [userCredentials, setUserCredentials] = useState({
    email: "",
    token: "",
  });

  const { email, token } = userCredentials;

  const navigate = useNavigate();

  function handleInputChange(e) {
    const { name, value } = e.target;
    setUserCredentials({
      ...userCredentials,
      [name]: value,
    });
  }

  function handleSubmit(e) {
    e.preventDefault();

    console.log(userCredentials);
    navigate("user/123");
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
          placeholder="token"
          value={token}
          name="token"
          onChange={handleInputChange}
        />

        <Button onClick={handleSubmit} type="submit" />
      </form>
    </div>
  );
}

export default Login;
