import { useState } from "react";
import RowSession from "./RowSession";
import ModalWindow from "../../../ui/ModalWindow";
import ModalSession from "./ModalSession";

const tableHeadRow = [
  "id",
  "name",
  "room",
  "atendee limit",
  "starting date",
  "end date",
  "city",
  "street",
  "building",
];

function SessionBox() {
  const [sessionsArr, setSessionsArr] = useState([]);
  const [showModal, setShowModal] = useState(false);

  function addSession(session) {
    setSessionsArr((prev) => [...prev, session]);
  }

  function removeSession(id) {
    setSessionsArr((prev) => prev.filter((session) => session.id !== id));
  }

  function modifySession(id) {}

  function toggleModal() {
    setShowModal(!showModal);
  }

  return (
    <section>
      <div className="max-h-[800px] w-full overflow-auto border-2 shadow-md sm:rounded-lg">
        <table className=" w-full text-left rtl:text-right">
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
            {sessionsArr.map((session) => (
              <RowSession
                session={session}
                key={session.id}
                removeSession={removeSession}
              />
            ))}
          </tbody>
        </table>

        <button
          onClick={toggleModal}
          className="m-4 rounded-md bg-fmcsGreen px-2 py-1 tracking-wide text-fmcsWhite"
        >
          add session
        </button>
      </div>
      {showModal && (
        <ModalWindow onClose={toggleModal}>
          <ModalSession toggleModal={toggleModal} addSession={addSession} />
        </ModalWindow>
      )}
    </section>
  );
}

export default SessionBox;
