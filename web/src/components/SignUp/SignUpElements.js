import styled from "styled-components";
import { Link } from 'react-router-dom';

export const Container = styled.div`
    min-height: 692px;
    position: relative;
    bottom: 0;
    left: 0;
    right: 0;
    top: 0;
    z-index: 0;
    overflow: hidden;
    background: linear-gradient(
        108deg,
        rgba(239, 184, 205, 1) 0%,
        rgba(245, 210, 224, 1) 100%
    );
`;

export const FormWrap = styled.div`
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin-top: 32px;
    margin-bottom: 32px;

    @media screen and (max-width: 400px) {
        height: 80%;
    }
`;

export const FormContent = styled.div`
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;

    @media screen and (max-width: 480px) {
        padding: 10px;
    }
`;

export const Form = styled.form`
    background: #00072D;
    max-width: 400px;
    height: auto;
    width: 100%;
    z-index: 1;
    display: grid;
    margin: 0 auto;
    padding: 80px 32px;
    border-radius: 4px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.9);

    @media screen and (max-width: 400px) {
        padding: 32px 32px;
    }
`;

export const FormH1 = styled(Link)`
    margin-bottom: 40px;
    text-align: center;
    
    font-family: 'Leckerli One', cursive;
    font-size: 52px;
    background: #00072D;
    background: -webkit-linear-gradient(
        to right,
        #00072D,
        #f5d2e0
    );
    background: linear-gradient(
        to right,
        #f5eed2,
        #00072D
    );

    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
`;

export const FormLabel = styled.label`
    margin-bottom: 8px;
    font-size: 14px;
    color: #fff;
`;

export const FormInput = styled.input`
    padding: 16px 16px;
    margin-bottom: 16px; //32 idi
    border: none;
    border-radius: 4px;
`;

export const FormButton = styled.button`
    background: #B38A9B;
    padding: 16px 0;
    border: none;
    border-radius: 4px;
    color: #fff;
    font-size: 20px;
    cursor: pointer;
`;

export const Text = styled(Link)`
    margin-left: 0px;
    margin-top: -14px;
    text-decoration: none;
    color: pink;
    font-weight: 700;
    font-size: 10px;

    @media screen and (max-width: 480px) {
        margin-left: 16px;
        margin-top: 8px;
    }
`;

export const Button = styled(Link)`
    border-radius: 50px;
    margin-top: 8px;
    background: ${({primary}) => (primary ? '#B38A9B' : '#010606')};
    //white-space: nowrap;
    padding: ${({big}) => (big ? '14px 48px' : '12px 30px')};
    color: ${({dark}) => (dark ? '#00072D' : '#fff')};
    font-size: ${({fontBig}) => (fontBig ? '20px' : '16px')};
    outline: none;
    border: none;
    cursor: pointer;
    display: flex;
    justify-content: start;
    align-items: start;
    transition: all 0.2s ease-in-out;

    &:hover {
        transition: all 0.2s ease-in-out;
        background: ${({primary}) => (primary ? '#fff' : '#B38A9B')};
    }
`;