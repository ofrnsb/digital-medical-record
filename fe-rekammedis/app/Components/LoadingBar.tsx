export default function LoadingBar() {
  return (
    <div className='absolute top-0 left-0 w-screen h-screen flex items-center justify-center bg-gray-800 opacity-[50%] z-50'>
      <div className='animate-spin h-[50px] w-[50px] rounded-[20px] border-[5px] border-white'></div>
    </div>
  );
}
