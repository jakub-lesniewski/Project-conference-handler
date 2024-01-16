import { useBackofficeContext } from "../BackofficeContext";

function TimelineElement({ timelineElement, handleSetCurrentTimelineElement }) {
  const {
    id,
    name,
    room,
    attendeeLimit,
    building,
    street,
    dateStart,
    dateEnd,
  } = timelineElement;
  const { removeTimelineElement } = useBackofficeContext();

  return (
    <tr
      className="cursor-pointer border-t-2 transition-all duration-200 hover:bg-fmcsWhite"
      onClick={() => handleSetCurrentTimelineElement(timelineElement)}
    >
      <td className="p-4">{name}</td>
      <td className="p-4">{room || "---"}</td>
      <td className="p-4">{attendeeLimit || "---"}</td>
      <td className="p-4">{building || "---"}</td>
      <td className="p-4">{street || "---"}</td>
      <td className="p-4">{dateStart}</td>
      <td className="p-4">{dateEnd}</td>
      <td
        onClick={(e) => {
          e.stopPropagation();
          removeTimelineElement(id);
        }}
        className="px-3 py-4 text-center transition-all duration-200 hover:bg-fmcsRed hover:text-fmcsWhite"
      >
        <p>remove</p>
      </td>
    </tr>
  );
}

export default TimelineElement;
