import styled from "styled-components";
//import { Link } from 'react-router-dom';

export const FormInput = styled.input`
    padding: 16px 16px;
    margin-bottom: 10px; 
    border: none;
    border-radius: 4px;
`;

export const FormWrap = styled.div`
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin-top: 10px;
    margin-bottom: 10px;

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
    background: #f0bdd0;
    max-width: 400px;
    height: 20px;
    width: 100%;
    z-index: 1;
    display: grid;
    margin: 10 auto;
    padding: 80px 32px;
    border-radius: 4px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.9);

    @media screen and (max-width: 400px) {
        padding: 32px 32px;
    }
`;
