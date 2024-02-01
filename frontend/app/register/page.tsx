"use client";

import React from "react";
import { useForm } from "react-hook-form";
import axiosInstance from "../_config/axios";
import { toast } from "react-toastify";
import { redirect, useRouter } from "next/navigation";

type RegisterForm = {
  username: string;
  password: string;
  firstName: string;
  lastName: string;
};

const Register = () => {
  const { register, handleSubmit } = useForm<RegisterForm>();
  const router = useRouter();

  const handleRegister = async (data: RegisterForm) => {
    const response = await axiosInstance.post("/auth/register", data);

    if (response.status === 200) {
      toast.success("Registered successfully!");
      router.replace("/login");
    } else {
      toast.error("Failed to register");
    }
  };

  return (
    <>
      <div className="text-2xl font-medium mb-4">Register</div>

      <form
        className="flex flex-col space-y-6"
        onSubmit={handleSubmit(handleRegister)}
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

        <div className="sm:col-span-4">
          <label className="block text-sm font-medium leading-6 text-gray-900">
            First name
          </label>
          <div className="mt-2">
            <input
              {...register("firstName")}
              type="text"
              className="block w-1/3 rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-gray-400 sm:text-sm sm:leading-6"
            />
          </div>
        </div>

        <div className="sm:col-span-4">
          <label className="block text-sm font-medium leading-6 text-gray-900">
            Last name
          </label>
          <div className="mt-2">
            <input
              {...register("lastName")}
              type="text"
              className="block w-1/3 rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-gray-400 sm:text-sm sm:leading-6"
            />
          </div>
        </div>

        <button
          type="submit"
          className="py-2 w-1/12 rounded-lg bg-blue-700 text-white text-sm"
        >
          Submit
        </button>
      </form>
    </>
  );
};

export default Register;
