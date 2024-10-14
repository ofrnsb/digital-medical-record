'use client';
import axios from 'axios';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';
import LoadingBar from '../Components/LoadingBar';
import Modals from '../Components/Modals';
import PatientModal from '../Components/PatientModal';
import { PROD_VM } from '../Data/URL';

export default function HomeDashboard() {
  const [userData, setUserData] = useState<Patient[]>([]);
  const [userData2, setUserData2] = useState<Patient[]>([]);
  const [patientType, setpatientType] = useState<string>('new');
  const [isShowmodal, setIsShowmodal] = useState<string>('close');
  const [refetch, setRefetch] = useState<boolean>(false);
  const [selectedPatientId, setSelectedPatientId] = useState<number>(0);
  const [showPatientData, setShowPatientData] = useState<boolean>(false);
  const { push } = useRouter();
  const [isLoading, setIsLoading] = useState<boolean>(false);

  let isAuthenticaedUser: string | null = null;
  if (typeof window !== 'undefined') {
    isAuthenticaedUser = localStorage.getItem('jwt');
  }

  const [doctorData, setDoctorData] = useState<Doctor[]>([]);
  let patientId = 0;

  const Logout = () => {
    localStorage.removeItem('jwt');
    localStorage.removeItem('token');
    push('/login');
  };

  useEffect(() => {
    setIsLoading(true);
    if (!isAuthenticaedUser) {
      push('/login');
    } else {
      axios
        .get(`${PROD_VM}/patient/firstStep`, {
          headers: { Authorization: `Bearer ${isAuthenticaedUser}` },
        })
        .then((res) => {
          setIsLoading(false);
          setUserData(res.data.payload);
        });
    }
  }, [refetch]);

  const onProcessPatient = (index: number) => {
    setIsLoading(true);
    setSelectedPatientId(index);
    setIsShowmodal('processPatient');
    axios
      .get(`${PROD_VM}/doctor`, {
        headers: { Authorization: `Bearer ${isAuthenticaedUser}` },
      })
      .then((res) => {
        setIsLoading(false);
        setDoctorData(res.data.payload);
      })
      .catch(() => {
        setIsLoading(false);
      });
  };

  const onFirstProcessPatient = () => {
    setIsLoading(true);
    axios
      .get(`${PROD_VM}/patient/firstStep`, {
        headers: { Authorization: `Bearer ${isAuthenticaedUser}` },
      })
      .then((res) => {
        setIsLoading(false);
        setUserData(res.data.payload);
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
        setIsLoading(false);
      });
  };

  const onSecondProcessPatient = () => {
    setIsLoading(true);
    axios
      .get(`${PROD_VM}/patient/secondStep`, {
        headers: { Authorization: `Bearer ${isAuthenticaedUser}` },
      })
      .then((res) => {
        setIsLoading(false);
        setUserData2(res.data.payload);
        console.log(res.data.payload);
      })
      .catch((error) => {
        setIsLoading(false);
        console.error('Error fetching data:', error);
      });
  };

  const onThirdStepPatient = () => {
    setIsLoading(true);
    axios
      .get(`${PROD_VM}/patient/thirdStep`, {
        headers: { Authorization: `Bearer ${isAuthenticaedUser}` },
      })
      .then((res) => {
        setIsLoading(false);
        setUserData2(res.data.payload);
      })
      .catch((error) => {
        setIsLoading(false);
        console.error('Error fetching data:', error);
      });
  };

  return !isAuthenticaedUser ? null : (
    <main className='flex flex-col items-center justify-between min-h-screen'>
      <nav className='bg-gray-200 p-[10px] w-full h-[60px] flex items-center justify-between'>
        <button
          className='bg-red-100 rounded-md w-[125px] h-[30px] ml-auto mr-[10px]'
          onClick={Logout}
        >
          Logout
        </button>
      </nav>

      <div className='flex-1 w-full flex items-center justify-center'>
        <section className='bg-gray-300 w-[250px] h-screen '>
          <div className='flex flex-col gap-[10px]'>
            <Link
              href='/home'
              className=' p-[5px] hover:bg-white hover:cursor-pointer'
            >
              Patient Data
            </Link>
            <Link
              className=' p-[5px] hover:bg-white hover:cursor-pointer'
              href='/alldata'
            >
              All Data
            </Link>

            <Link
              href='/ICD'
              className=' p-[5px] hover:bg-white hover:cursor-pointer'
            >
              ICD
            </Link>
          </div>
        </section>
        <section className='flex-1 h-screen p-[20px]'>
          <div className='flex justify-between'>
            <div className='flex gap-[10px]'>
              <button
                onClick={() => {
                  setpatientType('new');
                  onFirstProcessPatient();
                }}
                className='bg-teal-100 rounded-md w-[125px] h-[30px] mb-[20px]'
              >
                Appointments
              </button>
              <button
                onClick={() => {
                  setpatientType('waiting');
                  onSecondProcessPatient();
                }}
                className='bg-teal-300 rounded-md w-[125px] h-[30px] mb-[20px]'
              >
                Waiting
              </button>
              <button
                onClick={() => {
                  setpatientType('done');
                  onThirdStepPatient();
                }}
                className='bg-blue-200 rounded-md w-[125px] h-[30px] mb-[20px]'
              >
                Patients Data
              </button>
            </div>

            {patientType === 'new' ? (
              <>
                <button
                  onClick={() => {
                    setIsShowmodal('addPatient');
                  }}
                  className='bg-green-300 rounded-md w-[125px] h-[30px] mb-[20px]'
                >
                  Add Patient
                </button>
              </>
            ) : null}
          </div>

          {patientType === 'new' ? (
            <table>
              <thead>
                <tr>
                  <th>No</th>
                  <th>Name</th>
                  <th>Age</th>
                  <th>Gender</th>
                  <th>Phone Number</th>
                  <th>Address</th>
                  <th>Registered Date</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {userData.map((data) => {
                  patientId++;
                  return (
                    <tr key={data.id}>
                      <td>{patientId}</td>
                      <td>{data.patientName}</td>
                      <td>{data.patientAge}</td>
                      <td>{data.patientGender}</td>
                      <td>{data.patientPhoneNumber}</td>
                      <td>{data.patientAddress}</td>
                      <td>{data.patientRegisteredDate}</td>
                      <td>
                        <button
                          onClick={() => {
                            onProcessPatient(data.id);
                          }}
                          className='bg-red-300 rounded-md w-[125px] h-[30px]'
                        >
                          Process
                        </button>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          ) : patientType === 'waiting' ? (
            <table>
              <thead>
                <tr>
                  <th>No</th>
                  <th>Name</th>
                  <th>Age</th>
                  <th>Gender</th>
                  <th>Coming Date</th>
                  <th>Sickness</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {userData2.map((data, id) => {
                  {
                    patientId++;
                    return (
                      <tr key={data.id}>
                        <td>{patientId}</td>
                        <td>{data.patientName}</td>
                        <td>{data.patientAge}</td>
                        <td>{data.patientGender}</td>
                        <td>
                          {
                            data.comingDate[data.comingDate.length - 1]
                              .patientComingDate
                          }
                        </td>
                        <td>
                          {data.comingDate[data.comingDate.length - 1].sickness
                            .sicknessName == 'null'
                            ? '-'
                            : data.comingDate[data.comingDate.length - 1]
                                .sickness.sicknessName}
                        </td>
                        <td>
                          <button
                            onClick={() => {
                              setSelectedPatientId(id);

                              setShowPatientData(true);
                            }}
                            className='bg-green-300 rounded-md w-[125px] h-[30px]'
                          >
                            Update
                          </button>
                        </td>
                      </tr>
                    );
                  }
                })}
              </tbody>
            </table>
          ) : patientType === 'done' ? (
            <table>
              <thead>
                <tr>
                  <th>No</th>
                  <th>Name</th>
                  <th>Age</th>
                  <th>Gender</th>
                  <th>Coming Date</th>
                  <th>Sickness</th>
                  {/* <th>Action</th> */}
                </tr>
              </thead>
              <tbody>
                {userData2.map((data) => {
                  {
                    patientId++;
                    return (
                      <tr key={data.id}>
                        <td>{patientId}</td>
                        <td>{data.patientName}</td>
                        <td>{data.patientAge}</td>
                        <td>{data.patientGender}</td>
                        <td>
                          {
                            data?.comingDate[data.comingDate.length - 1]
                              ?.patientComingDate
                          }
                        </td>
                        <td>
                          {data?.comingDate[data.comingDate.length - 1]
                            ?.sickness.sicknessName == 'null'
                            ? '-'
                            : data?.comingDate[data.comingDate.length - 1]
                                ?.sickness.sicknessName}
                        </td>
                        {/* <td>
                          <button
                            onClick={() => {
                              setSelectedPatientId(data.id);
                              console.log(data.id);
                              setShowPatientData(true);
                            }}
                            className='bg-gray-300 rounded-md w-[125px] h-[30px]'
                            disabled
                          >
                            Update
                          </button>
                        </td> */}
                      </tr>
                    );
                  }
                })}
              </tbody>
            </table>
          ) : null}

          {/* <footer className='mt-[20px] flex items-center justify-between'>
            <p>showing 1 of 20 pages</p>
            <span className='flex items-center justify-between gap-[10px]'>
              <p>prev</p>
              <p>1</p>
              <p>next</p>
            </span>
          </footer> */}
        </section>
      </div>

      {isShowmodal !== 'close' ? (
        <>
          <Modals
            setIsShowmodal={setIsShowmodal}
            setRefetch={setRefetch}
            isShowmodal={isShowmodal}
            selectedPatientId={selectedPatientId}
            doctorData={doctorData}
            isAuthenticaedUser={isAuthenticaedUser}
          />
        </>
      ) : null}

      {showPatientData ? (
        <>
          <PatientModal
            userData={userData2[selectedPatientId]}
            setShowPatientData={setShowPatientData}
            setUserData2={setUserData2}
            isAuthenticaedUser={isAuthenticaedUser}
          />
        </>
      ) : null}

      {isLoading && <LoadingBar />}
    </main>
  );
}
