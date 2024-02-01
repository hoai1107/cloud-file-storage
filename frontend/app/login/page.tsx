"use client";

import { useForm } from "react-hook-form";
import axiosInstance from "../_config/axios";
import { useRouter } from "next/navigation";

type LoginForm = {
  username: string;
  password: string;
};

const Login = () => {
  const { register, handleSubmit } = useForm<LoginForm>();
  const router = useRouter();

  const handleLogin = async (data: LoginForm) => {
    const response = await axiosInstance.post("/auth/login", data);
    console.log(response);

    router.replace("/files");
  };

  return (
    <>
      <div className="text-2xl font-medium mb-4">Log in</div>

      <form
        className="flex flex-col space-y-6"
        onSubmit={handleSubmit(handleLogin)}
      >
        <div className="sm:col-span-4">
          <label className="block text-sm font-medium leading-6 text-gray-900">
            Username
          </label>
          <div className="mt-2">
            <input
              {...register("username")}
              type="text"
              className="block w-1/3 rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-gray-400 sm:text-sm sm:leading-6"
            />
          </div>
        </div>

        <div className="sm:col-span-4">
          <label className="block text-sm font-medium leading-6 text-gray-900">
            Password
          </label>
          <div className="mt-2">
            <input
              {...register("password")}
              type="password"
              className="block w-1/3 rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-gray-400 sm:text-sm sm:leading-6"
            />
          </div>
        </div>

        <button
          type="submit"
          className="py-2 w-1/12 rounded-lg bg-blue-700 text-white text-sm"
        >
          Log in
        </button>
      </form>
    </>
  );
};

export default Login;
