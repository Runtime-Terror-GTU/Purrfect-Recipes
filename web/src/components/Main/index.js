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
    SortBar,
    SortButton,
    SortMenu
} from './MainElements';
import { getRecipeOfTheDay } from '../../backend/RecipeOfTheDayServices';
import RecipeOfTheDay from './RecipeOfTheDay';
import { Button } from 'semantic-ui-react';

let difficultyVariable = "false";
let popularityVariable = "false";
//After click on Sort by Difficulty
const difficultySort = async (e) => {
    //first click
    if(popularityVariable == "true")
        popularityVariable = "false";
    //second click on same buton
    if(difficultyVariable == "true")
        difficultyVariable = "false";
    else
        difficultyVariable = "true";
    
    saveSortElements();
    window.location.reload();
   
}
//save locally
const saveSortElements= async (e) =>{
    localStorage.setItem("differentSort", difficultyVariable);
    localStorage.setItem("popularitySort",popularityVariable);
}
//After click on Sort by Popularity
const popularitySort = async (e) => {
     //first click
    if(difficultyVariable == "true")
        difficultyVariable = "false";
    //second click on same buton
    if(popularityVariable == "true")
        popularityVariable = "false";
    else
        popularityVariable = "true";

        saveSortElements();
        window.location.reload();

}
//Footer'daki Purrfect Recipes'a basınca homescreen'e gidiyor
export const Main = () => {
    
    const [recipes, setRecipes] = useState([]);
    const [ingredients, setIngredients] = useState([]);
    const [recipeOfTheDay, setRecipeOfTheDay] = useState([]);
    
    const fetchRecipes = async() => {
        
        let data = await getRecipes();
        /*
            This part for data selection if 2 of them are false we did not click any sort button 
           
            My popularitySort (line 45) and difficultySort (line 25) methods do not allow situation that 
            my variables' value can not be same.

            Line 91 and line 96 includes empty method block. 

        */
        if(difficultyVariable == "false" && popularityVariable =="false" )
            return data;  

        else if(difficultyVariable == "true")
            data=sortWithDifficulty(data);

        else if(popularityVariable == "true")
            data=sortWithPopularity(data);

        return data;
    }

    const sortWithPopularity= async(data) => {
        //some code for sorting
        return data;
    }
    //sort with difficulty
    const sortWithDifficulty = async(data) => {
        //some code for sorting
        return data;
    }

    //load sort element' from local 
    const loadSortElemetns = async() => {
        difficultyVariable = localStorage.getItem("differentSort");
        popularityVariable = localStorage.getItem("popularitySort");
    }
    const fetchRecipeOfTheDay = async() => {
        const data = await getRecipeOfTheDay();
        return data
    }
    
    useEffect(() => {
        (async function() {
            try {
                loadSortElemetns();
                const recipes = await fetchRecipes();
                setRecipes(recipes);
                const recipeOfTheDay = await fetchRecipeOfTheDay();
                setRecipeOfTheDay(recipeOfTheDay);
                //console.log(recipeOfTheDay)
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
                    <SortMenu>
                        <SortBar>

                            <SortButton 
                            type='button' 
                            onClick={difficultySort}>
                            Sort by Difficulty
                            </SortButton>

                        </SortBar>
                        <SortBar>

                            <SortButton 
                            type='button' 
                            onClick={popularitySort}>
                            Sort by Popularity
                            </SortButton>

                        </SortBar>
                        
                    </SortMenu>
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