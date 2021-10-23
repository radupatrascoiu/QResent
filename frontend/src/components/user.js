import { Button } from '@mui/material';
import React, { Component } from 'react';
import { Link } from 'react-router-dom';

class User extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            email: "",
            id: ""
        };
        this.props.keycloak.loadUserInfo().then(userInfo => {
            this.setState({ name: userInfo.name })
        });
    }

    render() {
        return (
        <Button color="inherit"><Link to="/secured">{this.state.name}</Link></Button>
        );
    }
}
export default User;