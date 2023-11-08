import { useState } from "react";
import eyeIcon from "../assets/eyeIcon.svg";

function InputForm({
  type = "text",
  placeholder = "placeholder",
  value,
  name,
  onChange,
}) {
  const [isPasswordVisible, setPasswordVisible] = useState(false);

  function togglePasswordVisibility() {
    setPasswordVisible(!isPasswordVisible);
  }

  return (
    <div className="relative">
      <input
        className="rounded-md border-2 p-1 pl-3 text-lg transition-all duration-300 focus:border-fmcsGreen focus:outline-none focus:ring-fmcsGreen"
        placeholder={placeholder}
        type={isPasswordVisible ? "text" : type}
        value={value}
        name={name}
        onChange={onChange}
      />

      {type === "password" && (
        <button type="button" onClick={togglePasswordVisibility} tabIndex="-1">
          <img
            src={eyeIcon}
            alt="toggle token visibility button"
            className="absolute right-3 top-1/2 -translate-y-1/2 cursor-pointer"
          />
        </button>
      )}
    </div>
  );
}

export default InputForm;
