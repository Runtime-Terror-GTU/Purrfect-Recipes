import React from 'react';
import Icon1 from '../../images/svg-4.svg';

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
const RecipeBox = () => {
    return (
        <RecipeBoxContainer id="RecipeBox">

            <RecipeBoxWrapper>
                
                <RecipeBoxCardContainer>

                    <RecipeBoxCardWrapper>
                        <RecipeBoxIcon src={Icon1}/>
                    </RecipeBoxCardWrapper>
                    <RecipeBoxCardWrapper>
                       <RecipeBoxH2>Mercimek Corbasi </RecipeBoxH2>
                        <RecipeBoxP>by ESS</RecipeBoxP>   
                    </RecipeBoxCardWrapper>

                </RecipeBoxCardContainer>


            </RecipeBoxWrapper>

        </RecipeBoxContainer>
    )
}

export default RecipeBox;
