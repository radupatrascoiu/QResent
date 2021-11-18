import { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router";
import { useKeycloak } from '@react-keycloak/web';
import { userApi } from '../services/userApi';


const ValidateQR = () => {
    const {courseID, presenceListID, qrcodeID} = useParams()
    const history = useHistory();
    const { initialized, keycloak } = useKeycloak();
    const [validationStatus, setValidationStatus] = useState("Loading...")

    useEffect(async () => {
        // request qr validation  /api/validate/courese/:coureseID/:presenceListID 
        if (keycloak && initialized) {
            try {
                const response = await userApi.recordQR(keycloak.token, courseID, presenceListID, qrcodeID);
                setValidationStatus(`${response.data["message"]} You ll be redirected back soon...`)

                setTimeout(() => {
                    history.push(`/course/${courseID}`);
                }, 8000)
            
            } catch (error) {
                setValidationStatus(error.data["data"])
                console.log(error);
            }
        }
    }, [keycloak, initialized])

    return ( <div>
        <h1>{validationStatus}</h1> 
    </div>);
}
 
export default ValidateQR ;