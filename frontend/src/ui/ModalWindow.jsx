import xIcon from "../assets/x-solid.svg";

function ModalWindow({ children }) {
  return (
    <div className="absolute inset-0 z-50 flex items-center justify-center backdrop-blur-sm">
      <main className="relative h-4/5 w-4/5 rounded-lg bg-fmcsWhite p-24">
        <button className="absolute right-7 top-7 h-5 w-5">
          <img src={xIcon} />
        </button>
        {children}
      </main>
    </div>
  );
}

export default ModalWindow;
