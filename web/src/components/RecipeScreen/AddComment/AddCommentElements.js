import styled from "styled-components";
import { Button } from 'semantic-ui-react';

export const AddCommentContainer = styled.div`
    background: #B38A9B;
    padding: 0 24px;

    @media screen and (max-width: 768px) {
        padding: 0 24px;
    }
`;

export const TextAreaBox = styled.textarea`
    display: flex;
    width: 100%;
    padding: 0 30px;
    justify-content: left;
    @media screen and (max-width: 768px) {
        display: flex;
    }
`;

export const UploadButton = styled(Button)`
    background: #F9C5D5;
    outline: none;
    border: none;
    cursor: pointer;
    transition: all 0.2s ease-in-out;
    text-decoration: none;
    border-radius: 50px;
    width: 200px;
    height: 100px;
    &:hover {
        transform: scale(1.02);
        transition: all 0.2s ease-in-out;
        cursor: pointer;
    }
`;

