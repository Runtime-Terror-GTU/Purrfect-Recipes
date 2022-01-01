import React from "react";
import {HeroH1,HeroP,  HeroContainer,HeroContent} from './contactInfoElements.js';
//import img from '../../images/bg-1.png';


function About() {
  return (
        < HeroContainer>
        <HeroContent>
        <HeroH1> General Info </HeroH1>
        <HeroP>
          This is a platform created for those who have ingredients in their home and don't know what to cook,and those who are wondering what dish can be made with the ingredients they have.
        </HeroP>
        <HeroP>
          You can access Purrfect Recipes via the website and the Android application.
        </HeroP>
        <HeroH1> Contact Info </HeroH1>
        <HeroP>
          E-Mail: purrfectrecipes@gmail.com
        </HeroP>
        </HeroContent>
        </HeroContainer>
  );
}

export default About;