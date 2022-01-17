import React, { useState, useEffect } from 'react'
import { 
    SidebarContainer, 
    Icon, 
    CloseIcon, 
    SidebarWrapper, 
    SideBtnWrap, 
    SidebarRoute 
} from '../../HomePage/Sidebar/SidebarElements';
import { IngredientList, TagList } from '../../../backend/RecipeValueListener';

const Sidebar = ({isOpen, toggle}) => {

    const [allTags, setTags] = useState([]);
    const [allIngredients, setIngredients] = useState([]);

    const fetchTags = async() => {
        let data = await TagList();
        return data;
    }

    const fetchIngredients = async() => {
        let data = await IngredientList();
        return data;
    }

    useEffect(() => {
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

    return (
        <SidebarContainer isOpen={isOpen} onClick={toggle} >
            
            <Icon onClick={toggle } >
                <CloseIcon />
            </Icon>

            <SidebarWrapper>
                <SideBtnWrap>
                    <SidebarRoute 
                    onClick={(e) => {localStorage.setItem("allTags", JSON.stringify(allTags))
                    localStorage.setItem("allIngredients", JSON.stringify(allIngredients))
                    } }
                    to="/addrecipe">Add a Purrfect Recipe</SidebarRoute>
                </SideBtnWrap>
                <SideBtnWrap>
                    <SidebarRoute to="/profile">Profile</SidebarRoute>
                </SideBtnWrap>
            </SidebarWrapper>

        </SidebarContainer>
    )
}

export default Sidebar; 
