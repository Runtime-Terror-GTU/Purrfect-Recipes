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
} from './SignUpElements';

const SignUp= () => {
    return (
        <>
            <Container>
                <FormWrap>
                    <FormContent>
                        <Form action='#'>
                            <FormH1 to='/'>Purrfect Recipes</FormH1>
                            <FormLabel htmlFor='for'>Email</FormLabel>
                            <FormInput type='email' required/>
                            <FormLabel htmlFor='for'>Username</FormLabel>
                            <FormInput type='username' required/>
                            <FormLabel htmlFor='for'>Password</FormLabel>
                            <FormInput type='password' required/>
                            <Text to = "/forgot">
                                 Forgot your password?
                            </Text>
                            <FormButton type='submit' to='/'>Let's cook!</FormButton>
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