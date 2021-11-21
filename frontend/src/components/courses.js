import * as React from 'react';
import { useEffect, useState } from 'react';
import { useKeycloak } from '@react-keycloak/web';
import { userApi } from '../services/userApi';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import CourseButton from "./courseButton";
import { Container } from '@mui/material';




const Courses = () => {
    const { initialized, keycloak } = useKeycloak();
    const [courses, setCourses] = useState(null);
    const [error, setError] = useState(false);
    const [updated, setUpdated] = useState(0);

    useEffect(async () => {
        if (keycloak && initialized) {
            try {
                const response = await userApi.getCourses(keycloak?.token);
                setCourses(response.data);
                console.log(response.data);
            } catch (error) {
                setError(true);
            }
        }
    }, [keycloak, initialized])

    return (<Container maxWidth="lg">
        {courses &&
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 250 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Course Name</TableCell>
                            <TableCell align="right">Professor</TableCell>
                            <TableCell align="right">Credits</TableCell>
                            <TableCell align="right">Enrollment</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {courses.map((course, idx) => (
                            <TableRow
                                key={idx}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                                <TableCell component="th" scope="row">
                                    {course.name}
                                </TableCell>
                                <TableCell align="right">
                                    {course.professor?.name}
                                </TableCell>
                                <TableCell align="right">
                                    {course.credits}
                                </TableCell>
                                <TableCell align="right">
                                    <CourseButton courseId={course.courseId} enrolled={course.enrolled} ></CourseButton>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        }
    </Container >
    );
}

export default Courses;