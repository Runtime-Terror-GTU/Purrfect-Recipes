import React from 'react'
import {FormInput,FormWrap,FormContent,Form} from './searchbarElments';

const SearchBar = () => {
    return (
        <>
            
                <FormWrap>
                    <FormContent>
                        <Form action='#'>
                            
                            <FormInput id='passwordInput' type="Search..." placeholder="Search..." required/>
                            
                            
                        </Form>
                    </FormContent>
                </FormWrap>
            
        </>
    )
}
export default SearchBar;