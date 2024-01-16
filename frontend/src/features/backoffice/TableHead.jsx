function TableHead({ type }) {
  const TABLE_HEADERS_ATTENDEES = [
    "name",
    "surname",
    "email",
    "affiliation",
    "",
  ];

  const TABLE_HEADERS_TIMELINE = [
    "name",
    "room",
    "attendee limit",
    "building",
    "street",
    "start date",
    "end date",
    "",
  ];

  const tableHeaders =
    type === "attendees" ? TABLE_HEADERS_ATTENDEES : TABLE_HEADERS_TIMELINE;

  return (
    <thead className="text-xs text-fmcsBlack">
      <tr>
        {tableHeaders.map((tableHeader, i) => (
          <th scope="col" key={i} className="px-4 py-3">
            {tableHeader}
          </th>
        ))}
      </tr>
    </thead>
  );
}

export default TableHead;
