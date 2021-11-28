import axios from 'axios';
import { config } from '../constants';

export const userApi = {
    getStudent, getCourse, getUsers, getCourses, studentCourseRoll, generatePresenceList, getPresenceList, recordQR, exportPresenceList, generateStatistics, adminCourseCreate
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

function adminCourseCreate(token, courseName, professorMail) {
    return instance.put(`/api/courses/create`,
        { courseName: courseName,
          professorMail: professorMail
        }, {
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
    return instance.get(`/api/courses/${courseID}`, {
        headers: {
            'Authorization': bearerAuth(token)
        }
    })
}

function generatePresenceList(token, courseID) {
    return instance.post(`/api/presencelist/${courseID}`, "", {
        headers: {
            'Authorization': bearerAuth(token)
        }
    })
}

function generateStatistics(token, courseId, courseNo) {
    return instance.get(`/api/statistics/${courseId}/${courseNo}`, {
        headers: {
            'Authorization': bearerAuth(token)
        }
    })
}

function getPresenceList(token, presenceListID) {
    return instance.get(`/api/presencelist/${presenceListID}`, {
        headers: {
            'Authorization': bearerAuth(token)
        }
    })
}

function recordQR(token, courseId, presenceListID, qrID) {
    return instance.post(`/api/qr/record/${courseId}/${presenceListID}/${qrID}`, "", {
        headers: {
            'Authorization': bearerAuth(token)
        }
    })
}

function exportPresenceList(token, presencelistId) {
    return instance.get(`/api/presencelist/export/${presencelistId}`, {
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