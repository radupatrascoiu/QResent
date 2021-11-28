import { useState } from "react";
import { useEffect } from "react";
import { useParams } from "react-router";
import { userApi } from '../services/userApi';
import { useKeycloak } from '@react-keycloak/web';
import { useHistory } from "react-router-dom";
import { Link } from 'react-router-dom';
import { TextareaAutosize } from '@mui/base';

import { Alert, Button, Container } from "@mui/material"

import Box from '@mui/material/Box';
import AuthorizedFunction from '../utils/authorization';
import TextField from '@mui/material/TextField';

import '../styles/course.css'


const CourseEdit = () => {
    const { courseID } = useParams()
    const history = useHistory()
    const [course, setCourse] = useState(null)
    const { initialized, keycloak } = useKeycloak();
    const [unauthorized, setUnauthorized] = useState(false);
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState(false);
    const [isSending, setIsSending] = useState(false);
    const [name, setName] = useState('');
    const [bonuses, setBonuses] = useState('');
    const [infos, setInfos] = useState('');
    const [requirements, setRequirements] = useState('');
    const [schedule, setSchedule] = useState('');
    const [credits, setCredits] = useState('');
    const [courseId, setCourseId] = useState('');

    // Load data on mount 
    useEffect(async () => {
        if (keycloak && initialized) {
            try {
                const response = await userApi.getCourse(keycloak.token, courseID);
                const courseProjection = await response.data["data"]
                console.log(courseProjection)
                setCourse(courseProjection)
                if (keycloak?.userInfo?.email !== courseProjection?.professor?.ldapId) {
                    setUnauthorized(true);
                } else {
                    setName(courseProjection.name)
                    setBonuses(courseProjection.bonuses)
                    setInfos(courseProjection.infos)
                    setRequirements(courseProjection.requirements)
                    setSchedule(courseProjection.schedule)
                    setCredits(courseProjection.credits)
                    setCourseId(courseProjection.id);
                }
            } catch (error) {
                console.log(error);
                history.push(`/notfound`);
            }
        }
    }, []);

    const sendRequest = async () => {
        if (isSending) return
        setIsSending(true);

        try {
            const response = await userApi.editCourse(keycloak?.token, courseId,
                name, bonuses, infos, requirements, schedule, credits);
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
        keycloak?.token !== null && keycloak?.token !== undefined &&
        <div>
            {AuthorizedFunction(keycloak, ['professor']) && course && unauthorized === false ?
                <Container size="lg">
                    {error === true ? <Box>
                        <Alert severity="error">There was a problem. </Alert>
                        <Button variant="contained" color="inherit"><Link to="/">Home</Link></Button>
                    </Box>
                        : success === true ?
                            <Box><Alert severity="success">Course edited with success!
                            </Alert>
                                <Button variant="contained" color="inherit"><Link to="/">Home</Link></Button>
                            </Box>
                            :
                            <div>
                                <Box
                                    component="form"
                                    sx={{
                                        '& .MuiTextField-root': { m: 1, width: '60ch' },
                                    }}
                                    noValidate
                                    autoComplete="off"
                                >
                                    <div>
                                        <TextField
                                            required
                                            id="outlined"
                                            label="Course Name"
                                            value={name}
                                            onChange={e => setName(e.target.value)}
                                        />
                                        <TextField
                                            required
                                            id="outlined"
                                            label="Credits"
                                            value={credits}
                                            onChange={e => setCredits(e.target.value)}
                                        />
                                    </div>
                                </Box>
                                <Box
                                    component="form"
                                    sx={{
                                        '& .MuiTextField-root': { m: 1, width: '60ch' },
                                    }}
                                    noValidate
                                    autoComplete="off"
                                >
                                    <div>
                                        <TextField
                                            required
                                            id="outlined"
                                            label="Infos"
                                            multiline={true}
                                            rows={3}
                                            value={infos}
                                            onChange={e => setInfos(e.target.value)}
                                        />
                                        <TextField
                                            required
                                            multiline={true}
                                            rows={3}
                                            id="outlined"
                                            label="Requirements"
                                            value={requirements}
                                            onChange={e => setRequirements(e.target.value)}
                                        />
                                        <TextField
                                            required
                                            id="outlined"
                                            label="Bonuses"
                                            value={bonuses}
                                            onChange={e => setBonuses(e.target.value)}
                                        />
                                        <TextField
                                            required
                                            id="outlined"
                                            label="Schedule"
                                            value={schedule}
                                            onChange={e => setSchedule(e.target.value)}
                                        />
                                    </div>
                                    <Button disabled={isSending} variant="contained" color="success" onClick={sendRequest}>Save</Button>
                                </Box>
                            </div>
                    }
                </Container> : <Box>
                    <Alert severity="error">Page not found. </Alert>
                    <Button variant="contained" color="info"><Link to="/">Home</Link></Button>
                </Box>
            }
        </div >
    );
}

export default CourseEdit;