import React from 'react'
import './App.css';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Home from './pages';
import SignInPage from './pages/signin';
import SignUpPage from './pages/signup';
import MainPage from './pages/main';
import RecipePage from './pages/recipe';
import ContactInfo from './components/ContactInfo';
import EditRecipe from './pages/editrecipe';
import AddRecipe from './pages/addrecipe';

//recipe duzenlenecek
function App(){
  return (
    <Router>
      <Switch>
        <Route path="/" component={Home} exact />
        <Route path="/signin" component={SignInPage} exact />
        <Route path="/signup" component={SignUpPage} exact />
        <Route path="/mainpage" component={MainPage} exact />
        <Route path="/recipe" component={RecipePage} exact />
        <Route path="/contactinfo" component={ContactInfo} exact />
        <Route path="/editrecipe" component={EditRecipe} exact />
        <Route path="/addrecipe" component={AddRecipe} exact />

      </Switch>
    </Router>
  )
}

export default App;