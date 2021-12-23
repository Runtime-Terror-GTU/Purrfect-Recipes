import React, { useState, useEffect } from 'react';
import RecipeBoxes from '../RecipeBoxes';
import MainNavBar from '../MainNavBar';
import MainSideBar from '../MainSideBar';
import SearchBar from '../SearchBar';
import Footer from '../Footer';
import { getRecipes } from '../../backend/RecipeValueListener';
import {
    MainContainer,
    MainWrapper,
    SearchWrapper,
    RecipeWrapper,
    OtherWrapper,
    MainIcon,
    MainH2,
    MainP,
    MainCardWrapper
} from './MainElements';

//Footer'daki Purrfect Recipes'a basınca homescreen'e gidiyor
export const Main = () => {
    
    const [recipes, setRecipes] = useState([]);
    
    const fetchRecipes = async() => {
        const data = await getRecipes();
        console.log("data");
        console.log(data);
        return data;
    }

    useEffect(() => {
        (async function() {
            try {
                const data = await fetchRecipes();
                setRecipes(data);
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
                        <SearchBar/>
                    </SearchWrapper>

                    <RecipeWrapper>
                        <RecipeBoxes recipes={recipes} />
                        <MainH2> esss  </MainH2>
                        <MainP> by ess </MainP>
                    </RecipeWrapper>

                    <OtherWrapper>
                        <MainH2> esss  </MainH2>
                        <MainP> by ess </MainP>
                    </OtherWrapper>    

                </MainWrapper>
            </MainContainer>
            <h1>as nejdet</h1>
            <h1>sa sevval</h1>
            <h1>as ess</h1>
            <Footer />
        </>
    )
}

export default Main;