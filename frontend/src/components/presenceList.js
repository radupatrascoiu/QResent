import QRTool from '../utils/QRTool';
import { useParams } from "react-router";
import { useEffect, useState } from 'react';
import { config } from '../constants';
import { useKeycloak } from '@react-keycloak/web';


const PresenceList = () => {
    const { initialized, keycloak } = useKeycloak();
    const {courseID, presenceListID} = useParams()
    const [qrObject, setQRObject] = useState(null)
    const [isActive, setIsActive] = useState(true)
    const [intervalHandler, setIntervalHandler] = useState(null)
    const { QR_EXPIRATION_TIME, PRESENCELIST_EXPIRATION_TIME } = config


    // TODO get the QR code and set it
    const updateQR = async () => {
        console.log("A new QR code was generated")
        setQRObject({"id": "12431121"})
    }

    // update qr code every 30 seconds for the next 120 second
    useEffect(async () => {
        if (keycloak && initialized) {
            if (isActive) {
                // get the first qr
                await updateQR()
                // Start updating the qr code every 30 second
                var intervalH = setInterval(async () => {
                    await updateQR()
                }, QR_EXPIRATION_TIME)
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
                {qrObject && isActive && QRTool.generateQR(`https://localhost:8080/validate/${courseID}/${presenceListID}/${qrObject.id}`) }
                {!isActive && <h1> You no loger can scan QR codes. </h1> }
            </div>
            <div className="attendeesTable">
                {/* face requesturi periodice sa ceara tabelul */}
            </div>
        </div>
    );
}
 
export default PresenceList;
 
