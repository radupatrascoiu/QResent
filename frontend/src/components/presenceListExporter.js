import React from 'react'
import * as FileSaver from "file-saver";
import * as XLSX from "xlsx";
import { Button } from '@mui/material';
import { useKeycloak } from '@react-keycloak/web';
import { userApi } from '../services/userApi';


export const PresenceListExporter = ({ presenceListID }) => {
  const { initialized, keycloak } = useKeycloak();
  const fileType =
    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8";
  const fileExtension = ".xlsx";

  const exportToExcel = async () => {
    if (keycloak && initialized) {
      try {
          // get the presence list
          const response = await userApi.exportPresenceList(keycloak.token, presenceListID);
          const presenceListProjection = await response.data["data"]
          const students = await presenceListProjection["students"]
          students.map((student) => {
            delete student.role
            delete student.courses
          })
          console.log(presenceListProjection)

          // actual export
          const fileName = "presenceList" + Date.now()
          const ws = XLSX.utils.json_to_sheet(students);
          const wb = { Sheets: { data: ws }, SheetNames: ["data"] };
          const excelBuffer = XLSX.write(wb, { bookType: "xlsx", type: "array" });
          const data = new Blob([excelBuffer], { type: fileType });
          FileSaver.saveAs(data, fileName + fileExtension);

      } catch (error) {
          console.log(error);
          alert("Sorry, the presence list can't be exported!")
      }
  }
  }

  return (
    <div className="exportButtonContainer">
      <Button variant="outlined" onClick={(e) => exportToExcel()}>Export presence list now</Button>
    </div>
  );
};