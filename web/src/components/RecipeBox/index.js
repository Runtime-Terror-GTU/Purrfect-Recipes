import React, { useState, useEffect } from 'react'
import {
    RecipeBoxContainer,
    RecipeBoxWrapper,
    RecipeBoxCardContainer,
    RecipeBoxIcon,
    RecipeBoxH2,
    RecipeBoxP,
    RecipeBoxCardWrapper,
    Img
} from './RecipeBoxElements';
import PremiumIcon from '../../images/premium_symbol.png';


//üstüne tıklayınca gitmesi lazım mlsfki
//Recipe Box Container main'e tasinmali mi ????
const RecipeBox = ({recipe}) => {

    return (
        <RecipeBoxContainer>
            <RecipeBoxWrapper>
                <RecipeBoxCardContainer to="/recipe" 
                onClick={(e) => localStorage.setItem("currentRecipe", JSON.stringify(recipe))}>
                    <RecipeBoxCardWrapper>
                        <RecipeBoxIcon src={recipe.R_RecipePicture} />
                    </RecipeBoxCardWrapper>
                    <RecipeBoxCardWrapper>
                        <RecipeBoxH2> {recipe.R_RecipeName}  </RecipeBoxH2>
                        <RecipeBoxP> by {recipe.R_RecipeOwner} 
                        {(() => {
                            if( recipe.R_RecipeOwnerStatus === "PREMIUM" ){
                                return(
                                    <Img src={PremiumIcon}/>         
                                )
                        }               
                        })()}
                        </RecipeBoxP>
                        


                    </RecipeBoxCardWrapper>
                    
                </RecipeBoxCardContainer>
            </RecipeBoxWrapper>
        </RecipeBoxContainer>
    )
}

export default RecipeBox;
