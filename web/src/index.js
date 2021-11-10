import React from 'react';
import ReactDOM from "react-dom";
import "semantic-ui-css/semantic.min.css";
import {BrowserRouter as Router, Route, Switch, Redirect,} from "react-router-dom";
import "./index.css";
import App from "./App";
import Login from './components/auth/Login';
import SignUp from "./components/auth/SignUp";

const Root = () => (
  <Router>
    <Switch>
      <Route exact path = "/" component = {App} />
      <Route path = "/signup" component = {SignUp} />
      <Route path = "/login" component = {Login} />
    </Switch>
  </Router>
)

ReactDOM.render(
    <Root />, document.getElementById("root")
);


