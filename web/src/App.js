import React from 'react'
import { Button } from "semantic-ui-react";
import { Link } from 'react-router-dom';


const App = () => {
  return (
    <div>
      <h1> PURRect Recipes </h1>
      <Button primary> Test button </Button>
      <Button>
       <Link to = "/login"> Login Page </Link>
      </Button>
      <Button>
        <Link to = "/signup"> Signup Page </Link>
      </Button>
      <Button>
        <Link to = "/guest"> Guest Page </Link>
      </Button>
    </div>
  );
};

export default App;
