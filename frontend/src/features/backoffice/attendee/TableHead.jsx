function TableHead() {
  const TABLE_HEADERS = ["name", "surname", "email", "affiliation", ""];

  return (
    <thead>
      <tr className="border border-black bg-fmcsGray text-sm">
        {TABLE_HEADERS.map((tableHeader, i) => (
          <th key={i} className="px-4 py-2 text-left">
            {tableHeader}
          </th>
        ))}
      </tr>
    </thead>
  );
}

export default TableHead;
