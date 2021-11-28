import { useParams } from "react-router";
import { useEffect, useState } from 'react';
import { config } from '../constants';
import { useKeycloak } from '@react-keycloak/web';
import { Button } from '@mui/material';
import { PresenceListExporter } from './presenceListExporter';
import { userApi } from '../services/userApi';
import { Doughnut } from 'react-chartjs-2';

const Statistics = () => {
    const { initialized, keycloak } = useKeycloak();
    const {courseID, courseNo} = useParams()
    const [statistics, setStatistics] = useState(null);
    
    // update qr code every 30 seconds for the next 120 second
    useEffect(async () => {
        if (keycloak && initialized) {
            // get statistics from backend
            const response = await userApi.generateStatistics(keycloak.token,  courseID, courseNo);
            const statisticsProjection = await response.data["data"]
            const statistics = await statisticsProjection["statistics"]

            console.log();
        }

        // generate chart
        // <Doughnut data={...} />

    }, [keycloak, initialized])


    return (
        <div className="statisticsContainer">
            <br/>
            {/* <div className="qrContainer">

            </div> */}
        </div>
    );
}
 
export default Statistics;