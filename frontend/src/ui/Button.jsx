function Button({ children = "submit", onClick, type = "button", disabled }) {
  return (
    <button
      disabled={disabled}
      onClick={onClick}
      type={type}
      className="inline-flex items-center justify-center gap-4 rounded-md border-2 border-fmcsGreen bg-fmcsGreen p-1 text-lg tracking-wider text-fmcsWhite transition-all duration-300 focus:border-fmcsBlack focus:outline-none"
    >
      {children}
    </button>
  );
}

export default Button;
