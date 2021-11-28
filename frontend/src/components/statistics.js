import { useParams } from "react-router";
import { useEffect, useState } from 'react';
import { config } from '../constants';
import { useKeycloak } from '@react-keycloak/web';
import { Button } from '@mui/material';
import { PresenceListExporter } from './presenceListExporter';
import { userApi } from '../services/userApi';
import { Bar } from "react-chartjs-2";
import html2canvas from "html2canvas";
import Chart from 'chart.js/auto'

import '../styles/chart.css'

const Statistics = () => {
    const { initialized, keycloak } = useKeycloak();
    const {courseID, courseNo} = useParams()
    const [statistics, setStatistics] = useState(null);
    const [totalNumberOfStudents, setTotalNumberOfStudents] = useState(null)
    const [numberOfStudentsPresentAtTheBeginning, setNumberOfStudentsPresentAtTheBeginning] = useState(null)
    const [numberOfStudentsPresentDuringTheCourse, setNumberOfStudentsPresentDuringTheCourse] = useState(null)
    const [numberOfStudentsPresentAtTheEndOfTheCourse, setNumberOfStudentsPresentAtTheEndOfTheCourse] = useState(null)
    const data = {
        labels: ["Total number of students", "Number of students present at the beginning", 
        "Number of students present during the course", "Number of students present at the end of the course"],
        datasets: [
          {
            label: "Label",
            data: [totalNumberOfStudents, numberOfStudentsPresentAtTheBeginning,
                numberOfStudentsPresentDuringTheCourse, numberOfStudentsPresentAtTheEndOfTheCourse],
            backgroundColor: ["#B22222", "#006400", "#00008B", "#FF8C00"]
          }
        ]
    };
    
    // update qr code every 30 seconds for the next 120 second
    useEffect(async () => {
        if (keycloak && initialized) {
            // get statistics from backend
            const response = await userApi.generateStatistics(keycloak.token, courseID, courseNo);
            console.log(response.data);
            const totalNumberOfStudents = await response.data["data"]["totalNumberOfStudents"]
            const numberOfStudentsPresentAtTheBeginning = await response.data["data"]["numberOfStudentsPresentAtTheBeginning"]
            const numberOfStudentsPresentDuringTheCourse = await response.data["data"]["numberOfStudentsPresentDuringTheCourse"]
            const numberOfStudentsPresentAtTheEndOfTheCourse = await response.data["data"]["numberOfStudentsPresentAtTheEndOfTheCourse"]
            setTotalNumberOfStudents(totalNumberOfStudents)
            setNumberOfStudentsPresentAtTheBeginning(numberOfStudentsPresentAtTheBeginning)
            setNumberOfStudentsPresentDuringTheCourse(numberOfStudentsPresentDuringTheCourse)
            setNumberOfStudentsPresentAtTheEndOfTheCourse(numberOfStudentsPresentAtTheEndOfTheCourse)
        }

    }, [keycloak, initialized])


    return (
        <div className="statisticsContainer">
            <br/>
            Statistics generated for week {courseNo}

            <div className="chart">
                <Bar
                    data={data}
                    options={{
                    title: {
                        display: true,
                        text: "Statistics",
                        fontSize: 32
                    },
                    legend: {
                        display: true,
                        position: "right"
                    }
                    }}
                    height={100}
                />
            </div>
        </div>
    );
}
 
export default Statistics;