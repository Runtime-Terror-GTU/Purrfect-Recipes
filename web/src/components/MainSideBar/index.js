import React from 'react';
import { 
    SidebarContainer, 
    Icon, 
    CloseIcon, 
    SidebarWrapper, 
    SideBtnWrap, 
    SidebarRoute 
} from '../Sidebar/SidebarElements';


const Sidebar = ({isOpen, toggle}) => {
    return (
        <SidebarContainer isOpen={isOpen} onClick={toggle} >
            
            <Icon onClick={toggle } >
                <CloseIcon />
            </Icon>

            <SidebarWrapper>
                <SideBtnWrap>
                    <SidebarRoute to="/profile">Profile</SidebarRoute>
                </SideBtnWrap>
            </SidebarWrapper>

        </SidebarContainer>
    )
}

export default Sidebar; 
