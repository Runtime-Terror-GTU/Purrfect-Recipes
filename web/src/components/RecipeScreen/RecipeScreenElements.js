import styled from "styled-components";
import { FaPaw } from 'react-icons/fa';

export const RecipeContainer = styled.div`
    color: #fff;
    background: #B38A9B;
    margin-top: 80px;

    @media screen and (max-width: 768px) {
        padding: 10px 0;
    }
`;

export const RecipeWrapper = styled.div`
    display: flex;
    width: 100%;
    padding: 0 24px;
    justify-content: left;
    @media screen and (max-width: 768px) {
        display: grid;
    }
`;

export const RecipeColumn = styled.div`
    display: grid;
    grid-template-columns: 5fr 1fr;
    align-items: left;
    grid-template-areas: ${({imgStart}) => (imgStart ? `'col2 col1'` : `'col1 col2'`)};
    padding: 0 50px;

    @media screen and (max-width: 768px) {
        grid-template-areas: ${({imgStart}) => (imgStart ? `'col1' 'col2'` : `'col1 col1' 'col2 col2'`)};
    }
`;

export const Column1 = styled.div`
    margin-bottom: 15px;
    padding: 0 15px;
    grid-area: col1;
`;

export const Column2 = styled.div`
    margin-bottom: 15px;
    padding: 0 15px;
    grid-area: col2;
`;

export const TopLine = styled.p`
    color: white;
    font-size: 16px;
    line-height: 16px;
    font-weight: 700;
    letter-soacing: 1.4px;
    text-transform: uppercase;
    margin-top: 30px;

`;

export const Heading = styled.h1`
    margin-bottom: 24px;
    font-size: 48px;
    line-height: 1.1;
    font-weight: 600;
    color: #f7f8fa;
    
    @media screen and (max-width: 480px) {
        font-size: 32px;
    }
`;

export const ImgWrap = styled.div`
    max-width: 555px;
    height: 100%;
`;

export const Img = styled.img`
    width: 500px;
    height: 500px;

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

export const PurrfectedRow = styled.div`
    display: grid;
    grid-template-columns: 1fr 1fr;
    align-items: left;
    grid-template-areas: ${({imgStart}) => (imgStart ? `'col2 col1'` : `'col1 col2'`)};
    margin-top: 50px;
    @media screen and (max-width: 1000px) {
        grid-template-columns: 75px 75px;
    }
`;

export const PurrfectedIcon = styled(FaPaw)`
    font-size: 50px;
    color: #F9C5D5;
`;

export const PurrfectedColumn1 = styled.div`
    margin-bottom: 15px;
    padding: 0 15px;
    grid-area: col1;
`;

export const PurrfectedColumn2 = styled.div`
    margin-top: 5px;
    padding: 0 15px;
    grid-area: col2;
`;

export const CommentsContainer = styled.div`
    color: #fff;
    background: #B38A9B;
    align-items: flex-start;
    width: %100;

    text-align: center;
    //padding: 50px 0;
    @media screen and (max-width: 768px) {
    }
`;

export const CommentsWrapper = styled.div`
    display: flex;
    align-items: center;
    margin-top: 50px;
    grid-template-columns: 1fr;
    width: %100;

    @media screen and (max-width: 1000px) {
        grid-template-rows: 1fr 1fr;
        grid-template-columns: 1fr;
    }
`;

export const CommentsColumn = styled.div`
    align-items: flex-start;
    margin-bottom: 15px;
    padding: 0 15px;
`;









//eğer yaparsak :,)
export const UserIcon = styled.img`
    height: 160px;
    width: 160px;
    margin-bottom: 10px;
`;