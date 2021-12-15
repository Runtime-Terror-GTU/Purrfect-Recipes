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
import { signUp, signIn } from "../../backend/UserService"
import { v4 as uuidv4 } from 'uuid';

const onSubmit = (e)=> {

    let user = {
        id: uuidv4(),
        password: document.getElementById("passwordInput").value,
        email: document.getElementById("emailInput").value,
        username: document.getElementById("userInput").value
    }
   signUp(user)
   signIn()
} 

const SignUp = () => {
    return (
        <>
            <Container>
                <FormWrap>
                    <FormContent>
                        <Form >
                            <FormH1 to='/'>Purrfect Recipes</FormH1>
                            <FormLabel htmlFor='for'>Email</FormLabel>
                            <FormInput  id='emailInput' type='email' placeholder="Email" required/>
                            <FormLabel htmlFor='for'>Username</FormLabel>
                            <FormInput id='userInput' type='username' placeholder="Username" required/>
                            <FormLabel htmlFor='for'>Password</FormLabel>
                            <FormInput id='passwordInput' type='password' placeholder="Password" required/>
                            
                            <FormButton type='submit' onClick={onSubmit}>Let's cook!</FormButton>
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