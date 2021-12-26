import ReactDOM from "react-dom";
import styled from "styled-components";
//B38A9B pembe
//F9C5D5 pembe diğer
export const Form = styled.form`
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    background-color: #F9C5D5;
    /* Change width of the form depending if the bar is opened or not */
    width: ${props => (props.barOpened ? "15rem" : "1rem")};
    /* If bar opened, normal cursor on the whole form. If closed, show pointer on the whole form so user knows he can click to open it */
    cursor: ${props => (props.barOpened ? "auto" : "pointer")};
    padding: 2.25rem;
    height: 2em;
    border-radius: 1rem;
    transition: width 300ms cubic-bezier(0.645, 0.045, 0.355, 1);
`;

export const Input = styled.input`
    font-size: 14px;
    line-height: 1;
    background-color: transparent;
    width: 100%;
    margin-left: ${props => (props.barOpened ? "1rem" : "0rem")};
    border: none;
    color: grey;
    transition: margin 300ms cubic-bezier(0.645, 0.045, 0.355, 1);
    
    &:focus,
    &:active {
        outline: none;
    }
    &::placeholder {
        color: white;
    }
`;

export const Button = styled.button`
    line-height: 1;
    pointer-events: ${props => (props.barOpened ? "auto" : "none")};
    cursor: ${props => (props.barOpened ? "pointer" : "none")};
    background-color: transparent;
    border: none;
    outline: none;
    color: black;
    outline: none;
    cursor: pointer;
    box-sizing: inherit;
    
`;