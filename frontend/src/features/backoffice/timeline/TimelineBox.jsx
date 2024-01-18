import { useBackofficeContext } from "../BackofficeContext";
import { useState } from "react";
import TableHead from "../TableHead";
import ModalWindow from "../../../ui/ModalWindow";
import TimelineElement from "./TimelineElement";
import EventForm from "./events/EventForm";
import SessionForm from "./sessions/SessionForm";

function TimelineBox() {
  const { timelineArr } = useBackofficeContext();
  const [toggleEventModal, setToggleEventModal] = useState(false);
  const [toggleSessionModal, setToggleSessionModal] = useState(false);
  const [currentTimelineElement, setCurrentTimelineElement] = useState(null);

  function handleToggleEventModal() {
    if (toggleEventModal === true) {
      setCurrentTimelineElement(null);
    }
    setToggleEventModal(!toggleEventModal);
  }

  function handleToggleSessionModal() {
    if (toggleSessionModal === true) {
      setCurrentTimelineElement(null);
    }
    setToggleSessionModal(!toggleSessionModal);
  }

  function handleSetCurrentTimelineElement(timelineElement) {
    setCurrentTimelineElement(timelineElement);

    if (!timelineElement.room) {
      handleToggleEventModal();
    } else {
      handleToggleSessionModal();
    }
  }

  return (
    <section className="m-4 rounded-lg border-4 p-2 shadow-md">
      <div className="flex gap-2 pb-2">
        <button
          onClick={handleToggleSessionModal}
          className="ml-1 mt-3 inline-flex rounded-md border border-fmcsGreen bg-fmcsGreen p-2 text-fmcsWhite transition-all duration-200 hover:bg-fmcsWhite hover:text-fmcsGreen"
        >
          add session
        </button>
        <button
          onClick={handleToggleEventModal}
          className="ml-1 mt-3 inline-flex rounded-md border border-fmcsGreen bg-fmcsGreen p-2 text-fmcsWhite transition-all duration-200 hover:bg-fmcsWhite hover:text-fmcsGreen"
        >
          add event
        </button>
      </div>

      <div className="relative mb-2 overflow-x-auto rounded-lg shadow-md">
        <table className="w-full border-4 border-opacity-70 text-left text-sm text-fmcsBlack rtl:text-right">
          <TableHead />
          <tbody>
            {timelineArr.map((element) => (
              <TimelineElement
                timelineElement={element}
                handleSetCurrentTimelineElement={
                  handleSetCurrentTimelineElement
                }
                key={element.id}
              />
            ))}
          </tbody>
        </table>
      </div>

      {toggleSessionModal && (
        <ModalWindow onClose={handleToggleSessionModal}>
          <SessionForm
            currentTimelineElement={currentTimelineElement}
            handleToggleSessionModal={handleToggleSessionModal}
          />
        </ModalWindow>
      )}

      {toggleEventModal && (
        <ModalWindow onClose={handleToggleEventModal}>
          <EventForm
            currentTimelineElement={currentTimelineElement}
            handleToggleEventModal={handleToggleEventModal}
          />
        </ModalWindow>
      )}
    </section>
  );
}

export default TimelineBox;
