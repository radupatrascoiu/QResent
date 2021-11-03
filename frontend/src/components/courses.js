import * as React from 'react';
import { useParams } from "react-router";
import { useEffect, useState } from 'react';
import { config } from '../constants';
import { useKeycloak } from '@react-keycloak/web';
import { userApi } from '../services/userApi';


const Courses = () => {
    const { initialized, keycloak } = useKeycloak();
    const [courses, setCourses] = useState(null);
    const [error, setError] = useState(false);

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

    return (<div>
        {courses && courses.map((course, idx) => (
            <li key={idx}>
                <ul>{course.name}</ul>
                <ul>{course.infos}</ul>
                <ul>{course.requirements}</ul>
                <ul>{course.professorId.name}</ul>
            </li>
        ))}
    </div >
    );
}

export default Courses;