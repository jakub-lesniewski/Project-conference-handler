import { useForm } from "react-hook-form";
import InputField from "../../../ui/InputField"; // Import your reusable input field component

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
    <form onSubmit={handleSubmit(onSubmit)}>
      <div className="grid grid-cols-2 gap-5">
        <InputField
          label="Name"
          name="name"
          defaultValue={name}
          register={register}
        />

        <InputField
          label="City"
          name="city"
          defaultValue={city}
          register={register}
        />

        <InputField
          label="Street"
          name="street"
          defaultValue={street}
          register={register}
        />

        <InputField
          label="Building"
          name="building"
          defaultValue={building}
          register={register}
        />

        <InputField
          label="Starting time"
          name="startTime"
          defaultValue={time_start}
          type="datetime-local"
          register={register}
        />

        <InputField
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
  );
}

export default ModalSession;
