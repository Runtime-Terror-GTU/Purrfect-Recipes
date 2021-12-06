import React from 'react'
import { Button } from "semantic-ui-react";
import { Link } from 'react-router-dom';
import { useMediaQuery } from 'react-responsive';
/*
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
*/
const Desktop = ({ children }) => {
  const isDesktop = useMediaQuery({ minWidth: 992 })
  return isDesktop ? children : null
}
const Tablet = ({ children }) => {
  const isTablet = useMediaQuery({ minWidth: 768, maxWidth: 991 })
  return isTablet ? children : null
}
const Mobile = ({ children }) => {
  const isMobile = useMediaQuery({ maxWidth: 767 })
  return isMobile ? children : null
}
/*
const Default = ({ children }) => {
  const isNotMobile = useMediaQuery({ minWidth: 768 })
  return isNotMobile ? children : null
}
*/

const App = () => (
  <div>
    <Desktop>
      <div>
        <h1> PURRect Recipes </h1>
        <Button primary> Test button </Button>
        <Button> <Link to = "/login"> Login Page </Link> </Button>
        <Button> <Link to = "/signup"> Signup Page </Link> </Button>
        <Button> <Link to = "/guest"> Guest Page </Link> </Button>
      </div>
    </Desktop>
    <Tablet>
      <div>
        <h1> PURRect Recipes </h1>
        <Button primary> Test button </Button>
        <Button> <Link to = "/login"> Login Page </Link> </Button>
        <Button> <Link to = "/signup"> Signup Page </Link> </Button>
        <Button> <Link to = "/guest"> Guest Page </Link> </Button>
      </div>
    </Tablet>
    <Mobile>
      <div>
        <h1> PURRect Recipes </h1>
        <Button primary> Test button </Button>
        <Button> <Link to = "/login"> Login Page </Link> </Button>
        <Button> <Link to = "/signup"> Signup Page </Link> </Button>
        <Button> <Link to = "/guest"> Guest Page </Link> </Button>
      </div>
    </Mobile>
    

    
  </div>
)

export default App;


/*
    <Default> 
      <div>
        <h1> PURRect Recipes </h1>
        <Button primary> Test button </Button>
        <Button> <Link to = "/login"> Login Page </Link> </Button>
        <Button> <Link to = "/signup"> Signup Page </Link> </Button>
        <Button> <Link to = "/guest"> Guest Page </Link> </Button>
      </div>
    </Default>
 */