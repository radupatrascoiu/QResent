import { Link } from "react-router-dom";
import { Button } from '@mui/material';

const NotFound = () => {
    return (
        <div className="not-found">
            <h2>Sorry</h2>
            <p>The page cannot be found</p>
            <Button variant="contained" color="info"><Link to="/">Back to the homepage...</Link></Button>
        </div>
    );
}
 
export default NotFound;