import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import { withHooksKC } from '../utils/withHooksKC';
import { Component } from 'react';
import LoginPage from './login';
import User from './user';
import Logout from './logout';
import { Link } from 'react-router-dom';
import { Grid } from '@mui/material';
import { useEffect, useState } from 'react';
import { useKeycloak } from '@react-keycloak/web';
import '../App.css';
import img from "../img/logoQR2.png"
import AuthorizedFunction from '../utils/authorization';

function Navbar() {
    const { initialized, keycloak } = useKeycloak();

    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static" style={{background: "Black" }}>
                <Toolbar>
                    <Grid className="leftGrid" container>
                        <Grid item>
                            <Typography type="title" variant="h6" component="div">
                                    <img className="img" src={img}/>
                            </Typography>
                        </Grid>
                        <Grid item>
                            <Typography variant="h7" component="div">
                                <Button color="inherit">
                                    <Link to="/">Home</Link>
                                </Button>
                                {initialized && keycloak?.authenticated &&
                                    <Button color="inherit">
                                        <Link to="/courses">Courses</Link>
                                    </Button>
                                }
                                {AuthorizedFunction(keycloak, ['admin']) && <Button color="inherit">
                                    <Link to="/create/course">Add Course</Link>
                                </Button>}
                            </Typography>
                        </Grid>
                    </Grid>
                    <Grid className="rightGrid">
                        {initialized && keycloak?.authenticated ?
                            <div style={{ display: 'inline-block' }}>
                                <User keycloak={keycloak} />
                                <Logout keycloak={keycloak} />
                            </div> : <LoginPage></LoginPage>
                        }
                    </Grid>
                </Toolbar>
            </AppBar>
        </Box>
    );
}
export default Navbar;