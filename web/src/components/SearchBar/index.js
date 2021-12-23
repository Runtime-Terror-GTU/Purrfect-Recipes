import React from 'react'
import {FormInput,Container,FormWrap,FormContent,Form} from './searchbarElments';

const SearchBar = () => {
    return (
        <>
            <Container>
                <FormWrap>
                    <FormContent>
                        <Form action='#'>
                            
                            <FormInput id='passwordInput' type="Search..." placeholder="Search..." required/>
                            
                            
                        </Form>
                    </FormContent>
                </FormWrap>
            </Container>
        </>
    )
}
export default SearchBar;