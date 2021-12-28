import styled from "styled-components";
import { Link } from "react-router-dom";

export const RecipeBoxContainer = styled.div`
    display: grid;
    flex-direction: row;
    justify-content: center;
    align-items: center;

    @media screen and (max-width: 768px) {
    }

    @media screen and (max-width: 480px) {
    }
`;

export const RecipeBoxWrapper = styled.div`
    display: flex;
    background: #B38A9B;
    align-items: center;
    @media screen and (max-width: 1000px) {
        padding: 0 50px;
    }

    @media screen and (max-width: 768px) {
        padding: 0 20px;
    }
`;

export const RecipeBoxCardContainer = styled(Link)`
    background: #fff;
    display: center;
    /margin: auto;
    padding: 10px 10px;
    flex-direction: column;
    align-items: center;
    border-radius: 10px;
    box-shadow: 0 1px 3 px rgba(0,0,0,0.2);
    transtion: all 0.2s ease-in-out;
    overflow: auto;
    box-sizing: content-box;
    grid-template-columns: 1fr;
    grid-template-rows: 1fr 1fr 1fr 1fr;
    color: #010606;
    font-size: 16px;
    outline: none;
    border: none;
    text-decoration: none;

    @media screen and (max-width: 1000px) {
        grid-template-rows: 1fr;
        grid-template-columns: 1fr 1fr 1fr 1fr;
        align-items: center;
        margin-bottom: 10px;   
        width: 400px;
        padding: 30px;
    }

    @media screen and (max-width: 768px) {
        grid-template-rows: 1fr;
        grid-template-columns: 1fr 1fr 1fr 1fr;
        padding: 0 20px;
        width: 350px;
    }
`;

export const RecipeBoxCardWrapper = styled.div`
    display: grid;
    flex-direction: left;
    align-items: top;
    grid-gap: 16px;
    auto-size: false;

    @media screen and (max-width: 1000px) {    
        align-items: center;
        padding: 0 20px;
    }

    @media screen and (max-width: 768px) {
        padding: 0 20px;
    }
`;

export const RecipeBoxIcon = styled.img`
    height: 300px;
    width: 280px;

    border-radius: 100px;
    align-items: center;
    margin-bottom: 10px;
    @media screen and (max-width: 1000px) {
        height: 300px;
        width: 280px;
        margin-left: auto;
        margin-right: auto;
        border-radius: 100px;
        align-items: center;
        margin-bottom: 10px;
        padding: 0 20px;
    }

    @media screen and (max-width: 768px) {
        height: 200px;
        width: 200px;
        padding: 0 20px;
    }
`;

export const RecipeBoxH2 = styled.h2`
    font-size: 1rem;
    margin-bottom: 10px;
    text-align: center;
`;

export const RecipeBoxP = styled.p`
    font-size: 1rem;
    margin-bottom: 10px;
    text-align: center;
`;