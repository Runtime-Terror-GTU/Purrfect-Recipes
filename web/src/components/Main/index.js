import React, { useState, useEffect } from 'react';
import RecipeBoxes from '../RecipeBoxes';
import IngredientButtons from './IngredientButtons';

import SearchBar from '../SearchBar';
import Footer from '../HomePage/Footer';
import { getRecipes, getIngredients } from '../../backend/RecipeValueListener';
import {
    MainContainer,
    MainWrapper,
    SearchWrapper,
    RecipeWrapper,
    OtherWrapper,
    MainIcon,
    MainH2,
    MainP,
    SearchCard
} from './MainElements';

//Footer'daki Purrfect Recipes'a basınca homescreen'e gidiyor
export const Main = () => {
    
    const [recipes, setRecipes] = useState([]);
    const [ingredients, setIngredients] = useState([]);

    const fetchRecipes = async() => {
        const data = await getRecipes();
        console.log("data");
        console.log(data);
        return data;
    }

    const fetchIngredients = async() => {
        const data = await getIngredients();
        console.log("data");
        console.log(data);
        return data;
    }

    useEffect(() => {
        (async function() {
            try {
                const data = await fetchRecipes();
                setRecipes(data);
                const ingredient = await fetchIngredients();
                setIngredients(ingredient)
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
                        <MainH2> esss  </MainH2>
                        <MainP> by ess </MainP>
                    </OtherWrapper>    

                </MainWrapper>
            </MainContainer>

            <Footer />
        </>
    )
}

export default Main;