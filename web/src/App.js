import React from 'react'
import './App.css';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Home from './pages';
import SignInPage from './pages/signin';
import SignUpPage from './pages/signup';
import MainPage from './pages/main';
import ContactInfo from './components/ContactInfo';

//recipe duzenlenecek
function App(){
  return (
    <Router>
      <Switch>
        <Route path="/" component={Home} exact />
        <Route path="/signin" component={SignInPage} exact />
        <Route path="/signup" component={SignUpPage} exact />
        <Route path="/mainpage" component={MainPage} exact />
        <Route path="/recipe"  exact />
        <Route path="/contactinfo" component={ContactInfo} exact />

      </Switch>
    </Router>
  )
}

export default App;