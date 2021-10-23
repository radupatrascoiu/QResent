import * as React from 'react';
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';
import Grid from  '@mui/material/Grid';

export default function Loader() {
    return (
        <Grid
            container
            spacing={0}
            direction="column"
            alignItems="center"
            justifyContent="center"
            style={{ minHeight: "100vh" }}
        >
            <Box sx={{ display: 'flex' }}>
                <CircularProgress />
            </Box>
        </Grid>
    );
}
