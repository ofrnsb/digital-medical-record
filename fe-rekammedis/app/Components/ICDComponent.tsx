export default function ICDComponent({
  setShowDetail,
  showDetail,
}: {
  setShowDetail: React.Dispatch<React.SetStateAction<IcdResult>>;
  showDetail: IcdResult;
}) {
  const { additionalInformation, definition, exclusions } = showDetail;

  const isAllEmpty = Object.values({
    additionalInformation,
    definition,
    exclusions,
  }).every(
    (value) => value === '' || (Array.isArray(value) && value.length === 0),
  );
  return (
    <div
      className={`min-h-fit p-[10px] text-justify bg-gray-200 w-[600px] absolute top-[168px] rounded-l-[10px] shadow-xl flex flex-col ${
        !showDetail.show
          ? 'right-[-600px]'
          : 'right-[0px] transition-all duration-[1000ms]'
      }
      
      `}
    >
      <div
        className='place-self-end hover:cursor-pointer text-red-400'
        onClick={() => {
          setShowDetail(
            (prev) =>
              ({
                ...prev,
                show: false,
              } as IcdResult),
          );
        }}
      >
        X
      </div>
      {isAllEmpty ? (
        <>
          <strong>no data</strong>
        </>
      ) : (
        <>
          {showDetail.definition ? (
            <div className='mr-[15px]'>
              <strong>Definition:</strong>
              <br />
              {showDetail.definition}
              <br />
            </div>
          ) : null}

          {showDetail.additionalInformation ? (
            <div className='mr-[15px]'>
              <strong>Additional Information:</strong>
              <br />
              {showDetail.additionalInformation}
              <br />
            </div>
          ) : null}

          {showDetail.exclusions && showDetail.exclusions.length > 0 ? (
            <div className='mr-[15px]'>
              <strong>Exclusions</strong>
              <br />
              {showDetail.exclusions.map((item, index) => (
                <p key={index}>{item}</p>
              ))}
            </div>
          ) : null}
        </>
      )}
    </div>
  );
}
