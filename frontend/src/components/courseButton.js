import * as React from 'react';
import { useEffect, useState, useRef, useCallback } from 'react';
import { useKeycloak } from '@react-keycloak/web';
import { userApi } from '../services/userApi';

import Button from '@mui/material/Button';


const CourseButton = (props) => {
    const { initialized, keycloak } = useKeycloak();
    const [error, setError] = useState(false);
    const [enrolled, setEnrolled] = useState(props.enrolled);
    const [isSending, setIsSending] = useState(false);
    const isMounted = useRef(true);

    useEffect(() => {
        isMounted.current = false
    }, [])

    const sendRequest = useCallback(async () => {
        if (isSending) return
        setIsSending(true);

        const response = await userApi.studentCourseRoll(keycloak?.token, props.courseId);
        if (response?.status === 200) {
            setEnrolled(!enrolled);
            setIsSending(false);
        }

        if (isMounted.current)
            setIsSending(false);

    }, [isSending])

    return (
        enrolled === true ?
            <Button variant="contained" disabled={isSending} onClick={sendRequest} color="warning">Withdraw</Button>
            :
            <Button variant="contained" disabled={isSending} onClick={sendRequest} color="success">Enroll</Button>
    );
}

export default CourseButton;