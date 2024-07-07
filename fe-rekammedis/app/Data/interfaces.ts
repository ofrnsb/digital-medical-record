interface Doctor {
  id: number;
  doctorName: string;
}

interface PatientComingDate {
  id: number;
  patientComingDate: string;
  note: string | null;
  diagnose: string | null;
  bloodpressure: string;
  respirationRate: string;
  temparature: string;
  height: string;
  weight: string;
  heartRate: string;
  sickness: Sickness;

  doctor: Doctor;
}

interface Sickness {
  id: number;
  sicknessName: string;
}

interface Patient {
  id: number;
  patientName: string;
  patientAge: number;
  patientGender: string;
  patientPhoneNumber: string;
  patientAddress: string;
  patientHeight: string;
  patientWeight: string;
  patientBloodPressure: string;
  patientRegisteredDate: string;
  comingDate: PatientComingDate[];
}

interface ResponsePayload {
  message: string;
  payload: Patient[];
}

interface ModalsProps {
  setIsShowmodal: React.Dispatch<React.SetStateAction<string>>;
  setRefetch: React.Dispatch<React.SetStateAction<boolean>>;
  isShowmodal: string;
  selectedPatientId: number;
  doctorData: Doctor[];
  isAuthenticaedUser: string | null;
}

interface doctorModal {
  parent: number | null;
  child: number | null;
}

interface patientNoteDiagnose {
  diagnose: string | undefined;
  note: string | undefined;
  patientComingDate: string;
  bloodpressure: string;
  respirationRate: string;
  temparature: string;
  height: string;
  weight: string;
  heartRate: string;
}

interface updateDataBodyRequest {
  patientName: string;
  patientAge: number;
  patientGender: string;
  sicknessName: string;
  doctorName: string;
  note: string;
  diagnose: string;
}

interface IcdResult {
  show: boolean;
  definition: string;
  additionalInformation: string;
  exclusions: string[];
}
