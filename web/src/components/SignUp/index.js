import React from 'react'
import { 
    Container, 
    FormButton, 
    FormContent, 
    FormH1, 
    FormInput, 
    FormLabel, 
    FormWrap,
    Form,
    Button 
} from './SignUpElements';
import { signUp } from "../../backend/UserService"
import { v4 as uuidv4 } from 'uuid';

const onSubmit = async (e) => {
    //TODO: show warning
    //sanırım toast deniyormus
    //KOD SORUN CIKARIRSA BUTTON TYPEINI SUBMIT YERİNE BUTTON YAP 

    if( document.getElementById("passwordInput").value !== "" 
        && document.getElementById("emailInput").value !== "" 
        && document.getElementById("userInput").value !== ""){
        
        let user = {
            id: uuidv4(),
            password: document.getElementById("passwordInput").value,
            email: document.getElementById("emailInput").value,
            username: document.getElementById("userInput").value
        }
        let userSignUp = await signUp(user)
        if ( userSignUp === true){
            window.localStorage.clear();
            localStorage.setItem("currentUser", JSON.stringify(user)); //????
            window.location.href = "/mainpage"
        } else{
            alert("Username must be unique");
            window.location.href = "/signup"
        }

    } else{
        alert("Fields cannot be empty");
        console.log("something empty");
    }

}

const SignUp = () => {
    return (
        <>
            <Container>
                <FormWrap>
                    <FormContent>
                        <Form action='#'>                            
                            <FormH1 to='/'>Purrfect Recipes</FormH1>
                            <FormLabel htmlFor='for'>Email</FormLabel>
                            <FormInput  id='emailInput' type='email' placeholder="Email" required/>
                            <FormLabel htmlFor='for'>Username</FormLabel>
                            <FormInput id='userInput' type='username' placeholder="Username" required/>
                            <FormLabel htmlFor='for'>Password</FormLabel>
                            <FormInput id='passwordInput' type='password' placeholder="Password" required/>
                            
                            <FormButton type='button' onClick={onSubmit}>Let's cook!</FormButton>
                            <Button to = "/signin">
                                Do you have already an account? 
                            </Button>
                            <Button to = "/guest">
                                Continue as a Purrfect guest
                            </Button>
                        </Form>
                    </FormContent>
                </FormWrap>
            </Container>
        </>
    )
}

export default SignUp;