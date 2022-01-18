import React, { useState, useEffect } from 'react';
import { addComment } from '../../../backend/CommentService';
import {
    TextAreaBox, 
    UploadButton,
    AddCommentContainer
} from '../AddComment/AddCommentElements';

let comment = "";

export default class AddComment extends React.Component {
    constructor(props) {
        super(props);        
        this.state = {
            comment: "",
            commentError: ""
        }
        this.handleComment = this.handleComment.bind(this);
    }

    handleComment(event) {
        this.setState({
            comment: event.target.value
        });
        comment = event.target.value;
    }

    validate = () => {
        let commentError = "";
        if( this.state.comment === undefined ||
            this.state.comment.length === 0 ){
            commentError = "Cannot be empty"
        }
        if( commentError ){
            this.setState({ commentError });
            return false;
        }
        return true;
    }

    upload() {
        const isValid = this.validate();
        if( isValid ){
            (async function() {
                try {
                    console.log(comment)
                    let user = JSON.parse(localStorage.getItem("currentUser"));
                    let recipe = JSON.parse(localStorage.getItem("currentRecipe"));
                    let newRecipe = await addComment(comment, Object.keys(user), (recipe.RecipeID));
                    localStorage.setItem("currentRecipe", JSON.stringify(newRecipe))
                    console.log(newRecipe)
                    window.location.href="/recipe";
                } catch (e) {   
                    console.error(e);   
                }  
            })();
        } else{
            this.state.commentError = ""
        }
    }

    render() {  
        return(
        <AddCommentContainer>               
        <form onSubmit={this.handleSubmit} className='form'>
            <TextAreaBox type='text'
            onChange={this.handleComment}
            rows="5" 
            />
            <div
            style={{ fontSize: 16, color: "pink" }}
            >
                {this.state.commentError}
            </div>
            <br />
        </form>
        <UploadButton onClick={this.upload.bind(this)}>
            Add Purrfect Comment
        </UploadButton>
        </AddCommentContainer>
        );
    }
}