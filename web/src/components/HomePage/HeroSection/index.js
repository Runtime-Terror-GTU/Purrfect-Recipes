import React, { useState } from 'react';
import { Button } from '../../ButtonElements';
import { 
    HeroContainer, 
    HeroBg, 
    HeroContent,
    HeroH1,
    HeroP,
    HeroBtnWrapper,
    ArrowForward,
    ArrowRight,
    Content 
} from './HeroElements';

const HeroSection = () => {
    const [hover, setHover] = useState(false);

    const onHover = () => {
        setHover(!hover);
    }

    return (
        <HeroContainer id='home'>
            <HeroBg>
                <Content/>
            </HeroBg>
            <HeroContent>
                <HeroH1>
                   Purrfect Recipes
                </HeroH1>
                <HeroP>
                    You have lots of ingredients and what are you going to cook?
                </HeroP>
                <HeroBtnWrapper>
                    <Button 
                        to="/signin" 
                        onMouseEnter={onHover} 
                        onMouseLeave={onHover}
                        primary='true'
                        dark='true'
                        smooth={true}
                        duration={500}
                        spy={true}
                        exact='true'
                        offset={80}
                    >
                        Let's decide and cook! {hover ? <ArrowForward /> : <ArrowRight /> }
                    </Button>
                </HeroBtnWrapper>
            </HeroContent>
        </HeroContainer>
    );
};

export default HeroSection;
