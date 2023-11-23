import { useState } from "react";
import SquareButton from "../../ui/SquareButton";
import ModalWindow from "../../ui/ModalWindow";

function HeaderBox() {
  const [modal, setModal] = useState(false);

  function handleToggleModal() {
    setModal(!modal);
  }

  return (
    <header className="flex flex-col justify-between gap-5 border border-t-0 text-center">
      <div className="p-10 text-2xl font-bold tracking-wider">
        <h1>Conference panel</h1>
        <h2>organisers_token123</h2>
      </div>

      <SquareButton onClick={handleToggleModal}>Create conference</SquareButton>
      {modal && <ModalWindow onClose={handleToggleModal} />}
    </header>
  );
}

export default HeaderBox;
