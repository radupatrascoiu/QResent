import * as React from 'react';
import { useEffect, useState, useRef, useCallback } from 'react';
import { useKeycloak } from '@react-keycloak/web';
import { userApi } from '../services/userApi';
import AuthorizedFunction from '../utils/authorization';
import { Link } from 'react-router-dom';

import Button from '@mui/material/Button';


const CourseButton = (props) => {
    const { initialized, keycloak } = useKeycloak();
    const [error, setError] = useState(false);
    const [enrolled, setEnrolled] = useState(props.enrolled);
    const [isSending, setIsSending] = useState(false);
    const [isProfessor, setIsProfessor] = useState(false);
    const [isCourseTitular, setIsCourseTitular] = useState(false);

    useEffect(() => {
        if (keycloak && initialized) {
            setIsCourseTitular(keycloak?.userInfo?.email === props.professorMail);
            setIsProfessor(AuthorizedFunction(keycloak, ['professor']));
        }
    }, [])

    const sendRequest = useCallback(() => {
        if (isSending) return
        setIsSending(true);

        const response = userApi.studentCourseRoll(keycloak?.token, props.courseId);
        if (response?.status === 200) {
            setEnrolled(!enrolled);
            setIsSending(false);
        }

        setIsSending(false);

    }, [isSending])

    return (
        keycloak?.token !== null && keycloak?.token !== undefined &&
        <div>
            {
                isProfessor === true ?
                    isCourseTitular === true ?
                        <Button variant="contained" disabled={isSending} color="secondary"><Link to={"/course/" + props.courseId + "/edit"}>Edit</Link></Button>
                        :
                        <Button variant="contained" disabled={true} onClick={sendRequest} color="secondary">Edit</Button>
                    :
                    enrolled === true ?
                        <Button variant="contained" disabled={isSending} onClick={sendRequest} color="warning">Withdraw</Button>
                        :
                        <Button variant="contained" disabled={isSending} onClick={sendRequest} color="success">Enroll</Button>
            }</div>
    );
}

export default CourseButton;