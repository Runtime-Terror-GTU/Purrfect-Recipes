import styled from "styled-components";
import { FaPaw, FaEdit } from 'react-icons/fa';
import { MdDelete } from 'react-icons/md';
import { Link } from "react-router-dom";

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
    display: grid;
    color: #fff;
    background: #B38A9B;
    text-align: center;
    grid-template-rows: 1fr 10fr;

    @media screen and (max-width: 768px) {
    }
`;
//B38A9B pembe
//F9C5D5 pembe diğer
export const CommentsWrapper = styled.div`
    display: grid;
    align-items: center;
    width: %100;
    grid-template-rows: 250px 250px;

    @media screen and (max-width: 1000px) {
        grid-template-rows: 1fr 1fr;
        grid-template-columns: 1fr;
    }
`;

export const CommentsColumn = styled.div`
    align-items: center;
    padding: 0 250px;
`;

export const AddCommentColumn = styled.div`
    align-items: center;
`;

export const PremiumImg = styled.img`
    width: 50px;
    height: 40px;
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

export const IconWrapper = styled.div`
    display: grid;
    align-items: center;
    width: %100;
    margin-left: 60px;
    grid-template-rows: 1fr 1fr;

    @media screen and (max-width: 1000px) {
        grid-template-columns: 1fr 1fr;
    }
`;

export const DeleteIcon = styled(MdDelete)`
    font-size: 40px;
    color: #F9C5D5;
`;

export const EditIcon = styled(FaEdit)`
    font-size: 35px;
    color: #F9C5D5;

`;

export const IconLink = styled(Link)`
    background: #B38A9B;
    outline: none;
    border: none;
    cursor: pointer;
    transition: all 0.2s ease-in-out;
    text-decoration: none;
    &:hover {
        transform: scale(1.12);
        transition: all 0.2s ease-in-out;
        cursor: pointer;
    }
    
    //&:hover {
    //    transition: all 0.2s ease-in-out;
    //    background: #fff;
    //    color: #010606;
    //
    */
`;