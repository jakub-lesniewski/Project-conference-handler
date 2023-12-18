function RowSession({ session, removeSession, setCurrentSession, onClick }) {
  const {
    id,
    name,
    room_number,
    time_start,
    time_end,
    city,
    street,
    building,
  } = session;

  return (
    <tr
      onClick={() => {
        setCurrentSession(session);
        onClick();
      }}
      className="cursor-pointer border-b text-sm transition-all duration-200 hover:bg-fmcsGray"
    >
      <td className="p-3 text-center">{id}</td>
      <td className="p-3 text-center">{name}</td>
      <td className="p-3 text-center">{room_number}</td>
      <td className="p-3 text-center">{time_start}</td>
      <td className="p-3 text-center">{time_end}</td>
      <td className="p-3 text-center">{city}</td>
      <td className="p-3 text-center">{street}</td>
      <td className="p-3 text-center">{building}</td>
      <td className="w-18 relative text-center">
        <button
          onClick={(e) => {
            e.stopPropagation();
            removeSession(id);
          }}
          className="absolute inset-0 h-full w-24 overflow-hidden px-4 py-2 transition-all duration-200 hover:bg-fmcsRed hover:text-fmcsWhite"
        >
          remove
        </button>
      </td>
    </tr>
  );
}

export default RowSession;
