function RowAtendee({ atendee, onModify, removeAtendee }) {
  const { id, name, surname, email, affiliation } = atendee;

  function handleModify() {
    onModify(atendee);
  }

  return (
      <tr
          onClick={handleModify}
          className="cursor-pointer border-b text-sm transition-all duration-200 hover:bg-fmcsGray"
      >
        <td className="p-3 text-center">{id}</td>
        <td className="p-3 text-center">{name}</td>
        <td className="p-3 text-center">{surname}</td>
        <td className="p-3 text-center">{email}</td>
        <td className="p-3 text-center">{affiliation}</td>

        <td className="w-18 relative text-center">
          <button
              onClick={(e) => {
                e.stopPropagation();
                removeAtendee(id);
              }}
              className="absolute inset-0 h-full w-24 overflow-hidden px-4 py-2 transition-all duration-200 hover:bg-fmcsRed hover:text-fmcsWhite"
          >
            remove
          </button>
        </td>
      </tr>
  );
}

export default RowAtendee;
