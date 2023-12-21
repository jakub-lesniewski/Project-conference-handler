import { useForm, useFieldArray } from "react-hook-form";
import InputField from "../../../ui/InputField";
import { useState } from "react";

function ModalSession({ session, toggleModal, addSession }) {
  const { register, handleSubmit, control, watch } = useForm({
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

  function onSubmit(data) {
    const sessionData = data;

    const newSession = {
      name: sessionData.name,
      room: sessionData.room,
      atendeeLimit: sessionData.atendeeLimit,
      startingDate: sessionData.startingDate,
      endingDate: sessionData.endingDate,
      city: sessionData.city,
      street: sessionData.street,
      building: sessionData.building,
      eventsArr: data.eventsArr,
    };

    addSession(newSession);
    console.log(newSession);
    toggleModal();
  }

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="h-[500px]">
      <section className="grid grid-cols-3 gap-4">
        <div className="flex flex-col gap-2">
          <InputField
            defaultValue={session?.name}
            label="Name"
            placeholder="De revolutionibus orbium coelestium"
            name="name"
            register={register}
          />

          <InputField
            defaultValue={session?.room}
            label="Room"
            placeholder="C201"
            name="room"
            register={register}
          />

          <InputField
            label="Atendee limit"
            type="number"
            name="atendeeLimit"
            register={register}
          />
        </div>

        <div className="flex flex-col gap-2">
          <InputField
            defaultValue={session?.city}
            label="City"
            placeholder="Łódź"
            name="city"
            register={register}
          />

          <InputField
            defaultValue={session?.street}
            label="Street"
            placeholder="Stefana Banacha 22"
            name="street"
            register={register}
          />

          <InputField
            defaultValue={session?.building}
            label="Building"
            placeholder="Faculty of Mathematics and Computer Science, University of Lodz"
            name="building"
            register={register}
          />
        </div>

        <div className="flex flex-col gap-2">
          <InputField
            defaultValue={session?.startingDate}
            label="Starting date"
            name="startingDate"
            type="datetime-local"
            register={register}
          />

          <InputField
            defaultValue={session?.endingDate}
            label="Ending date"
            name="endingDate"
            type="datetime-local"
            register={register}
          />
        </div>
      </section>

      <section className="overflow-auto-y mt-12 border border-fmcsBlack p-2">
        <ul className="gap-2g flex flex-col">
          {fields.map((item, index) => {
            return (
              <li key={index} className="flex h-8 gap-2">
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
            Append
          </button>
        </section>
      </section>

      <button className="mt-8 rounded-md bg-fmcsGreen px-2 py-1 tracking-wide text-fmcsWhite">
        Submit Changes
      </button>
    </form>
  );
}

export default ModalSession;
