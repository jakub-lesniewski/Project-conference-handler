import { useBackofficeContext } from "../BackofficeContext";
import { useState } from "react";
import AttendeeElement from "./AttendeeElement";
import ModalWindow from "../../../ui/ModalWindow";
import TableHead from "./TableHead";
import AttendeeForm from "./AttendeeForm";
import Button from "../../../ui/Button";

function AttendeesBox() {
  const { attendeesArr } = useBackofficeContext();
  const [toggleModal, setToggleModal] = useState(false);
  const [currentAttendee, setCurrentAttendee] = useState(null);

  function handleToggleModal() {
    console.log(currentAttendee);
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
    <section className="m-4">
      <div className="rouned-lg">
        <table className="mb-4 table-auto text-fmcsBlack shadow-md">
          <TableHead />

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

      <Button onClick={handleToggleModal}>add attendee</Button>

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
