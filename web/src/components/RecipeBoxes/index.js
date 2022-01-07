import React from 'react';
import { Grid } from 'semantic-ui-react';
import RecipeBox  from '../RecipeBox';


const RecipeBoxes = ({recipes}) => {

    return (
        <Grid>
            {
                recipes.map((recipe, i) => (
                    <Grid key={i}>
                        <RecipeBox recipe={recipe}/>
                    </Grid>
                ))
            }
            
        </Grid>
    )
}

export default RecipeBoxes;
