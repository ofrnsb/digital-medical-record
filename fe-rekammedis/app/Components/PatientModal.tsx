import axios from 'axios';
import { useEffect, useState } from 'react';
import { PROD_URL } from '../Data/URL';

export default function PatientModal({
  userData,
  setShowPatientData,
  setUserData2,
  isAuthenticaedUser,
}: {
  userData: Patient;
  setShowPatientData: React.Dispatch<React.SetStateAction<boolean>>;
  setUserData2: React.Dispatch<React.SetStateAction<Patient[]>>;
  isAuthenticaedUser: string | null;
}) {
  const [comingDate, setComingDate] = useState(userData.comingDate);
  const [activeNoteId, setActiveNoteId] = useState<doctorModal>({
    parent: null,
    child: null,
  });
  const [noteDiagnose, setNoteDiagnose] = useState<patientNoteDiagnose>({
    diagnose: '',
    note: '',
    patientComingDate: '',
    bloodpressure: '',
    respirationRate: '',
    temparature: '',
    height: '',
    weight: '',
    heartRate: '',
  });

  useEffect(() => {
    setComingDate(userData.comingDate);
  }, [userData.comingDate]);

  const handleInputChange = (index: number, value: string) => {
    const newcomingDate = [...comingDate];
    newcomingDate[index].sickness.sicknessName = value;
    setComingDate(newcomingDate);
  };

  const handleShowNote = (
    id: number,
    index: number,
    data: patientNoteDiagnose,
  ) => {
    setNoteDiagnose(data);

    setActiveNoteId({ parent: index, child: id });
  };

  const updateData = (_id: number, index: number) => {
    axios
      .put(
        `${PROD_URL}/patient/update/${userData.id}?comingDateId=${_id}`,
        {
          patientName: userData.patientName,
          patientAge: userData.patientAge,
          patientGender: userData.patientGender,
          sicknessName: comingDate[index].sickness.sicknessName,
          note: noteDiagnose.note,
          diagnose: noteDiagnose.diagnose,
        },
        {
          headers: { Authorization: `Bearer ${isAuthenticaedUser}` },
        },
      )
      .then(() => {
        axios
          .get(`${PROD_URL}/patient/secondStep`, {
            headers: { Authorization: `Bearer ${isAuthenticaedUser}` },
          })
          .then((res) => {
            setUserData2(res.data.payload);
          })
          .catch((error) => {});
        setShowPatientData(false);
      });
  };

  return (
    <div className='absolute w-screen h-screen flex justify-center items-center'>
      <div className='absolute w-screen h-screen bg-gray-500 opacity-[0.5]' />
      <div className='bg-white h-min w-[500px] opacity-[1] z-10 rounded-[20px] flex justify-center items-center p-[20px]'>
        <div className='h-full w-full'>
          <div className='flex flex-col mb-[10px]'>
            <button
              onClick={() => {
                setShowPatientData(false);
              }}
              className='h-[20px] w-[20px] leading-[20px] place-self-end text-red-400 hover:font-bold hover:font-bold text-lg'
            >
              x
            </button>
            <span className='font-extralight leading-none'>
              {userData.patientName} ({userData.patientAge})
            </span>
          </div>

          <div className='flex flex-col gap-[10px] justify-center'>
            {comingDate.map((data, index) => (
              <div key={data.id} className='bg-blue-200 p-[10px] rounded-[5px]'>
                <input
                  className={`border-[0px] rounded-[5px] bg-transparent focus:outline-none font-extralight ${
                    data.sickness.sicknessName === 'null'
                      ? 'border-[1px] p-[5px]'
                      : ''
                  }`}
                  type='text'
                  value={data.sickness.sicknessName}
                  onChange={(e) => handleInputChange(index, e.target.value)}
                />

                <div key={index}>
                  <div
                    className='bg-blue-300 mt-[5px] p-[5px] rounded-[5px] hover:bg-blue-400 cursor-pointer font-semibold'
                    onClick={() => {
                      handleShowNote(
                        data.id,
                        index,
                        data as patientNoteDiagnose,
                      );
                    }}
                  >
                    {data.patientComingDate}
                  </div>
                  {activeNoteId.parent == index &&
                    activeNoteId.child == data.id && (
                      <div className='bg-blue-400 mt-[5px] p-[5px] rounded-[5px] hover:bg-blue-500 cursor-pointer flex gap-[5px] flex-col'>
                        <div className='leading-none font-bold'>
                          {data.doctor.doctorName}
                        </div>
                        <div className='grid grid-cols-3 font-extralight'>
                          <p>BP: {data.bloodpressure} </p>

                          <p>RT: {data.respirationRate} </p>

                          <p>HR: {data.heartRate} </p>

                          <p>Temp: {data.temparature} </p>

                          <p>Height: {data.height} </p>

                          <p>Weight: {data.weight} </p>
                        </div>
                        <textarea
                          onChange={(e) => {
                            let newNoteDiagnose = { ...noteDiagnose };
                            newNoteDiagnose.diagnose = e.target.value;
                            setNoteDiagnose(newNoteDiagnose);
                          }}
                          className='border-[0px] w-full h-[80px] border-[1px] rounded-[5px] bg-transparent focus:outline-none p-[5px] text-extralight'
                          value={noteDiagnose.diagnose ?? ''}
                          placeholder='Diagnose'
                          readOnly={data.diagnose !== null}
                        />
                        <textarea
                          onChange={(e) => {
                            let newNoteDiagnose = { ...noteDiagnose };
                            newNoteDiagnose.note = e.target.value;
                            setNoteDiagnose(newNoteDiagnose);
                          }}
                          className='border-[0px] w-full h-[80px] border-[1px] rounded-[5px] bg-transparent focus:outline-none p-[5px] text-extralight'
                          value={noteDiagnose.note ?? ''}
                          placeholder='Note'
                          readOnly={data.note !== null}
                        />

                        <button
                          onClick={() => {
                            updateData(data.id, index);
                          }}
                          className='text-extralight bg-green-200 rounded-[5px] py-[5px] '
                        >
                          Update
                        </button>
                      </div>
                    )}
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
