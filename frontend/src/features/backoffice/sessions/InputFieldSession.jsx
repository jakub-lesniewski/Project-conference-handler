function InputFieldSession({
  label,
  name,
  defaultValue,
  type = "text",
  register,
}) {
  return (
    <div className="flex items-center justify-between gap-3">
      <label className="font-medium tracking-wide">{label}</label>
      <input
        {...register(name)}
        defaultValue={defaultValue}
        type={type}
        className="rounded-md border-2 px-2 py-1 transition-all duration-300 focus:border-fmcsGreen focus:outline-none focus:ring-fmcsGreen"
      />
    </div>
  );
}

export default InputFieldSession;
