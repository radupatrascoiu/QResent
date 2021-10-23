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
import '../App.css';


class Navbar extends Component {
    render() {
        return (
            <Box sx={{ flexGrow: 1 }}>
                <AppBar position="static">
                    <Toolbar>
                        <Grid className="leftGrid" container 
                        >
                            <Grid item>
                                <Typography type="title" variant="h6" component="div">
                                    QResent
                                </Typography>
                            </Grid>
                            <Grid item>
                                <Typography variant="h7" component="div">
                                    <Button color="inherit">
                                        <Link to="/">Home</Link>
                                    </Button>
                                </Typography>
                            </Grid>
                        </Grid>

                        <Grid className="rightGrid">
                            {this.props.initialized && this.props.keycloak?.authenticated ?
                                <div style={{ display: 'inline-block' }}>
                                    <User keycloak={this.props.keycloak} />
                                    <Logout keycloak={this.props.keycloak} />
                                </div> : <LoginPage></LoginPage>
                            }
                        </Grid>
                    </Toolbar>
                </AppBar>
            </Box>
        );
    }
}

export default withHooksKC(Navbar);