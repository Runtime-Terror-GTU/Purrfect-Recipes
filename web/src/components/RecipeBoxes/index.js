import React, { useState, useEffect } from 'react';
import { Grid } from 'semantic-ui-react';
import { getRecipes } from '../../backend/RecipeService';
import RecipeBox  from '../RecipeBox';


/*
const fetchRecipes = async() => {
    console.log("countRecipes");  

    const recipes = await getRecipes();
    console.log("ciddimisin ");  

    let countRecipes = Object.keys(recipes).length;
    console.log(countRecipes);  
    return recipes;
}

*/
const RecipeBoxes = ({recipes}) => {


/*    
    let recipes = fetchRecipes();
    console.log("buradkjfkd")

    let countRecipes = Object.keys(recipes).length;
    console.log("olmadıdimi");  

    console.log(countRecipes);  

/*
    for(let i=0; i<countRecipes; i++){
        //recipesArray[i] = {};
        //recipesArray[i].recipeName = recipes[recipesObjects[i]].R_RecipeName;
        console.log( recipes[i].recipeName );
    }
*/


    return (
        <Grid>
            
            {console.log("recipeboxesdeneme1")}

            {/*
                Object.keys(recipes).map((recipe) => (

                    <Grid>
                        <RecipeBox recipe={recipe}/>
                    </Grid>
                ))
                */
            }
            
            {console.log("recipeboxesdeneme2")}

        </Grid>
    )
}

export default RecipeBoxes;
