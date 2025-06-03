export default function TestPage() {
  return (
    <div>
      <h1>Test Page</h1>
      <p>This is a test page to verify the setup.</p>
      <p>1 - {JSON.stringify(process.env.NEXT_PUBLIC_API_URL)}</p>
      <p>2 - {JSON.stringify(process.env.API_URL)}</p>
      <p>3 - {JSON.stringify(process.env)}</p>
    </div>
  );
}