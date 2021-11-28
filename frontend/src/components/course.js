import { useState } from "react";
import { useEffect } from "react";
import { useParams } from "react-router";
import { userApi } from '../services/userApi';
import { useKeycloak } from '@react-keycloak/web';
import { useHistory } from "react-router-dom";

import {Alert, Button} from "@mui/material"
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';

import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

import '../styles/course.css'


const Course = () => {
    const {courseID} = useParams()
    const [courseNo, setCourseNo] = useState(null)
    const history = useHistory()
    const [course, setCourse] = useState(null)
    const { initialized, keycloak } = useKeycloak();
    const [open, setOpen] = useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const goToStatistics = () => {
        setOpen(false);
        if (courseNo > 0 && courseNo < 15) {
            history.push(`/statistics/${courseID}/${courseNo}`);
        } else {
            alert("Please provide a number between 1 and 14.")
        }
    }

    // Load data on mount 
    useEffect(async () => {
        if (keycloak && initialized) {
            try {
                const response = await userApi.getCourse(keycloak.token, courseID);
                const courseProjection = await response.data["data"]
                console.log(courseProjection)
                setCourse(courseProjection)
            } catch (error) {
                console.log(error);
                history.push(`/notfound`);
            }
        }
    }, [initialized, keycloak]);

    const generatePresenceList = async () => {
        if (keycloak && initialized && course != null) {
            try {
                const response = await userApi.generatePresenceList(keycloak.token, courseID);
                const presenceListId = response.data["statisticsId"]
                console.log(`Presence list ${presenceListId} generated.`)

                // redirect to presence list page
                history.push(`/course/${courseID}/presencelist/${presenceListId}`);
            } catch (error) {
                console.log(error);
            }
        }
    }

    const generateStatistics = async () => {
        if (keycloak && initialized && course != null) {
            try {
                const response = await userApi.generateStatistics(keycloak.token, courseID, courseNo);
                const statisticsId = response.data["statisticsId"]
                console.log(`Statistics ${statisticsId} generated.`)

                // redirect to statistics page
                history.push(`/course/${courseID}/statistics/${statisticsId}`);
            } catch (error) {
                console.log(error);
            }
        }
    }

    return (
        <div className="courseContainer">
            {course &&
                <Card sx={{ maxWidth: "100%" }}>
                    <CardContent>
                        <Typography  sx={{ my: "20px" }} gutterBottom variant="h5" component="div">{course.name}</Typography>
                        <TableContainer component={Paper}>
                            <Table sx={{ minWidth: "50%" }} aria-label="spanning table">
                                <TableBody>
                                        <TableRow key={course.id + "professor"}>
                                            <TableCell>Professor 🙍</TableCell>
                                            <TableCell align="center">{course?.professor?.name}</TableCell>
                                        </TableRow>
                                        <TableRow key={course.id + "credits"}>
                                            <TableCell>Credits 💰</TableCell>
                                            <TableCell align="center">{course?.credits}</TableCell>
                                        </TableRow>
                                        <TableRow key={course.id + "infos"}>
                                            <TableCell>Infos ℹ️</TableCell>
                                            <TableCell align="Left"  sx={{ width: "50%" }}>
                                                <Accordion>
                                                    <AccordionSummary
                                                    expandIcon={<ExpandMoreIcon />}
                                                    aria-controls="panel1a-content"
                                                    id="panel1a-header"
                                                    >
                                                        <Typography sx={{ mx: "auto", opacity: "40%" }} fontSize="15px"> -&nbsp;-&nbsp;-&nbsp;-&nbsp;- &nbsp; reveal &nbsp; -&nbsp;-&nbsp;-&nbsp;-&nbsp;-</Typography>
                                                    </AccordionSummary>
                                                    <AccordionDetails>
                                                        <Typography fontSize="13px">{course?.infos}</Typography>
                                                    </AccordionDetails>
                                                </Accordion>
                                            </TableCell>
                                        </TableRow>
                                        <TableRow key={course.id + "requirements"}>
                                            <TableCell>Requirements ⚔️</TableCell>
                                            <TableCell align="Left"  sx={{ width: "50%" }}>
                                                <Accordion>
                                                    <AccordionSummary
                                                    expandIcon={<ExpandMoreIcon />}
                                                    aria-controls="panel1a-content"
                                                    id="panel1a-header"
                                                    >
                                                        <Typography sx={{ mx: "auto", opacity: "40%" }} fontSize="15px"> -&nbsp;-&nbsp;-&nbsp;-&nbsp;- &nbsp; reveal &nbsp; -&nbsp;-&nbsp;-&nbsp;-&nbsp;-</Typography>
                                                    </AccordionSummary>
                                                    <AccordionDetails>
                                                        <Typography fontSize="13px">{course?.requirements}</Typography>
                                                    </AccordionDetails>
                                                </Accordion>
                                            </TableCell>
                                        </TableRow>
                                        <TableRow key={course.id + "bonuses"}>
                                            <TableCell>Bonuses 🏋️‍♀️</TableCell>
                                            <TableCell align="Left"  sx={{ width: "50%" }}>
                                                <Accordion>
                                                    <AccordionSummary
                                                    expandIcon={<ExpandMoreIcon />}
                                                    aria-controls="panel1a-content"
                                                    id="panel1a-header"
                                                    >
                                                        <Typography sx={{ mx: "auto", opacity: "40%" }} fontSize="15px"> -&nbsp;-&nbsp;-&nbsp;-&nbsp;- &nbsp; reveal &nbsp; -&nbsp;-&nbsp;-&nbsp;-&nbsp;-</Typography>
                                                    </AccordionSummary>
                                                    <AccordionDetails>
                                                        <Typography fontSize="13px">{course?.bonuses}</Typography>
                                                    </AccordionDetails>
                                                </Accordion>
                                            </TableCell>
                                        </TableRow>
                                        <TableRow key={course.id + "schedule"}>
                                            <TableCell>Schedule 📅</TableCell>
                                            <TableCell align="Left"  sx={{ width: "50%" }}>
                                                <Accordion>
                                                    <AccordionSummary
                                                    expandIcon={<ExpandMoreIcon />}
                                                    aria-controls="panel1a-content"
                                                    id="panel1a-header"
                                                    >
                                                        <Typography sx={{ mx: "auto", opacity: "40%" }} fontSize="15px"> -&nbsp;-&nbsp;-&nbsp;-&nbsp;- &nbsp; reveal &nbsp; -&nbsp;-&nbsp;-&nbsp;-&nbsp;-</Typography>
                                                    </AccordionSummary>
                                                    <AccordionDetails>
                                                        {course?.schedule?.map((availableTime) => (<div><Typography fontSize="13px">{availableTime}</Typography><br/></div>))}
                                                    </AccordionDetails>
                                                </Accordion>
                                            </TableCell>
                                        </TableRow>
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </CardContent>
                    <CardActions sx={{ my: "20px" }}>
                        <Button variant="outlined" sx={{ mx: "auto", marginBottom: "10px" }} onClick={generatePresenceList}>Generate a presence list</Button> <br/>
                        <Button variant="outlined" sx={{ mx: "auto", marginBottom: "10px" }} onClick={handleClickOpen}>Generate statistics</Button> <br/>
                    </CardActions>
                </Card>
            }
            {course && <Dialog open={open} onClose={handleClose}>
                    <DialogTitle>Generate Statistics</DialogTitle>
                    <DialogContent>
                    <DialogContentText>
                    Please provide the number of the week for which you want statistcs.
                    </DialogContentText>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="name"
                        label="Course Number"
                        type="number"
                        fullWidth
                        variant="standard"
                        onChange={(e) => {
                            setCourseNo(e.target.value)
                        }}
                    />
                    </DialogContent>
                    <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={goToStatistics}>Submit</Button>
                    </DialogActions>
                </Dialog>
            }
        </div>
    );
}
 
export default Course;