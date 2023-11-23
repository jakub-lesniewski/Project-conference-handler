function ModalWindow({ children, onClose }) {
  function handleCloseClick() {
    onClose();
  }

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center backdrop-blur-sm">
      <div className="relative rounded-md bg-fmcsWhite p-20">
        <button className="absolute right-3 top-3" onClick={handleCloseClick}>
          Close
        </button>
        {children}
      </div>
    </div>
  );
}

export default ModalWindow;
