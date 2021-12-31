import React, { useState, useEffect } from 'react';
import RecipeBoxes from '../RecipeBoxes';
import SearchBar from '../SearchBarV2';
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

let easytohardVariable = "false";
let hardtoeasyVariable = "false";
let lesstomostVariable = "false";
let mosttolessVariable = "false";


//for difficulty easiest to hardest
const easytohard = async (e) => {
    //other button's variables
    if( hardtoeasyVariable === "true" || lesstomostVariable === "true" || mosttolessVariable === "true" ){
        hardtoeasyVariable = "false";
        lesstomostVariable = "false";
        mosttolessVariable = "false";
    }
        
   //second click on same buton
    if(easytohardVariable === "true")
        easytohardVariable = "false";
    //first click
    else
        easytohardVariable = "true";
        
    saveSortElements();
    window.location.reload();

}
//for difficulty hardest to easiest
const hardtoeasy = async (e) => {
    //other button's variables
    if(easytohardVariable === "true" || lesstomostVariable === "true" || mosttolessVariable === "true"){
        easytohardVariable = "false";
        lesstomostVariable = "false";
        mosttolessVariable = "false";
    }
   
    //second click on same buton
    if(hardtoeasyVariable === "true")
        hardtoeasyVariable = "false";
    //first click
    else
        hardtoeasyVariable = "true";
    
   saveSortElements();
   window.location.reload();
}
//for popularity min to max
const lesstomost = async (e) => {
    //other button's avriables
    if(easytohardVariable === "true" || hardtoeasyVariable === "true" || mosttolessVariable === "true"){
        easytohardVariable = "false";
        hardtoeasyVariable = "false";
        mosttolessVariable = "false";
    }
       
    //second click on same buton
    if(lesstomostVariable === "true")
        lesstomostVariable = "false";
    //first click
    else
        lesstomostVariable = "true";

    saveSortElements();
    window.location.reload();

}
//for popularity max to min 
const mosttoless = async (e) => {
    //other buttp's variables
    if(easytohardVariable === "true" || hardtoeasyVariable === "true" || lesstomostVariable === "true"){
        easytohardVariable = "false";
        hardtoeasyVariable = "false";
        lesstomostVariable = "false";
    }
      
    //second click on same buton
    if(mosttolessVariable === "true")
        mosttolessVariable = "false";
    //first click
    else
        mosttolessVariable = "true";
        
    saveSortElements();
    window.location.reload();

}


//save locally
const saveSortElements= async (e) =>{
    localStorage.setItem("easytohard", easytohardVariable);
    localStorage.setItem("hardtoeasy", hardtoeasyVariable);
    localStorage.setItem("lesstomost", lesstomostVariable);
    localStorage.setItem("mosttoless", mosttolessVariable);
}


