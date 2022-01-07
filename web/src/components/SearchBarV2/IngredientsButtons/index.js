import { IngredientList } from '../../../backend/RecipeValueListener';
import React, { useState, useEffect } from 'react';
import IngredientsButton from '../IngeredientsButton';
import { Grid } from 'semantic-ui-react';


export default function IngredientsButtons(){

    const [ingredients, setIngredients] = useState([]);
    /*
            For testing

    const test = async (data) => {
       
        let countIngredients=Object.keys(data).length;

       
            

        for(let i=0; i<countIngredients; i++){
            console.log(data[i].ingredientName);
        }
        
    }
    */

    //For Call Ingredients from RecipeValueListener
    useEffect(() => {
        (async function() {
            try {
                
                const ingredients = await IngredientList();
                setIngredients(ingredients);
                //await test(ingredients);
            } catch (e) {
                console.error(e);
            }
        })();
    }, []);

    return (
        <Grid>
            
            {
                ingredients.map((ingredients ,i) => (
                    <Grid key={i}>
                        <IngredientsButton ingredients={ingredients}/>
                    </Grid>
                ))
            }
            
        </Grid>
      );





}