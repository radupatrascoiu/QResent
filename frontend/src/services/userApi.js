import axios from 'axios';
import { config } from '../constants';

export const userApi = {
    getStudent, getCourse, getUsers, getCourses, studentCourseRoll
}

function getStudent(token) {
    return instance.get(`/api/user/student/`, {
        headers: {
            'Authorization': bearerAuth(token)
        }
    })
}

function getCourses(token) {
    return instance.get(`/api/courses/`, {
        headers: {
            'Authorization': bearerAuth(token)
        }
    })
}

function studentCourseRoll(token, course) {
    return instance.put(`/api/courses/enroll`,
        { courseId: course }, {
            headers: {
                'Authorization': bearerAuth(token)
            }
        })
}

function getUsers(token) {
    return instance.get(`/api/user/users/`, {
        headers: {
            'Authorization': bearerAuth(token)
        }
    })
}

function getCourse(token, courseID) {
    return instance.get(`/api/course/${courseID}`, {
        headers: {
            'Authorization': bearerAuth(token)
        }
    })
}



const instance = axios.create({
    baseURL: config.API_URL
})

instance.interceptors.response.use(response => {
    return response;
}, function (error) {
    if (error.response.status === 404) {
        return { stauts: error.response.status };
    }
    return Promise.reject(error.response);
});

function bearerAuth(token) {
    return `Bearer ${token}`
};