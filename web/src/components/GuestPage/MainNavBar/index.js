//type rafce and press enter
import React, { useState, useEffect } from 'react'
import { FaBars } from 'react-icons/fa';
import { IconContext } from 'react-icons/lib';
import { animateScroll as scroll } from 'react-scroll';
import { 
    Nav, 
    NavbarContainer, 
    NavLogo, 
    MobileIcon, 
    NavBtn, 
    NavBtnLink 
} from '../../HomePage/Navbar/NavbarElements';
import { IngredientList, TagList } from '../../../backend/RecipeValueListener';

const MainNavBar = ({toggle}) => {
    const [scrollNav, setScrollNav] = useState(false);
    const [allTags, setTags] = useState([]);
    const [allIngredients, setIngredients] = useState([]);

    const changeNav = () => {
        if(window.scrollY >= 80){
            setScrollNav(true);
        } else{
            setScrollNav(false);
        }
    }

    const fetchTags = async() => {
        let data = await TagList();
        return data;
    }

    const fetchIngredients = async() => {
        let data = await IngredientList();
        return data;
    }

    useEffect(() => {
        window.addEventListener('scroll', changeNav);
        (async function() {
            try {
                const allTags = await fetchTags();
                const allIngredients = await fetchIngredients();
                setTags(allTags);
                setIngredients(allIngredients);
            } catch (e) {
                console.error(e);
            }
        })();
    }, [])

    const toggleHome = () => {
        scroll.scrollToTop();
    }

    return (
        <>
            <IconContext.Provider value={{color: '#F9C5D5'}}>
                <Nav setScrollNav={scrollNav}>
                    <NavbarContainer>

                        <NavLogo to='/mainpage' onClick={toggleHome}>
                            Purrfect Recipes
                        </NavLogo>

                        <MobileIcon onClick={toggle}>
                            <FaBars />
                        </MobileIcon>

                        <NavBtn>
                            <NavBtnLink to="/signin">Signin</NavBtnLink>
                        </NavBtn>

                        <NavBtn>
                            <NavBtnLink to="/signup" >
                                Sign up
                            </NavBtnLink>
                        </NavBtn>

                    </NavbarContainer>
                </Nav>
            </IconContext.Provider>
        </>
        
    );
};

export default MainNavBar;
