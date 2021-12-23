import React from 'react'
import {FormInput,FormWrap,FormContent,Form} from './searchbarElements';

const SearchBar = () => {
    return (
        <>
            
                <FormWrap>
                    <FormContent>
                        <Form action='#'>
                            
                            <FormInput id='searchinput' type="Search..." placeholder="Search..." required/>
                            
                            
                        </Form>
                    </FormContent>
                </FormWrap>
            
        </>
    )
}
export default SearchBar;