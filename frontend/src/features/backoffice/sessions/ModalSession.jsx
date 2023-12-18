import { useForm } from "react-hook-form";
import InputFieldSession from "./InputFieldSession"; // Import your reusable input field component

function ModalSession({ session, toggleModal }) {
  const { register, handleSubmit } = useForm();

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

  function onSubmit(data) {
    // toggleModal(false);
    console.log(data);
  }

  return (
    <div>
      <form onSubmit={handleSubmit(onSubmit)}>
        <div className="grid grid-cols-2 gap-5">
          <InputFieldSession
            label="Name"
            name="name"
            defaultValue={name}
            register={register}
          />

          <InputFieldSession
            label="City"
            name="city"
            defaultValue={city}
            register={register}
          />

          <InputFieldSession
            label="Street"
            name="street"
            defaultValue={street}
            register={register}
          />

          <InputFieldSession
            label="Building"
            name="building"
            defaultValue={building}
            register={register}
          />

          <InputFieldSession
            label="Starting time"
            name="startTime"
            defaultValue={time_start}
            type="datetime-local"
            register={register}
          />

          <InputFieldSession
            label="Ending time"
            name="endTime"
            defaultValue={time_end}
            type="datetime-local"
            register={register}
          />
        </div>

        <button className="mt-5 rounded-md bg-fmcsGreen px-2 py-1 tracking-wide text-fmcsWhite">
          submit changes
        </button>
      </form>
    </div>
  );
}

export default ModalSession;
