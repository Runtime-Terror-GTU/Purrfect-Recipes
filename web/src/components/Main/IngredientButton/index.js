import React from 'react';
import {
    IngredientButtonContainer,
    IngredientButtonWrapper,
    IngredientButtonCardContainer,
    IngredientButtonIcon,
    IngredientButtonH2,
    IngredientButtonP,
    IngredientButtonCardWrapper
} from './IngredientButtonElements';

//üstüne tıklayınca gitmesi lazım mlsfki
//ingredient Box Container main'e tasinmali mi ????
const IngredientButton = ({ingredient}) => {

    return (
        <IngredientButtonContainer>
            <IngredientButtonWrapper>
                <IngredientButtonCardWrapper>
                    <IngredientButtonH2> {ingredient.IngredientName}  </IngredientButtonH2>
                </IngredientButtonCardWrapper>       
            </IngredientButtonWrapper>
        </IngredientButtonContainer>
    )
}

export default IngredientButton;
