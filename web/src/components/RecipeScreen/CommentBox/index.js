import React, { useState, useEffect } from 'react';
import { deleteComment, getComment } from '../../../backend/CommentService';
import { 
    CommentContainer, 
    Img, 
    TextContainer, 
    CommentContents, 
    ImgContainer,
    PremiumImg,
    IconLink,
    DeleteIcon
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

    const handleDelete = () => {
        (async function() {
            try {
                let user = JSON.parse(localStorage.getItem("currentUser"));
                let recipe = JSON.parse(localStorage.getItem("currentRecipe"));
                let newRecipe = await deleteComment(commentInfo, Object.keys(user), (recipe.RecipeID));
                localStorage.setItem("currentRecipe", JSON.stringify(newRecipe))
                window.location.href="/recipe";
            } catch (e) {
                console.error(e);
            }
        })();
    }

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
            <h1>
                {(() => {
                    //console.log(commentInfo.CommentOwnerID)
                    let user = JSON.parse(localStorage.getItem("currentUser"));
                    //console.log(Object.keys(user).toString())

                if( commentInfo.CommentOwnerID === Object.keys(user).toString() ){
                    return(
                        <IconLink onClick={handleDelete} to='/recipe'>
                            <DeleteIcon /> 
                        </IconLink>                    
                    )
                }
                })()}
            </h1>
        </CommentContainer>
        </>
    )
}

export default CommentBox;