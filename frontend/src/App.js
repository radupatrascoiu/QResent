import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Link, Switch } from 'react-router-dom';
import Home from './components/home';
import Student from './components/student';
import { withHooksKC } from './utils/withHooksKC';
import { Component } from 'react';
import Navbar from './components/navbar';
import Course from './components/course';
import presenceList from './components/presenceList';
import NotFound from './components/notfound';
import ValidateQR from './services/validateQR';
import Courses from './components/courses';
import Statistics from './components/statistics';

class App extends Component {
  render() {
    return (
      <div className="App">
        <BrowserRouter>
          <Navbar></Navbar>
          <div className="container">
            <Switch>
              <Route exact path="/" component={Home} />
              <Route exact path="/course/:courseID" component={Course} />
              <Route path="/course/:courseID/presencelist/:presenceListID" component={presenceList} />
              <Route path="/statistics/:courseID/:courseNo" component={Statistics} />
              <Route path="/validate/:courseID/:presenceListID/:qrcodeID" component={ValidateQR} />
              <Route path="/secured" component={Student} />
              <Route exact path="/courses" component={Courses} />
              <Route path="*"><NotFound /></Route>
            </Switch>
          </div>
        </BrowserRouter>
      </div>
    );
  }
}

export default withHooksKC(App);
