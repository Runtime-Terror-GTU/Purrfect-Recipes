import React, { useState, useEffect } from 'react';
import { Button, Comment, CommentContent, Form } from 'semantic-ui-react';
import { getComment } from '../../../backend/CommentService';
import { 
    CommentContainer, 
    Img, 
    TextContainer, 
    CommentContents, 
    ImgContainer 
} from './CommentBoxElements';

export const CommentBox = (commentID) => {
    {console.log(commentID.commentID)}
    const [commentInfo, setCommentInfo] = useState([]);

    const fetchComment = async() => {
        const data = await getComment(commentID.commentID);
        return data;
    }
    
    useEffect(() => {
        (async function() {
            try {
                const data = await fetchComment();
                setCommentInfo(data);                
            } catch (e) {
                console.error(e);
            }
        })();
    }, []);

    return (
        <>
        <CommentContainer>
            <ImgContainer>
                <Img src={commentInfo.CommentOwnerPic} />
                <h1>{commentInfo.CommentOwnerUsername}</h1>
            </ImgContainer>
            <TextContainer>
                <CommentContents>{commentInfo.CommentContent}</CommentContents>
            </TextContainer>
        </CommentContainer>

        </>
    )
}

export default CommentBox;