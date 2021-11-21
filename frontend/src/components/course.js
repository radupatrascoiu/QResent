import { useState } from "react";
import { useEffect } from "react";
import { useParams } from "react-router";
import { userApi } from '../services/userApi';
import { useKeycloak } from '@react-keycloak/web';
import { useMediaQuery } from 'react-responsive'
import { useHistory } from "react-router-dom";
import {Button} from "@mui/material"


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
    
    const [course, setCourse] = useState(null)
    const { initialized, keycloak } = useKeycloak();

    // Load data on mount 
    useEffect(async () => {
        if (keycloak && initialized) {
            try {
                const response = await userApi.getCourse(keycloak.token, courseID);
                setCourse(response.data["data"])
            } catch (error) {
                console.log(error);
            }
        }
    }, [initialized, keycloak]);

    const generatePresenceList = async () => {
        if (keycloak && initialized && course != null) {
            try {
                const response = await userApi.generatePresenceList(keycloak.token, courseID);
                const presenceListId = response.data["data"]
                console.log(`Presence list ${presenceListId} generated.`)

                // redirect to presence list page
                history.push(`/course/${courseID}/presencelist/${presenceListId}`);
            } catch (error) {
                console.log(error);
            }
        }
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
                    <Button onClick={generatePresenceList}>Generate a presence list</Button> <br/>
                    <Button>Export presence list</Button> <br/>

                </div>
            }

        </div>
    );
}
 
export default Course;