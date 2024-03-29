function InputField({
  label,
  name,
  defaultValue,
  type = "text",
  register,
  placeholder,
  autoFocus = false,
}) {
  return (
    <div className="flex items-center justify-between gap-3">
      <label className="font-medium tracking-wide">{label}</label>
      <input
        {...register(name)}
        placeholder={placeholder}
        defaultValue={defaultValue}
        type={type}
        className="rounded-md border-2 px-2 py-1 transition-all duration-300 focus:border-fmcsGreen focus:outline-none focus:ring-fmcsGreen"
        autoFocus={autoFocus}
        min="0"
        max="500"
        required
      />
    </div>
  );
}

export default InputField;
