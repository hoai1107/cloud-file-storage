"use client";

import axiosInstance from "../_config/axios";

const FilesPage = () => {
  const getId = async () => {
    const response = await axiosInstance.get("/auth/test");
    console.log(response);
  };

  return (
    <button className=" bg-slate-500 text-white" onClick={getId}>
      Test auth
    </button>
  );
};

export default FilesPage;
