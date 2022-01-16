import React, { useState, useEffect } from 'react';
import ScrollToTop from '../components/ScrollToTop';
import MainNavBar from '../components/Main/MainNavBar';
import MainSideBar from '../components/Main/MainSideBar';
import EditRecipeScreen from '../components/AddEditRecipeScreen/EditRecipeScreen';

const EditRecipe = () => {
    const [isOpen, setIsOpen] = useState(false);

    const toggle = () => {
        setIsOpen(!isOpen);
    };
    return (
        <>
            <ScrollToTop />
            <MainSideBar isOpen={isOpen} toggle={toggle} />
            <MainNavBar toggle={toggle} />
            <EditRecipeScreen />
        </>
    )
}

export default EditRecipe;