import React, { Component } from 'react';
import { withHooksKC } from '../utils/withHooksKC';
import '../utils/QRTool';
import {Card} from 'react-bootstrap'
import img from "../img/acs_mobi.png";
import img2 from "../img/sigla_mps.png";
import logoQR from "../img/logoQR2.png";
import Content1 from "./ReadMore1";
import Content2 from "./ReadMore2";
import Content3 from "./ReadMore3";
import Faq from 'react-faq-component';




class Home extends Component {
  render() {
    const acs = "https://acs.pub.ro/://www.instagram.com";
    const mps = "https://ocw.cs.pub.ro/courses/mps";
    const qr = "https://ocw.cs.pub.ro/courses/mps/proiect/proiect-1"

    const data = {
      title: <p style={
        {
        color: "white",
        backgroundColor: 'lightblue',
        borderRadius: '8px',
        textAlign: 'center',
        width: 330,
        pading: 10
        }}> 
        FAQ (How it works?) </p>,
      rows: [
        {
          title: "Cum îmi fac un cont nou?",
          content: "Accesând secțiunea 'Sign in'. Te poți conecta fie prind mail, fie folosind contul Google."
        },
        {
          title: "Cum mă înrolez la un curs?",
          content: "Pentru a te înrola la un curs trebuie să ai un cont de student și să accesezi secțiunea 'Courses'. "
        },
        {
          title: "Cum pot face prezența? ",
          content: "Pentru a face prezența, trebuie să accesezi pagină cursului și să scanezi codul QR corespunzător până expiră timpul."
        },
        {
          title: "Ce fac dacă nu merge codul QR?",
          content: "Lângă codul QR este prezența o alternativă: un URL care poate fi direct copiat în bara de căutare."
        },
        {
          title: "Se pot genera statistici?",
          content: "Da, accesând pagina unui curs apoi 'Generate Statistics'."
        }]
    }

    return (
      <div className="container">
        <div CardGroup style = {{display: 'flex', alignItems: 'stretch'}}>
          <Card style = {
            {width: '35rem', boxShadow: '0 3px 5px 2px rgba(0, 0, 0, .5)',
            border:'2px solid black', backgroundColor:'lightblue', flex:1,
            marginLeft: '5vw', marginTop: '10vh', 
            marginBottom:'5vh', padding: 10, borderRadius:'7px'         
         }}>
            <Card.Img variant="top" src={logoQR} />
            <Card.Body >
              <Card.Title > <b> QResent </b></Card.Title>
                <a href = {qr}> QResent on OCW </a>
              <Card.Text style={{background:'lightblue'}}>
                <Content1 />
              </Card.Text>
            </Card.Body>
            <Card.Footer>
            </Card.Footer>
          </Card>

          <Card style = {
            {width: '35rem', boxShadow: '0 3px 5px 2px rgba(0, 0, 0, .5)',
            border:'2px solid black', backgroundColor:'lightblue', flex:1,
            marginLeft: '10vw', marginTop: '10vh', 
            marginBottom:'5vh', padding: 10, borderRadius:'7px' 
         }}>
            <Card.Img variant="top" src={img} style={ 
            {width: '14rem'}} />
            <Card.Body >
              <Card.Title> <b> Facultatea de Automatică şi Calculatoare </b></Card.Title>
                <a href = {acs}> ACS over all </a>
              <Card.Text style={{background:'lightblue'}}>
                <Content2 />
              </Card.Text>
            </Card.Body>
            <Card.Footer>
            </Card.Footer>
          </Card>

          <Card style={ 
            {width: '35rem', boxShadow: '0 3px 5px 2px rgba(0, 0, 0, .5)',
            border:'2px solid black', backgroundColor:'lightblue', flex:1,
            marginLeft: '10vw', marginTop: '10vh', marginRight: '10vh',
            marginBottom:'5vh', padding: 10, borderRadius:'7px' 
         }}>
            <Card.Img variant="top" src={img2} style={ 
            {width: '8rem'}} />
            <Card.Body>
              <Card.Title> <b>  Managementul Proiectelor Software </b> </Card.Title>
              <a href = {mps}> MPS on OCW </a>
              <Card.Text>
                <Content3 />
              </Card.Text>
            </Card.Body>
            <Card.Footer>
            </Card.Footer>
          </Card>

      </div>

      <Faq data={data} style = {{pading:'0px 10px'}}/>
      </div>


    );
  }
}
export default withHooksKC(Home);