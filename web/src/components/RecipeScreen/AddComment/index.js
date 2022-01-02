import React from 'react';
import { Button, Comment, Form } from 'semantic-ui-react';

export const AddComment = (commentID) => {
    {console.log(commentID)}
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