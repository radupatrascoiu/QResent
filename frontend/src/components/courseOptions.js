import * as React from 'react';
import { useEffect, useState, useRef, useCallback } from 'react';
import { useKeycloak } from '@react-keycloak/web';
import { userApi } from '../services/userApi';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { Button, Container } from '@mui/material';
import AuthorizedFunction from '../utils/authorization';
import { Alert } from '@mui/material';

const CourseOptions = () => {
    const { initialized, keycloak } = useKeycloak();
    const [courseName, setCourseName] = useState("");
    const [professorMail, setProfessorMail] = useState("");
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState(false);

    const [isSending, setIsSending] = useState(false);

    const tryAgain = () => {
        setSuccess(false);
        setError(false);
        setProfessorMail("");
        setCourseName("");
    }

    const sendRequest = async () => {
        if (isSending) return
        setIsSending(true);

        try {
            const response = await userApi.adminCourseCreate(keycloak?.token, courseName, professorMail);
            if (response?.status === 200) {
                setIsSending(false);
                setSuccess(true);
            } else {
                setError(true);
                setIsSending(false);
            }
        } catch (e) {
            setError(true);
            setIsSending(false);
        }
    }

    return (
        AuthorizedFunction(keycloak, ['admin']) &&
        <Container size="lg">
            {error === true ? <Box>
                <Alert severity="error">There was a problem. </Alert>
                <Button variant="contained" color="warning" onClick={tryAgain}>Try again</Button>
            </Box>
                : success === true ?
                    <Box><Alert severity="success">Course created with success!
                    </Alert>
                        <Button variant="contained" color="info" onClick={tryAgain}>Add more</Button>
                    </Box>
                    :
                    <Box
                        component="form"
                        sx={{
                            '& .MuiTextField-root': { m: 1, width: '25ch' },
                        }}
                        noValidate
                        autoComplete="off"
                    >
                        <div>
                            <TextField
                                required
                                id="outlined-required"
                                label="Course Name"
                                value={courseName}
                                onChange={e => setCourseName(e.target.value)}
                            />
                            <TextField
                                required
                                id="outlined-required"
                                label="Professor Email"
                                value={professorMail}
                                onChange={e => setProfessorMail(e.target.value)}
                            />
                        </div>
                        <Button disabled={isSending} variant="contained" color="success" onClick={sendRequest}>Create</Button>
                    </Box>
            }
        </Container>
    );
}

export default CourseOptions;