function SelectField({
  defaultValue,
  name,
  values,
  required = true,
  register,
}) {
  return (
    <select
      {...register(name)}
      defaultValue={defaultValue}
      required={required}
      className="rounded-md border-2 px-2 py-1 transition-all duration-300 focus:border-fmcsGreen focus:outline-none focus:ring-fmcsGreen"
    >
      {values.map((value, i) => (
        <option value={value} key={i}>
          {value}
        </option>
      ))}
    </select>
  );
}

export default SelectField;
