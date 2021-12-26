import React from 'react';
import {
    RecipeBoxContainer,
    RecipeBoxWrapper,
    RecipeBoxCardContainer,
    RecipeBoxIcon,
    RecipeBoxH2,
    RecipeBoxP,
    RecipeBoxCardWrapper
} from './RecipeOfTheDayElements';

//üstüne tıklayınca gitmesi lazım mlsfki
//Recipe Box Container main'e tasinmali mi ????
const RecipeOfTheDay = ({recipe}) => {
    return (
        <RecipeBoxContainer>
            <RecipeBoxWrapper>
                <RecipeBoxCardContainer to="/recipe">
                    <RecipeBoxCardWrapper>
                        <RecipeBoxP> RECIPE OF THE DAY  </RecipeBoxP>
                    </RecipeBoxCardWrapper>
                    <RecipeBoxCardWrapper>
                        <RecipeBoxH2> {recipe.R_RecipeName}  </RecipeBoxH2>
                    </RecipeBoxCardWrapper>
                    <RecipeBoxCardWrapper>
                        <RecipeBoxP> by {recipe.R_RecipeOwner} </RecipeBoxP>
                    </RecipeBoxCardWrapper>
                    <RecipeBoxCardWrapper>
                        <RecipeBoxIcon src={recipe.R_RecipePicture} />
                    </RecipeBoxCardWrapper>

                </RecipeBoxCardContainer>
            </RecipeBoxWrapper>
        </RecipeBoxContainer>
    )
}

export default RecipeOfTheDay;
