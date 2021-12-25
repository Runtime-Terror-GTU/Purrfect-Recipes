import React from 'react';
import { Grid } from 'semantic-ui-react';
import IngredientButton  from '../IngredientButton';
import { IngredientButtonsContainer } from './IngredientsButtonsElements';


const IngredientButtons = ({ingredients}) => {

    return (
        <IngredientButtonsContainer>
            
            {
                ingredients.map((ingredient ,i) => (
                    <Grid key={i}>
                        <IngredientButton ingredient={ingredient}/>
                    </Grid>
                ))
            }
            
        </IngredientButtonsContainer>
    )
}

export default IngredientButtons;
