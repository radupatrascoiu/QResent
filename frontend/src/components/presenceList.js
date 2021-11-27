import QRTool from '../utils/QRTool';
import { useParams } from "react-router";
import { useEffect, useState } from 'react';
import { config } from '../constants';
import { useKeycloak } from '@react-keycloak/web';
import { Button } from '@mui/material';
import { PresenceListExporter } from './presenceListExporter';
import { userApi } from '../services/userApi';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import Timer from 'react-compound-timer';
import Box from '@mui/material/Box';

import '../styles/presenceList.css';

const PresenceList = () => {
    const { initialized, keycloak } = useKeycloak();
    const {courseID, presenceListID} = useParams()
    const [presenceList, setPresenceList] = useState(null)
    const [isActive, setIsActive] = useState(true)
    const [intervalHandler, setIntervalHandler] = useState(null)
    const { QR_EXPIRATION_TIME, PRESENCELIST_EXPIRATION_TIME, QR_FETCH_INTERVAL, FRONTEND_URL } = config
    
    // get the QR code and set it
    const fetchQR = async () => {        
        if (keycloak && initialized) {
            try {
                const response = await userApi.getPresenceList(keycloak.token, presenceListID);
                setPresenceList(await response.data["data"])
                if (response.data != null && new Date() > new Date(response.data["data"]["timestampClosed"]))  setIsActive(false)
            } catch (error) {
                console.log(error);
            }
        }
    }

    // update qr code every 30 seconds for the next 120 second
    useEffect(async () => {
        if (keycloak && initialized) {
            if (isActive) {
                // get the first qr
                await fetchQR()
                // Start updating the qr code every 30 second
                var intervalH = setInterval(async () => {
                    await fetchQR()
                }, QR_FETCH_INTERVAL)
                setIntervalHandler(intervalH)
                // Count down presence list remaining time
                setTimeout(() => { setIsActive(false); console.log("Pressence list expired")}, PRESENCELIST_EXPIRATION_TIME + QR_EXPIRATION_TIME)
            } else {
                // stop getting the qr code
                clearInterval(intervalHandler)
            }
        }
    }, [isActive, keycloak, initialized])


    return (
        <div className="presenceListContainer">
            <br/>
            {isActive && <div className="qrInfoContainer">
                <Card sx={{ maxWidth: "100%" }}>
                    <CardContent>
                        <div className="textContainer">
                        <Typography variant="h3" gutterBottom component="div">
                            Confirm Course Presence
                        </Typography>
                        <Typography variant="body1" gutterBottom>
                            Please scan the QR code on the right in order to confirm your presence for today's course.
                        </Typography>
                        <Typography variant="body1" gutterBottom>
                            Thank you!
                        </Typography>
                        <Typography variant="body1" gutterBottmo>
                            Time left to confirm your presence:
                            {" "}
                            <div className="timerContainer">
                                <Box sx={{border: 1}}>
                                    <Timer initialTime={90000} direction="backward">
                                        <Timer.Minutes/>
                                        {":"}
                                        <Timer.Seconds/>
                                    </Timer>
                                </Box>
                            </div>
                        </Typography>
                        <br></br>
                            {presenceList && isActive && `${FRONTEND_URL}/validate/${courseID}/${presenceListID}/${presenceList["qrId"]}` }
                        </div>
                        <div className="qrContainer">
                            {presenceList && isActive && QRTool.generateQR(`${FRONTEND_URL}/validate/${courseID}/${presenceListID}/${presenceList["qrId"]}`, {
                                "size": 250,
                            })}
                        </div>
                    </CardContent>
                </Card>
            </div>}
            {!isActive && <div className="presenceTimeoutContainer">
                <Card sx={{maxWidth: "100%"}}>
                    <CardContent>
                        <Typography variant="h3" gutterBottom component="div">
                            Presence confirmation process finished
                        </Typography>
                        <Typography variant="body1" gutterBottom component="div">
                            Thank you for using QResent to confirm today's course presence. Please use the button below to export the presence list.
                        </Typography>
                        <PresenceListExporter presenceListID={presenceListID}/>
                    </CardContent>
                </Card>
            </div>}
        </div>
    );
}
 
export default PresenceList;
 
