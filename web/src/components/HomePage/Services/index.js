import React from 'react';
import Icon1 from '../../../images/svg-4.svg';
import Icon2 from '../../../images/svg-5.svg';
import Icon3 from '../../../images/svg-6.svg';
import {
    ServicesContainer,
    ServicesH1,
    ServicesWrapper,
    ServicesCard,
    ServicesIcon,
    ServicesH2,
    ServicesP
} from './ServicesElements';

const Services = () => {
    return (
        <ServicesContainer id="premium">

            <ServicesH1>Premium Features</ServicesH1>
            <ServicesWrapper>
                <ServicesCard>
                    <ServicesIcon src={Icon1}/>
                    <ServicesH2>Personalize your profile</ServicesH2>
                    <ServicesP>Add profile picture as you want</ServicesP>
                </ServicesCard>

                <ServicesCard>
                    <ServicesIcon src={Icon2}/>
                    <ServicesH2>Suggest to us</ServicesH2>
                    <ServicesP>Suggest ingredients to add the system</ServicesP>
                </ServicesCard>

                <ServicesCard>
                    <ServicesIcon src={Icon3}/>
                    <ServicesH2>Be Purrfect</ServicesH2>
                    <ServicesP>like Purrfect Recipes Family ;)</ServicesP>
                </ServicesCard>
            </ServicesWrapper>

        </ServicesContainer>
    )
}

export default Services;
