"use client";

import { ChangeEvent, useState } from "react";
import axiosInstance from "../_config/axios";
import {
  PresignedUrlResponse,
  UploadPresignedUrlRequest,
} from "@/types/presignedUrl";
import { Cookies, useCookies } from "react-cookie";
import axios from "axios";
import { get } from "http";
import ListFiles from "./_components/ListFiles";
import { FileObject } from "@/types/files";

const FilesPage = () => {
  const [files, setFiles] = useState<FileObject[]>([]);
  const [cookies] = useCookies(["access-token"]);

  // @ts-ignore
  const jwtToken = cookies.jwt;

  const getListFiles = async () => {
    const response = await axiosInstance.get<FileObject[]>("/files", {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    });

    setFiles(response.data);
  };

  const handleFileUpload = async (event: ChangeEvent<HTMLInputElement>) => {
    const file: File = event.target.files![0];

    const requestBody: UploadPresignedUrlRequest = {
      fileName: file.name,
      fileType: file.name.split(".").pop()!,
    };

    const response = await axiosInstance.post<PresignedUrlResponse>(
      "/files/upload",
      requestBody,
      {
        headers: {
          Authorization: `Bearer ${jwtToken}`,
        },
      }
    );

    const presignedUrl = response.data.url;

    axios
      .put(presignedUrl, file, {
        headers: {
          "Content-Type": file.type,
        },
      })
      .then(() => {
        getListFiles();
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <>
      <div className="flex items-center justify-between py-2">
        <h2 className="text-3xl font-semibold">FILES</h2>

        <label className="flex items-center px-4 py-2 bg-blue-500 text-white font-semibold rounded cursor-pointer hover:bg-blue-600">
          <span>Add File</span>
          <input type="file" className="hidden" onChange={handleFileUpload} />
        </label>
      </div>
      <ListFiles files={files} />
    </>
  );
};

export default FilesPage;
