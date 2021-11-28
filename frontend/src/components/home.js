import React, { Component } from 'react';
import { withHooksKC } from '../utils/withHooksKC';
import '../utils/QRTool';

class Home extends Component {
  
  render() {
    return (
      <p>Public page</p>    
    );
  }
}
export default withHooksKC(Home);