import React, { useState } from 'react';
import HeroSection from '../components/HomePage/HeroSection';
import InfoSection from '../components/HomePage/InfoSection';
import { 
    homeObjOne, 
    homeObjTwo, 
    homeObjThree 
} from '../components/HomePage/InfoSection/Data';
import Navbar from '../components/HomePage/Navbar';
import Sidebar from '../components/HomePage/Sidebar';
import Services from '../components/HomePage/Services';
import Footer from '../components/HomePage/Footer';

/** 
 * HOME PAGE
 */
const Home = () => {
    const [isOpen, setIsOpen] = useState(false);

    const toggle = () => {
        setIsOpen(!isOpen);
    };

    return (
        <>
            <Sidebar isOpen={isOpen} toggle={toggle} />
            <Navbar toggle={toggle} />
            <HeroSection />
            <InfoSection {...homeObjOne} />
            <InfoSection {...homeObjTwo} />
            <Services />
            <InfoSection {...homeObjThree} />
            <Footer />
        </>
    );
};

export default Home;
