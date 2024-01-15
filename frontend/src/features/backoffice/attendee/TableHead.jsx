function TableHead() {
  const TABLE_HEADERS = ["name", "surname", "email", "affiliation", ""];

  return (
    <thead className="text-xs text-fmcsBlack">
      <tr>
        {TABLE_HEADERS.map((tableHeader, i) => (
          <th scope="col" key={i} className="px-4 py-3">
            {tableHeader}
          </th>
        ))}
      </tr>
    </thead>
  );
}

export default TableHead;
