import styled from "styled-components";
import { Link } from "react-router-dom";

export const MainContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background: #B38A9B;
    margin-top: -80px;

    @media screen and (max-width: 768px) {
    }

    @media screen and (max-width: 480px) {
    }
`;

export const MainWrapper = styled.div`
    margin-top: 160px;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    align-items: top;

    @media screen and (max-width: 1000px) {
        grid-template-columns: 1fr;
    }

    @media screen and (max-width: 768px) {
        grid-template-columns: 1fr;
        //padding: 0 20px;
    }
`;

export const SearchWrapper = styled.div`
    display: grid;
    grid-gap: 0px;
    padding: 1px;
    flex-direction: left;
    align-items: top;
    grid-template-rows: repeat(4, 80px);
    grid-template-columns: 1fr;
    background: #B38A9B;

    //grid-gap: 16px;
    background: #B38A9B;
    @media screen and (max-width: 1000px) {
        grid-template-columns: 1fr 1fr;
    }

    @media screen and (max-width: 768px) {
        grid-template-columns: 1fr;
        padding: 0 20px;
    }
`;

export const SearchCard = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
    border-radius: 10px;
    max-height: 340px;
    padding: 30px;
    box-shadow: 0 1px 3 px rgba(0,0,0,0.2);
    transtion: all 0.2s ease-in-out;
    background: #B38A9B;

    &:hover {
        transform: scale(1.02);
        transition: all 0.2s ease-in-out;
        cursor: pointer;
    }
`;

export const RecipeWrapper = styled.div`
    display: grid;
    flex-direction: left;
    align-items: top;
    //grid-gap: 16px;
    background: #B38A9B;
    @media screen and (max-width: 1000px) {
        grid-template-columns: 1fr;
    }

    @media screen and (max-width: 768px) {
        grid-template-columns: 1fr;
        padding: 0 20px;
    }
`;

export const OtherWrapper = styled.div`
    display: grid;
    grid-template-rows: 1fr 1fr;
    grid-template-columns: 1fr;
    //align-items: center;
    //padding: 0 -50px;
    background: #B38A9B;

    @media screen and (max-width: 1000px) {
        grid-template-columns: 1fr;
    }

    @media screen and (max-width: 768px) {
        grid-template-columns: 1fr;
        padding: 0 20px;
    }
`;

export const OtherCard = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
    background: #B38A9B;

`;


export const MainIcon = styled.img`
    height: 100px;
    width: 100px;
    border-radius: 5px;

    //margin-bottom: 10px;
    @media screen and (max-width: 1000px) {
        height: 75px;
        width: 75px;
    }

    @media screen and (max-width: 768px) {
        height: 50px;
        width: 50px;
    }
`;

export const MainH2 = styled.h2`
    font-size: 1rem;
    margin-bottom: 10px;
`;

export const MainP = styled.p`
    font-size: 1rem;
    text-align: left;
`;

export const SortMenu = styled.ul`
    display: flex;
    align-items: center;
    list-style: none;
    text-align: center;
    margin-right: -22px;
    margin-top: 20px;

    @media screen and (max-width: 768px) {
        display: none;
    }
`;

export const SortBar = styled.li`
    height: 80px;
`;

export const SortButton = styled.button`
    background: #000000;
    padding: 16px 0;
    //border: 2px solid palevioletred;
    margin-left: 8px;
    border: none;
    border-radius: 25px;
    text-align: center;
    color: #fff;
    font-size: 18px;
    cursor: pointer;
    
`;
/*
export const SortButtons = styled.div`
    white-space: nowrap;

    outline: none;
    border: none;

    display: flex;
    justify-content: center;
    align-items: center;
    grid-template-rows: 1fr 1fr;
    grid-template-columns: 1fr 1fr;

    @media screen and (max-width: 1000px) {
        grid-template-columns: 1fr;
    }

    @media screen and (max-width: 768px) {
        grid-template-columns: 1fr;
        padding: 0 20px;
    }

`;

export const SortButton = styled(Link)`
    border-radius: 50px;
    background: ${({primary}) => (primary ? '#F9C5D5' : '#F9C5D5')};
    white-space: nowrap;
    padding: ${({big}) => (big ? '14px 48px' : '12px 30px')};
    color: ${({dark}) => (dark ? '#010606' : '#fff')};
    font-size: ${({fontBig}) => (fontBig ? '20px' : '16px')};
    outline: none;
    border: none;
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: all 0.2s ease-in-out;

    &:hover {
        transition: all 0.2s ease-in-out;
        background: ${({primary}) => (primary ? '#fff' : '#B38A9B')};
    }
`;

*/