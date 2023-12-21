import { useForm, useFieldArray } from "react-hook-form";

function EventBox() {
  const { register, control, watch } = useForm({
    defaultValues: {
      eventsArr: [{ name: "Event 1", atendeeLimit: 40 }],
    },
  });

  const { fields, append, remove } = useFieldArray({
    control,
    name: "eventsArr",
  });

  const watchResult = watch("eventsArr");
  console.log(watchResult);

  return (
    <>
      <ul className="flex flex-col gap-2">
        {fields.map((item, index) => {
          return (
            <li key={item.id} className="flex h-8 gap-2">
              <input
                {...register(`eventsArr.${index}.name`, {
                  required: true,
                })}
                placeholder="Name"
                className="rounded-md border-2 px-2 py-1 transition-all duration-300 focus:border-fmcsGreen focus:outline-none focus:ring-fmcsGreen"
              />

              <input
                {...register(`eventsArr.${index}.startDateTime`, {
                  required: true,
                })}
                type="datetime-local"
                placeholder="Start Date and Time"
                className="rounded-md border-2 px-2 py-1 transition-all duration-300 focus:border-fmcsGreen focus:outline-none focus:ring-fmcsGreen"
              />

              <input
                {...register(`eventsArr.${index}.endDateTime`, {
                  required: true,
                })}
                type="datetime-local"
                placeholder="End Date and Time"
                className="rounded-md border-2 px-2 py-1 transition-all duration-300 focus:border-fmcsGreen focus:outline-none focus:ring-fmcsGreen"
              />
              <button
                type="button"
                onClick={() => remove(index)}
                className="rounded-md bg-fmcsGreen px-2 py-1 tracking-wide text-fmcsWhite"
              >
                Delete
              </button>
            </li>
          );
        })}
      </ul>
      <section>
        <button
          type="button"
          onClick={() => {
            append();
          }}
          className="mt-8 rounded-md bg-fmcsGreen px-2 py-1 tracking-wide text-fmcsWhite"
        >
          append
        </button>
      </section>
    </>
  );
}

export default EventBox;
