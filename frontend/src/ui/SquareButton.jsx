function SquareButton({ children, onClick }) {
  return (
    <button
      type="button"
      onClick={onClick}
      className="border-2 border-fmcsGreen bg-fmcsGreen p-2 font-bold tracking-wider text-fmcsWhite transition-all hover:bg-fmcsWhite hover:text-fmcsGreen"
    >
      {children}
    </button>
  );
}

export default SquareButton;
