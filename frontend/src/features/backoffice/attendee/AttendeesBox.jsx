import { useBackofficeContext } from "../BackofficeContext";
import { useState } from "react";
import AttendeeElement from "./AttendeeElement";
import ModalWindow from "../../../ui/ModalWindow";
import TableHead from "../TableHead";
import AttendeeForm from "./AttendeeForm";

function AttendeesBox() {
  const { attendeesArr } = useBackofficeContext();
  const [toggleModal, setToggleModal] = useState(false);
  const [currentAttendee, setCurrentAttendee] = useState(null);

  function handleToggleModal() {
    if (toggleModal === true) {
      setCurrentAttendee(null);
    }
    setToggleModal(!toggleModal);
  }

  function handleSetCurrentAttendee(attendee) {
    setCurrentAttendee(attendee);
    handleToggleModal();
  }

  return (
    <section className="m-4 rounded-lg border-4 p-2">
      <button
        className="mb-2 ml-1 mt-3 inline-flex rounded-md border border-fmcsGreen bg-fmcsGreen p-2 text-fmcsWhite transition-all duration-200 hover:bg-fmcsWhite hover:text-fmcsGreen"
        onClick={handleToggleModal}
      >
        add attendee
      </button>

      <div className="relative mb-2 overflow-x-auto rounded-lg shadow-md">
        <table className="w-full border-4 border-opacity-70 text-left text-sm text-fmcsBlack rtl:text-right">
          <TableHead type="attendees" />
          <tbody>
            {attendeesArr.map((attendee) => (
              <AttendeeElement
                attendee={attendee}
                handleSetCurrentAttendee={handleSetCurrentAttendee}
                key={attendee.id}
              />
            ))}
          </tbody>
        </table>
      </div>

      {toggleModal && (
        <ModalWindow onClose={handleToggleModal}>
          <AttendeeForm
            currentAttendee={currentAttendee}
            handleToggleModal={handleToggleModal}
          />
        </ModalWindow>
      )}
    </section>
  );
}

export default AttendeesBox;
