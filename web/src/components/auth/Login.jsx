import React from 'react'
import { Link } from 'react-router-dom';
import { Form, Segment, Button, Grid, Message } from "semantic-ui-react";
import styles from "./loginSignup.module.css";

const Login = () => {

    const handleSubmit = event => {
        event.preventDefault();
    }

    return (
        <Grid textAlign = "center" verticalAlign = "middle" className = { styles.container }>
            <Grid.Column style = {{ maxWidth: 450 }}>
                <h1 className = { styles.formHeader } > 
                    Welcome to  
                </h1>
                <h1 className = { styles.formHeader } > 
                    <span> Purr</span>
                    fect Recipes 
                </h1>
                <Form
                size = "large"
                className = {styles.form}
                onSubmit = {handleSubmit}
                >
                    <Segment>
                        <Form.Input
                        fluid
                        icon = "mail"
                        iconPosition = "left"
                        name = "email"
                        placeholder = "Enter an e-mail address"
                        type = "email"
                        />
                        <Form.Input
                        fluid
                        icon = "lock"
                        iconPosition = "left"
                        name = "password"
                        placeholder = "Enter a password"
                        type = "password"
                        />
                        <Message class="psw">Forgot <a href="#">password?</a></Message>
                        <Button 
                        color = "blue"
                        fluid
                        size = "large"
                        >
                        Let's cook!
                        </Button>  
                    </Segment>
                </Form>
                <Message>
                    Don't you have an account? <Link to = "/signup"> Create a Purrfect account! </Link>
                </Message>
                <Message>
                    <Link to = "/guest"> Continue as a Purrfect guest </Link>
                </Message>
            </Grid.Column>  
        </Grid>
    )
}

export default Login
