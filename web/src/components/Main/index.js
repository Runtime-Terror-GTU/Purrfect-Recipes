import React, { useState, useEffect } from 'react';
import RecipeBoxes from '../RecipeBoxes';
import SearchBar from '../SearchBar';
import Footer from '../HomePage/Footer';
import { getRecipes } from '../../backend/RecipeValueListener';
import {
    MainContainer,
    MainWrapper,
    SearchWrapper,
    RecipeWrapper,
    OtherWrapper,
    SearchCard,
    OtherCard,
    SortButtons,
    SortButton
} from './MainElements';
import { getRecipeOfTheDay } from '../../backend/RecipeOfTheDayServices';
import RecipeOfTheDay from './RecipeOfTheDay';
//Footer'daki Purrfect Recipes'a basınca homescreen'e gidiyor
export const Main = () => {
    
    const [recipes, setRecipes] = useState([]);
    const [ingredients, setIngredients] = useState([]);
    const [recipeOfTheDay, setRecipeOfTheDay] = useState([]);

    const fetchRecipes = async() => {
        const data = await getRecipes();
        return data;
    }



    const fetchRecipeOfTheDay = async() => {
        const data = await getRecipeOfTheDay();
        return data
    }

    useEffect(() => {
        (async function() {
            try {
                const recipes = await fetchRecipes();
                setRecipes(recipes);
                const recipeOfTheDay = await fetchRecipeOfTheDay();
                setRecipeOfTheDay(recipeOfTheDay);
                console.log(recipeOfTheDay)
            } catch (e) {
                console.error(e);
            }
        })();
    }, []);

    return (
        <>
            <MainContainer>
                <MainWrapper>
        
                    <SearchWrapper>
                        <SearchCard>
                            <SearchBar searchType="Search by Recipe Name" />
                        </SearchCard>

                        <SearchCard>
                            <SearchBar searchType="Search by Recipe Owner" />
                        </SearchCard>

                        <SearchCard>
                            <SearchBar searchType="Search by Ingredient" />                       
                        </SearchCard>

                        <SearchCard>
                            <h1>deneme</h1>                       
                        </SearchCard>
                    </SearchWrapper>

                    <RecipeWrapper>
                        <RecipeBoxes recipes={recipes} />
                    </RecipeWrapper>

                    <OtherWrapper>
                        <OtherCard> 
                            <RecipeOfTheDay recipe={recipeOfTheDay} />
                        </OtherCard>
                        <OtherCard> ADVERTISEMENT </OtherCard>
                    </OtherWrapper>    

                </MainWrapper>
            </MainContainer>

            <Footer />
        </>
    )
}

export default Main;