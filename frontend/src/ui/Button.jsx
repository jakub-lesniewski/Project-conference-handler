function Button({ children = "submit", onClick, type = "button", disabled }) {
  return (
    <button
      disabled={disabled}
      onClick={onClick}
      type={type}
      className="inline-flex items-center justify-center gap-4 whitespace-nowrap rounded-md border-2 border-fmcsGreen bg-fmcsGreen p-2 text-lg tracking-wider text-fmcsWhite transition-all duration-300 focus:border-fmcsBlack focus:outline-none focus:ring-fmcsBlack"
    >
      {children}
    </button>
  );
}

export default Button;
