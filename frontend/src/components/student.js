import React, { Component } from 'react';
import User from './user';
import Logout from './logout';
import { withHooksKC } from '../utils/withHooksKC'
import LoginPage from './login';
import Loader from './loader';
import { userApi } from '../services/userApi';

class Student extends Component {
    constructor(props) {
        super(props);
        this.state = {
            initialized: false,
            keycloak: null,
            data: null,
        }
    }

    async componentDidMount() {
        const authenticated = this.props.keycloak?.authenticated;
        const keycloak = this.props.keycloak
        console.log(keycloak.token);

        this.setState({
            initialized: authenticated,
        });

        try {
            const response = await userApi.getStudent(keycloak?.token);
            this.setState({
                data: response.data
            });
        } catch (error) {
            console.log(error);
        }
    }

    render() {
        if (this.props.initialized) {
            if (this.props.keycloak?.authenticated)
                return (
                    <div>
                        <p>Student Page - Secured</p>
                        {this.state.data}
                    </div>
                ); else return (<LoginPage></LoginPage>)
        }
        return (
            <Loader></Loader>
        );
    }
}

export default withHooksKC(Student);