import React from 'react';
import { 
    SidebarContainer, 
    Icon, 
    CloseIcon, 
    SidebarWrapper, 
    SidebarMenu, 
    SidebarLink, 
    SideBtnWrap, 
    SidebarRoute 
} from './SidebarElements';


const Sidebar = ({isOpen, toggle}) => {
    return (
        <SidebarContainer isOpen={isOpen} onClick={toggle} >
            
            <Icon onClick={toggle } >
                <CloseIcon />
            </Icon>

            <SidebarWrapper>
                <SidebarMenu>
                    <SidebarLink to="about" onClick={toggle}>About</SidebarLink>
                    <SidebarLink to="why" onClick={toggle}>Why</SidebarLink>
                    <SidebarLink to="premium" onClick={toggle}>Premium Account</SidebarLink>
                    <SidebarLink to="decide" onClick={toggle}>Sign Up</SidebarLink>
                </SidebarMenu>

                <SideBtnWrap>
                    <SidebarRoute to="signin">Sign In</SidebarRoute>
                </SideBtnWrap>
            </SidebarWrapper>




        </SidebarContainer>
    )
}

export default Sidebar; 
