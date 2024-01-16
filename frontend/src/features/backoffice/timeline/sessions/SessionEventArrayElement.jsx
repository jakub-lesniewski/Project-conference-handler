function SessionEventArrayElement({ event }) {
  const { id, type, name, abstract, leadEmail, dateStart, dateEnd } = event;

  return (
    <li className="flex cursor-pointer gap-2 overflow-auto border-b-4 hover:bg-fmcsWhite">
      <p className="w-20 border-r-4 p-1 pr-2 text-center font-bold">{type}</p>
      <p className="min-w-[200px] border-r-4 p-1 pr-2 text-center">{name}</p>
      <p className="min-w-[200px] border-r-4 p-1 pr-2 text-center">
        {leadEmail || "---"}
      </p>
      <p className="border-r-4 p-1 pr-3 font-bold">
        {dateStart.slice(-4)} - {dateEnd.slice(-4)}
      </p>
      <button
        type="button"
        className="ml-auto mr-5 p-1 transition-all duration-200 hover:bg-fmcsRed hover:text-fmcsWhite"
      >
        remove
      </button>
    </li>
  );
}

export default SessionEventArrayElement;
