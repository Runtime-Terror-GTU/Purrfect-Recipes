import React from 'react';
import {
    RecipeBoxContainer,
    RecipeBoxWrapper,
    RecipeBoxCardContainer,
    RecipeBoxIcon,
    RecipeBoxH2,
    RecipeBoxP,
    RecipeBoxCardWrapper,
    PremiumImg
} from './RecipeOfTheDayElements';
import PremiumIcon from '../../../images/premium_symbol.png';

const RecipeOfTheDay = ({recipe}) => {
    return (
        <RecipeBoxContainer>
            <RecipeBoxWrapper>
                <RecipeBoxCardContainer to="/recipe"
                onClick={(e) => localStorage.setItem("currentRecipe", JSON.stringify(recipe))}>
                    <RecipeBoxCardWrapper>
                        <RecipeBoxP> RECIPE OF THE DAY  </RecipeBoxP>
                    </RecipeBoxCardWrapper>
                    <RecipeBoxCardWrapper>
                        <RecipeBoxH2> {recipe.R_RecipeName}  </RecipeBoxH2>
                    </RecipeBoxCardWrapper>
                    <RecipeBoxCardWrapper>
                        <RecipeBoxP> by {recipe.R_RecipeOwner} 
                        {(() => {
                            if( recipe.R_RecipeOwnerStatus === "PREMIUM" ){
                                return(
                                    <PremiumImg src={PremiumIcon}/>         
                                )
                            }               
                        })()}
                        </RecipeBoxP>
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
