import Navbar from "./_components/Navbar";
import "./globals.css";

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body>
        <Navbar />
        <div className="py-16 bg-gray-50 overflow-hidden min-h-screen">
          <div className="max-w-7xl mx-auto px-4 space-y-8 sm:px-6 lg:px-8">
            {children}
          </div>
        </div>
      </body>
    </html>
  );
}
