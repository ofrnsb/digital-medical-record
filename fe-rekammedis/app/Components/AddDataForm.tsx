export default function AddDataForm({
  addDataForm,
}: {
  addDataForm: { type: string; form: React.JSX.Element } | null;
}) {
  return <>{addDataForm?.form}</>;
}
