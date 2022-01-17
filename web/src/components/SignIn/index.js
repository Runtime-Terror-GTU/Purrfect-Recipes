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
    Text,
    Button 
} from './SignInElements';
import { signIn } from "../../backend/UserService"

const onSubmit = async (e) => {
    //TODO: show warning
    //sanırım toast deniyormus
    //KOD SORUN CIKARIRSA BUTTON TYPEINI SUBMIT YERİNE BUTTON YAP 

    if( document.getElementById("passwordInput").value !== "" 
        && document.getElementById("userInput").value !== "" ){

        let user = {
            password: document.getElementById("passwordInput").value,
            username: document.getElementById("userInput").value
        }

        let userSignIn = await signIn(user, document.getElementById("passwordInput").value);
  
        if ( userSignIn !== null ){
            window.localStorage.clear();
            localStorage.setItem("currentUser", JSON.stringify(userSignIn)); //????
            if( userSignIn[Object.keys(userSignIn)].R_User_Status == "ADMIN" ){
                window.location.href = "/admin";
            } else if( userSignIn[Object.keys(userSignIn)].R_User_Status == "MODERATOR" ){
                window.location.href = "/moderator";
            }  else {
                window.location.href = "/mainpage";
            }
        } else{
            window.location.href = "/signin";
        }
    } else{
        console.log("login - something empty");
    }

}

const SignIn = () => {
    return (
        <>
            <Container>
                <FormWrap>
                    <FormContent>
                        <Form action='#'>
                            <FormH1 to='/'>Purrfect Recipes</FormH1>

                            <FormLabel htmlFor='for'>Username</FormLabel>
                            <FormInput id='userInput' type='username' placeholder="Username" required/>
                            <FormLabel htmlFor='for'>Password</FormLabel>
                            <FormInput id='passwordInput' type='password' placeholder="Password" required/>
                            
                            <Text to = "/forgot">
                                 Forgot your password?
                            </Text>
                            <FormButton type='button' onClick={onSubmit}>Let's cook!</FormButton>
                            <Button to = "/signup">
                                Don't you have an account? Create a Purrfect account! 
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

export default SignIn;