import styled from "styled-components";
import { Link } from "react-router-dom";

export const IngredientButtonsContainer = styled.div`
    display: grid;
    grid-gap: 0px;
    padding: 1px;
    flex-direction: left;
    align-items: top;
    grid-template-rows: repeat(1, 80px);
    grid-template-columns: repeat(2, 60px);

    //grid-gap: 16px;
    background: #B38A9B;
    @media screen and (max-width: 1000px) {
        grid-template-columns: 1fr 1fr;
    }

    @media screen and (max-width: 768px) {
        grid-template-columns: 1fr;
        padding: 0 20px;
    }
`;