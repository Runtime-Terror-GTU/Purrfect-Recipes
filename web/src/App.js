import React from 'react'
import './App.css';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Home from './pages';
import SignInPage from './pages/signin';
import SignUpPage from './pages/signup';
import MainPage from './pages/main';
<<<<<<< HEAD
import RecipePage from './pages/recipe';
=======
import ContactInfo from './components/ContactInfo';
>>>>>>> 60b0b601c2ca2f5d3cf084bea138a02a31d92f62

//recipe duzenlenecek
function App(){
  return (
    <Router>
      <Switch>
        <Route path="/" component={Home} exact />
        <Route path="/signin" component={SignInPage} exact />
        <Route path="/signup" component={SignUpPage} exact />
        <Route path="/mainpage" component={MainPage} exact />
<<<<<<< HEAD
        <Route path="/recipe" component={RecipePage} exact />
=======
        <Route path="/recipe"  exact />
        <Route path="/contactinfo" component={ContactInfo} exact />

>>>>>>> 60b0b601c2ca2f5d3cf084bea138a02a31d92f62
      </Switch>
    </Router>
  )
}

export default App;