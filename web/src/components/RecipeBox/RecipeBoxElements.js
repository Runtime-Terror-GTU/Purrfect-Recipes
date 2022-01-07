import styled from "styled-components";
import { Link } from "react-router-dom";

export const RecipeBoxContainer = styled.div`
    height: 175px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background: #B38A9B;
    auto-size: false;
    
    @media screen and (max-width: 768px) {
        height: 110px;
    }

    @media screen and (max-width: 480px) {
        height: 130px;
    }
`;

export const RecipeBoxWrapper = styled.div`
    max-width: 1000px;
    margin: 0 auto;
    display: grid;
    //grid-template-columns: 1fr 1fr 1fr;
    align-items: center;
    grid-gap: 16px;
    padding: 0 50px;
    auto-size: false;


    @media screen and (max-width: 1000px) {
        //grid-template-columns: 1fr 1fr;
    }

    @media screen and (max-width: 768px) {
        //grid-template-columns: 1fr;
        padding: 0 20px;
    }
`;

export const RecipeBoxCardContainer = styled(Link)`
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
    width: 500px;
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
        height: 100px;
    }

    @media screen and (max-width: 768px) {
        //grid-template-columns: 1fr;
        padding: 0 20px;
        width: 350px;
        height: 100px;
    }
`;

export const RecipeBoxCardWrapper = styled.div`
    max-width: 1000px;
    margin: 0 auto;
    display: grid;
    //grid-template-columns: 1fr 1fr;
    flex-direction: left;
    align-items: center;
    grid-gap: 16px;
    padding: 0 50px;
    auto-size: false;

    @media screen and (max-width: 1000px) {
        //grid-template-columns: 1fr 1fr;
    }

    @media screen and (max-width: 768px) {
        //grid-template-columns: 1fr;
        padding: 0 20px;
    }
`;

export const RecipeBoxIcon = styled.img`
    height: 100px;
    width: 100px;
    border-radius: 5px;

    //margin-bottom: 10px;
    @media screen and (max-width: 1000px) {
        height: 80px;
        width: 80px;
    }

    @media screen and (max-width: 768px) {
        height: 60px;
        width: 60px;
    }
`;

export const RecipeBoxH2 = styled.h2`
    font-size: 1rem;
    margin-bottom: 10px;
`;

export const RecipeBoxP = styled.p`
    font-size: 1rem;
    text-align: left;
`;

export const Img = styled.img`
    width: 40px;
    height: 30px;
    margin-top: 10px;
    @media screen and (max-width: 1000px) {
        width: 40px;
        height: 30px;
    }

    @media screen and (max-width: 768px) {
        width: 30px;
        height: 20px;
    }
`;