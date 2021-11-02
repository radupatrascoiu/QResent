import { useState } from "react";
import { useEffect } from "react";
import { useParams } from "react-router";
import { userApi } from '../services/userApi';
import { useKeycloak } from '@react-keycloak/web';
import { useMediaQuery } from 'react-responsive'
import { useHistory } from "react-router-dom";



const Course = () => {
    const {courseID} = useParams()
    const history = useHistory();

    const isDesktopOrLaptop = useMediaQuery({
        query: '(min-width: 1224px)'
      })
    const isBigScreen = useMediaQuery({ query: '(min-width: 1824px)' })
    const isTabletOrMobile = useMediaQuery({ query: '(max-width: 1224px)' })
    const isPortrait = useMediaQuery({ query: '(orientation: portrait)' })
    const isRetina = useMediaQuery({ query: '(min-resolution: 2dppx)' })
    
    const [course, setCourse] = useState({
        "id": "1231rd1d2",
        "name": "UBD",
        "professor": "1231411224",
        "information": "Aici se trece",
        "requirements": "Sa nu faci ce face Radu Patrascoiu cu regele Boicea Alexandru.",
        "bonuses": "Mai vrei si bonus ?",
        "schedule": ["Luni, 10:00-12:00", "Marti, 16:00-18:00", "Miercuri, 10:00-12:00", "Joi, 12:00-14:00" ]
    })
    const { initialized, keycloak } = useKeycloak();

    // TODO Load data on mount 
    // useEffect(async () => {
    //     if (keycloak && initialized) {
    //         console.log(keycloak.token)
    //         try {
    //             const response = await userApi.getCourse(keycloak.token, courseID);
    //             setCourse(response.data)
    //         } catch (error) {
    //             console.log(error);
    //         }
    //     }
    // }, [initialized, keycloak]);

    const generatePresenceList = async () => {
        // TODO
        //  make req to generate the presence list
        const presenceList = {
            "id": "2131r1i2j12"
        }
        
        //  redirect to pressence list page
        history.push(`/course/${courseID}/presencelist/${presenceList.id}`);
    }

    const exportPresenceList = async () => {
        // TODO
    }

    return (
        <div>
            { (isDesktopOrLaptop || isBigScreen || isTabletOrMobile || isPortrait) && 
                <div className="courseContainer">
                    {course?.name } <br/>
                    {course?.professor } <br/>
                    {course?.requirements } <br/>
                    {course?.information } <br/>
                    {course?.bonuses } <br/>
                    {course?.schedule } <br/>
                    <button onClick={generatePresenceList}>Generate a presence list</button> <br/>
                    <button>Export presence list</button> <br/>

                </div>
            }

        </div>
    );
}
 
export default Course;