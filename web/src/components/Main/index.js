import React, { useState, useEffect } from 'react';
import RecipeBoxes from '../RecipeBoxes';
import MainNavBar from '../MainNavBar';
import MainSideBar from '../MainSideBar';

import Footer from '../Footer';
import { getRecipes } from '../../backend/RecipeService';


//Footer'daki Purrfect Recipes'a basınca homescreen'e gidiyor
export const Main = () => {
    
    const [recipes, setRecipes] = useState([]);
    
    const fetchRecipes = async() => {
        const { data } = await getRecipes();
        setRecipes(data);
    }

    useEffect(() => {
        fetchRecipes();
    }, []);

    const [isOpen, setIsOpen] = useState(false);

    const toggle = () => {
        setIsOpen(!isOpen);
    };

    return (
        <div>
            <MainSideBar isOpen={isOpen} toggle={toggle} />
            <MainNavBar toggle={toggle} />
            <h1>selam naber ess</h1>
            <h1>selam naber ess</h1>
            <h1>selam naber ess</h1>
            <h1>selam naber ess</h1>
            <h1>selam naber ess</h1>
            <h1>selam naber ess</h1>
            <h1>selam naber ess</h1>
            <h1>selam naber ess</h1>
            <RecipeBoxes recipes={recipes} />
            <Footer />
        </div>
    )
}

export default Main;