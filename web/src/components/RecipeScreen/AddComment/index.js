import React from 'react';
import { Button, Comment, Form } from 'semantic-ui-react';

export const AddComment = () => {
    let stringCommentOwner = localStorage.getItem("currentUser");
    let stringRecipe = localStorage.getItem("currentRecipe");
    //alert("The Value Received is " + b);
    let recipe = JSON.parse(stringRecipe);
    let commentOwner = JSON.parse(stringCommentOwner);
    return (
        <Comment.Group>
            <Form reply>
            <Form.TextArea />
            <Button content='Add Purrfect Comment' labelPosition='left' icon='edit' primary />
            </Form>
        </Comment.Group>
    )
}

export default AddComment;