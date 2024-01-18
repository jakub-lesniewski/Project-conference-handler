function Button({
  children = "submit",
  onClick,
  type = "button",
  style,
  disabled,
}) {
  let buttonClass = "";

  switch (style) {
    case "alt":
      buttonClass =
        "inline-flex rounded-md border border-fmcsGreen bg-fmcsGreen p-2 text-fmcsWhite transition-all duration-200 hover:bg-fmcsWhite hover:text-fmcsGreen";
      break;
    default:
      buttonClass =
        "inline-flex items-center justify-center gap-4 whitespace-nowrap rounded-md border-2 border-fmcsGreen bg-fmcsGreen p-2 text-lg tracking-wider text-fmcsWhite transition-all duration-300 focus:border-fmcsBlack focus:outline-none focus:ring-fmcsBlack";
  }

  return (
    <button
      disabled={disabled}
      onClick={onClick}
      type={type}
      className={buttonClass}
    >
      {children}
    </button>
  );
}

export default Button;
