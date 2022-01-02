import styled from "styled-components";
import { FaPaw } from 'react-icons/fa';

export const CommentContainer = styled.div`
    color: #fff;
    background: #B38A9B;
    display: grid;
    grid-template-columns: 1fr 1fr;
    margin-top: 10px;
    border-bottom: 5px solid white;
    @media screen and (max-width: 768px) {
        padding: 10px 0;
    }
    align-items: flex-start;
    width: %100;
`;

export const ImgContainer = styled.div`
    color: white;
    background: #B38A9B;
    //padding: 50px 0;
    grid-template-rows: 1fr 1fr;
    text-align: center;
    align-items: flex-start;
    width: 50%;

    @media screen and (max-width: 768px) {
    }
`;

export const Img = styled.img`
    width: 50px;
    height: 50px;
    align-items: flex-start;

    border-radius: 100px;
    margin: 0 0 10px 0;
    padding-right: 0;
    @media screen and (max-width: 1000px) {
        width: 100%;
        height: 100%;
    }

    @media screen and (max-width: 768px) {
        width: 100%;
        height: 100%;
    }
`;

export const TextContainer = styled.div`
    color: #B38A9B;
    background: #fff;
    align-items: baseline;
    -moz-box-shadow: 0 0 5px 3px #9d9d9d;
    -webkit-box-shadow: 0 0 3px 5px #9d9d9d;
    box-shadow: 0 0 5px 3px #9d9d9d;
    width: %100;

    @media screen and (max-width: 768px) {
    }
`;

export const CommentContents = styled.p`
    max-width: 440px;
    align-text: center;
    margin-bottom: 35px;
    font-size: 18px;
    line-height: 24px;
    color: black;

`; 