//Footer'daki Purrfect Recipes'a basınca homescreen'e gidiyor
export const Main = () => {
    
    const [recipes, setRecipes] = useState([]);
    const [ingredients, setIngredients] = useState([]);
    const [recipeOfTheDay, setRecipeOfTheDay] = useState([]);
    
    const fetchRecipes = async() => {
        
        let data = await getRecipes();
        /*
            This part for data selection if 4 of them are false we did not click any sort button 
           
            Methods which start on line 29-49-68-88, do not allow situations that  variable's value can be same.

            Line 155-159-164-169 includes empty method block. 183 is optionaly if you do not use you can delete that method block

        */
        if(easytohardVariable === "false" && hardtoeasyVariable === "false" && lesstomostVariable === "false" && mosttolessVariable === "false")
            return data;  

        else if(easytohardVariable === "true")
            data = sortEasytoHard(data);

        else if(hardtoeasyVariable === "true")
            data = sortHardtoEasy(data);

        else if(lesstomostVariable === "true")
            data = sortLesstoMost(data);

        else if(mosttolessVariable === "true")
            data = sortMosttoLess(data);
        return data;
    }

    //data1 data2 den zorsa true döner
    function compareDifficulty(data1,data2) {

            if(data2.R_RecipeDifficulty==="Easy"){
                if(data1.R_RecipeDifficulty==="Easy") return false;
                else return true;
            }
            else if(data2.R_RecipeDifficulty==="Medium"){
                if(data1.R_RecipeDifficulty==="Hard") return true;
                else return false;
            }
            else if(data2.R_RecipeDifficulty==="Hard"){
                return false;
            }

    }
    
    //for sorting easy to hard
    const sortEasytoHard= async(data) => {

            let temp;
            let j;
            for (let i = 1; i < data.length; i++) {
                j = i;         
                temp = data[i];
                let compare =compareDifficulty(data[j - 1],temp);
                while (j > 0 && compare===true) {
                    compare = compareDifficulty(data[j - 1],temp);
                    data[j] = data[j - 1];
                    j--;
                }
                data[j] = temp;
            }
            return data;
    }

   //for sorting hardest to easiest
    const sortHardtoEasy = async(data) => {
            let temp;
            let j;
            for (let i = 1; i < data.length; i++) {
                j = i;         
                temp = data[i];
                let compare =compareDifficulty(data[j - 1],temp);
                while (j > 0 && compare===false) {
                    compare = compareDifficulty(data[j - 1],temp);
                    data[j] = data[j - 1];
                    j--;
                }
                data[j] = temp;
            }
            return data;
    }

    //for sorting min to max
    const sortLesstoMost = async(data) => {

            let temp;
            let j;
            let compare;
            
            for (let i = 1; i < data.length; i++) {
                j = i;
                temp = data[i];
                compare= Number(data[j - 1].R_RecipePurrfectedCount)>Number(temp.R_RecipePurrfectedCount);

                while (j > 0 && compare===true ){
                    data[j] = data[j - 1];
                    j--;
                    if(j>0) compare= Number(data[j - 1].R_RecipePurrfectedCount)>Number(temp.R_RecipePurrfectedCount);
                }
                data[j] = temp;
            }

            return data;
    }

    //for sorting max to min 
    const sortMosttoLess = async(data) => {
            let temp;
            let j;
            let compare;
            
            for (let i = 1; i < data.length; i++) {
                j = i;
                temp = data[i];
                compare= Number(data[j - 1].R_RecipePurrfectedCount)<Number(temp.R_RecipePurrfectedCount);

                while (j > 0 && compare===true ){
                    data[j] = data[j - 1];
                    j--;
                    if(j>0) compare= Number(data[j - 1].R_RecipePurrfectedCount)<Number(temp.R_RecipePurrfectedCount);
                }
                data[j] = temp;
            }
            return data;
    }


    //load sort element' from local 
    const loadSortElemetns = async() => {
        easytohardVariable = localStorage.getItem("easytohard");
        hardtoeasyVariable = localStorage.getItem("hardtoeasy");
        lesstomostVariable = localStorage.getItem("lesstomost");
        mosttolessVariable = localStorage.getItem("mosttoless"); 
    }
    const fetchRecipeOfTheDay = async() => {
        const data = await getRecipeOfTheDay();
        return data;
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
                            <SearchBar placeholder={"Search by Recipe Name"} />
                        </SearchCard>
                        
                    </SearchWrapper>
                   
                    <RecipeWrapper>
                        <SortMenu>
                            <SortBar>
                                <SortButton 
                                type='button' 
                                onClick={easytohard}>
                                Easiest to Hardest
                                </SortButton>
                            </SortBar>
                            <SortBar>
                                <SortButton 
                                type='button' 
                                onClick={hardtoeasy}>
                                Hardest to Easiest
                                </SortButton>
                            </SortBar>
                            <SortBar>
                                <SortButton 
                                type='button' 
                                onClick={mosttoless}>
                                Most Popularity
                                </SortButton>
                            </SortBar>
                            <SortBar>
                                <SortButton 
                                type='button' 
                                onClick={lesstomost}>
                                Less Popularity
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