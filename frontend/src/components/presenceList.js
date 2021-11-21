import QRTool from '../utils/QRTool';
import { useParams } from "react-router";
import { useEffect, useState } from 'react';
import { config } from '../constants';
import { useKeycloak } from '@react-keycloak/web';
import { userApi } from '../services/userApi';


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
                setPresenceList(response.data["data"])
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
            <div className="qrContainer">
                {presenceList && isActive && QRTool.generateQR(`${FRONTEND_URL}/validate/${courseID}/${presenceListID}/${presenceList["qrId"]}`) }
                {!isActive && <h1> You no loger can scan QR codes. </h1> }
                {presenceList && isActive && `${FRONTEND_URL}/validate/${courseID}/${presenceListID}/${presenceList["qrId"]}` }
            </div>
            <div className="attendeesTable">
                {/* face requesturi periodice sa ceara tabelul */}
            </div>
        </div>
    );
}
 
export default PresenceList;
 
