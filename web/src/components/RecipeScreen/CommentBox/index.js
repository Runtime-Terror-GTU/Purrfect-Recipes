import React, { useState, useEffect } from 'react';
import { getComment } from '../../../backend/CommentService';
import { 
    CommentContainer, 
    Img, 
    TextContainer, 
    CommentContents, 
    ImgContainer,
    PremiumImg 
} from './CommentBoxElements';
import PremiumIcon from '../../../images/premium_symbol.png';

export const CommentBox = (commentID) => {
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
                <h1>{commentInfo.CommentOwnerUsername}
                {(() => {
                if( commentInfo.CommentOwnerStatus === "PREMIUM" ){
                    return(
                        <PremiumImg src={PremiumIcon}/>         
                    )
                }
                })()}
                </h1>
            </ImgContainer>
            <TextContainer>
                <CommentContents>{commentInfo.CommentContent}</CommentContents>
            </TextContainer>
        </CommentContainer>
        </>
    )
}

export default CommentBox;