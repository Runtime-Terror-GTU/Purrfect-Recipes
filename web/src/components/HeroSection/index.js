import React, { useState } from 'react';
import { Button } from '../ButtonElements';
import { 
    HeroContainer, 
    HeroBg, 
    VideoBg,
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
                    ess mama yapacak
                </HeroH1>
                <HeroP>
                    Minnos mama yiyecek
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
                        Let's cook! {hover ? <ArrowForward /> : <ArrowRight /> }
                    </Button>
                </HeroBtnWrapper>
            </HeroContent>
        </HeroContainer>
    );
};

export default HeroSection;
