import React, { useState, useEffect } from 'react';
import RecipeScreen from '../components/RecipeScreen';
import ScrollToTop from '../components/ScrollToTop';
import MainNavBar from '../components/Main/MainNavBar';
import MainSideBar from '../components/Main/MainSideBar';

const RecipePage = () => {
    const [isOpen, setIsOpen] = useState(false);
    const toggle = () => {
        setIsOpen(!isOpen);
    };
    return (
        <>
            <ScrollToTop />
            <MainSideBar isOpen={isOpen} toggle={toggle} />
            <MainNavBar toggle={toggle} />
            <RecipeScreen />
        </>
    )
}

export default RecipePage;
