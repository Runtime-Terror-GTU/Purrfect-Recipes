import React from 'react';
import {
    RecipeBoxContainer,
    RecipeBoxWrapper,
    RecipeBoxCardContainer,
    RecipeBoxIcon,
    RecipeBoxH2,
    RecipeBoxP,
    RecipeBoxCardWrapper
} from './RecipeBoxElements';

//üstüne tıklayınca gitmesi lazım mlsfki
//Recipe Box Container main'e tasinmali mi ????
const RecipeBox = ({recipe}) => {

    return (
        <RecipeBoxContainer>
            <RecipeBoxWrapper>
                <RecipeBoxCardContainer to="/recipe">
                    
                    <RecipeBoxCardWrapper>
                        <RecipeBoxIcon src={recipe.R_RecipePicture} />
                    </RecipeBoxCardWrapper>
                    <RecipeBoxCardWrapper>
                        <RecipeBoxH2> {recipe.R_RecipeName}  </RecipeBoxH2>
                        <RecipeBoxP> by {recipe.R_RecipeOwner} </RecipeBoxP>
                    </RecipeBoxCardWrapper>
                    
                </RecipeBoxCardContainer>
            </RecipeBoxWrapper>
        </RecipeBoxContainer>
    )
}

export default RecipeBox;
