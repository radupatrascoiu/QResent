import React, { Component } from 'react';
import { withHooksKC } from '../utils/withHooksKC';

class Home extends Component {
  render() {
    return (
      <div className="container">
        <p>Public page</p>
      </div>
    );
  }
}
export default withHooksKC(Home);