import styled from "styled-components";
import { FaPaw } from 'react-icons/fa';

export const CommentContainer = styled.div`
    color: #fff;
    background: #B38A9B;
    display: grid;
    align-items: center;
    grid-template-columns: 1fr 1fr;
    margin-bottom: 30px;
    border-bottom: 5px solid pink;
    @media screen and (max-width: 768px) {
        padding: 10px 0;
    }
`;

export const ImgContainer = styled.div`
    color: white;
    background: #B38A9B;
    //padding: 50px 0;
    text-align: center;
    align-items: center;
    grid-template-rows: 1fr 1fr;
    @media screen and (max-width: 768px) {
    }
`;

export const Img = styled.img`
    width: 50px;
    height: 50px;
    border-radius: 100px;
    margin: 0 0 10px 0;
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
    background: #F9C5D5;

    text-align: center;
    align-items: center;

    -moz-box-shadow: 0 0 5px 3px #F9C5D5;
    -webkit-box-shadow: 0 0 3px 5px #F9C5D5;
    box-shadow: 0 0 5px 3px #F9C5D5;

    @media screen and (max-width: 768px) {
    }
`;

export const CommentContents = styled.p`
    max-width: 440px;
    align-text: center;
    font-size: 18px;
    line-height: 24px;
    color: grey;

`; 
