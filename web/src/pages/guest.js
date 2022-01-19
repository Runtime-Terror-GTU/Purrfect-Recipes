import React, { useState, useEffect } from 'react';
import ScrollToTop from '../components/ScrollToTop';
import MainNavBar from '../components/GuestPage/MainNavBar';
import MainSideBar from '../components/GuestPage/MainSideBar';
import GuestPage from '../components/GuestPage';

const Guest = () => {
    const [isOpen, setIsOpen] = useState(false);

    const toggle = () => {
        setIsOpen(!isOpen);
    };

    return (
        <>
            <ScrollToTop />
            <MainSideBar isOpen={isOpen} toggle={toggle} />
            <MainNavBar toggle={toggle} />
            <GuestPage />
        </>
    )
}

export default Guest;