import styled from "styled-components";
import { Link } from 'react-router-dom';

export const FormInput = styled.input`
    padding: 16px 16px;
    margin-bottom: 16px; //32 idi
    border: none;
    border-radius: 4px;
`;
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
