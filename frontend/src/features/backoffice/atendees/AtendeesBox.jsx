import { useEffect, useState } from "react";
import ModalWindow from "../../../ui/ModalWindow";
import ModalAtendee from "./ModalAtendee";
import RowAtendee from "./RowAtendee";

const tableHeadRow = ["id", "name", "surname", "email", "affiliation"];

function AtendeesBox({ setAtendeesData }) {
  const [atendeesArr, setAtendeesArr] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedAtendee, setSelectedAtendee] = useState(null);

  useEffect(() => {
    setAtendeesData(atendeesArr);
  }, [atendeesArr]);

  function modifyAtendee(atendee) {
    setSelectedAtendee(atendee);
    toggleModal();
  }

  function toggleModal() {
    if (selectedAtendee) {
      setSelectedAtendee(null);
    }
    setShowModal(!showModal);
  }

  function removeAtendee(id) {
    setAtendeesArr((prev) => prev.filter((atendee) => atendee.id !== id));
  }

  return (
    <section>
      <div className="max-h-[800px] w-full overflow-auto border-2 shadow-md sm:rounded-lg">
        <table className="w-full text-left rtl:text-right">
          <thead className="border-b text-xs uppercase">
            <tr>
              {tableHeadRow.map((item, index) => (
                <th key={index} className="p-3 text-center">
                  {item}
                </th>
              ))}
              <th className="w-24"></th>
            </tr>
          </thead>

          <tbody>
            {atendeesArr.map((atendee) => (
              <RowAtendee
                atendee={atendee}
                key={atendee.id}
                onModify={modifyAtendee}
                removeAtendee={removeAtendee}
              />
            ))}
          </tbody>
        </table>
        <button
          onClick={toggleModal}
          className="m-4 rounded-md bg-fmcsGreen px-2 py-1 tracking-wide text-fmcsWhite"
        >
          add atendee
        </button>
      </div>

      {showModal && (
        <ModalWindow onClose={toggleModal}>
          <ModalAtendee
            toggleModal={toggleModal}
            atendee={selectedAtendee}
            setAtendeesArr={setAtendeesArr}
          />
        </ModalWindow>
      )}
    </section>
  );
}

export default AtendeesBox;
