import styled from "styled-components";
import { Link } from "react-router-dom";

export const MainContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background: #fff;
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
    align-items: center;
    //padding: 0 50px;

    @media screen and (max-width: 1000px) {
        grid-template-columns: 1fr 1fr;
    }

    @media screen and (max-width: 768px) {
        grid-template-columns: 1fr;
        //padding: 0 20px;
    }
`;

export const MainCardContainer = styled(Link)`
    background: #fff;
    display: flex;
    flex-direction: row;
    //justify-content: flex-start;
    align-items: center;
    border-radius: 10px;
    max-height: 340px;
    padding: 30px;
    box-shadow: 0 1px 3 px rgba(0,0,0,0.2);
    transtion: all 0.2s ease-in-out;
    overflow: auto;
    box-sizing: content-box;
    //width: 50px;
    &:hover {
        transform: scale(1.02);
        transition: all 0.2s ease-in-out;
        cursor: pointer;
    }
//    white-space: nowrap;
    color: #010606;
    font-size: 16px;
    outline: none;
    border: none;
    text-decoration: none;

    @media screen and (max-width: 1000px) {
        //grid-template-columns: 1fr 1fr;
        width: 400px;
    }

    @media screen and (max-width: 768px) {
        //grid-template-columns: 1fr;
        padding: 0 20px;
        width: 350px;
    }
`;

export const MainCardWrapper = styled.div`
    max-width: 1000px;
    margin: 0 auto;
    display: grid;
    //grid-template-columns: 1fr 1fr;
    flex-direction: left;
    align-items: center;
    grid-gap: 16px;
    auto-size: false;


    @media screen and (max-width: 1000px) {
        //grid-template-columns: 1fr 1fr;
    }

    @media screen and (max-width: 768px) {
        //grid-template-columns: 1fr;
        padding: 0 20px;
    }
`;

export const SearchWrapper = styled.div`
    display: grid;
    flex-direction: left;
    align-items: top;
    //grid-gap: 16px;
    background: black;
    @media screen and (max-width: 1000px) {
        //grid-template-columns: 1fr 1fr;
    }

    @media screen and (max-width: 768px) {
        //grid-template-columns: 1fr;
        padding: 0 20px;
    }
`;

export const RecipeWrapper = styled.div`
    display: grid;
    flex-direction: left;
    align-items: top;
    //grid-gap: 16px;
    background: red;
    @media screen and (max-width: 1000px) {
        //grid-template-columns: 1fr 1fr;
    }

    @media screen and (max-width: 768px) {
        //grid-template-columns: 1fr;
        padding: 0 20px;
    }
`;

export const OtherWrapper = styled.div`
    display: grid;
    flex-direction: left;
    align-items: top;
    //grid-gap: 16px;
    background: yellow;
    @media screen and (max-width: 1000px) {
        //grid-template-columns: 1fr 1fr;
    }

    @media screen and (max-width: 768px) {
        //grid-template-columns: 1fr;
        padding: 0 20px;
    }
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