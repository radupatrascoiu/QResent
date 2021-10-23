import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Link } from 'react-router-dom';
import Home from './components/home';
import Student from './components/student';
import { withHooksKC } from './utils/withHooksKC';
import { Component } from 'react';
import Navbar from './components/navbar';

class App extends Component {
  render() {
    return (
      <div className="App">
        <BrowserRouter>
          <Navbar></Navbar>
          <div className="container">
            <Route exact path="/" component={Home} />
            <Route path="/secured" component={Student} />
          </div>
        </BrowserRouter>
      </div>
    );
  }
}

export default withHooksKC(App);
