function SquareButton({ children }) {
  return (
    <button
      type="button"
      className="border-2 border-fmcsGreen bg-fmcsGreen p-2 font-bold tracking-wider tracking-wider text-fmcsWhite hover:bg-fmcsWhite hover:text-fmcsGreen"
    >
      {children}
    </button>
  );
}

export default SquareButton;
