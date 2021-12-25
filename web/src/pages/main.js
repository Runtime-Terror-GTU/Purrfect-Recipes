import React, { useState, useEffect } from 'react';
import ScrollToTop from '../components/ScrollToTop';
import Main from '../components/Main';
import MainNavBar from '../components/Main/MainNavBar';
import MainSideBar from '../components/Main/MainSideBar';

const MainPage = () => {
    const [isOpen, setIsOpen] = useState(false);

    const toggle = () => {
        setIsOpen(!isOpen);
    };
    return (
        <>
            <ScrollToTop />
            <MainSideBar isOpen={isOpen} toggle={toggle} />
            <MainNavBar toggle={toggle} />
            <Main />
        </>
    )
}

export default MainPage;