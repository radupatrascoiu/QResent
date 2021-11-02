import { useEffect } from "react";
import { useHistory, useParams } from "react-router";


const ValidateQR = () => {
    const {courseID, presenceListID, qrcodeID} = useParams()
    const history = useHistory();

    useEffect(async () => {
        // TODO request qr validation  /api/validate/courese/:coureseID/:presenceListID 

        // redirect back to course
        history.push(`/course/${courseID}`);
    }, [])

    return ( <div/> );
}
 
export default ValidateQR ;