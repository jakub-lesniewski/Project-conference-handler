function SquareButton({ children, onClick, type = "button" }) {
  return (
    <button
      type={type}
      onClick={onClick}
      className="border-2 border-fmcsGreen bg-fmcsGreen p-2 font-bold tracking-wider text-fmcsWhite transition-all hover:bg-fmcsWhite hover:text-fmcsGreen"
    >
      {children}
    </button>
  );
}

export default SquareButton;
