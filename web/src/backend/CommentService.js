import { ref, get, query, orderByKey, equalTo } from "firebase/database";
import { database } from "./firebase";

const getComment = async (commentID) => {
    let commentFromFirebase = await findComment(commentID);
    let userFromFirebase = await findRecipeOwner(commentFromFirebase[commentID]["Comment Owner"]);

    let comment = [];
    comment = {
                CommentContent: commentFromFirebase[commentID]["Comment Content"],
                CommentOwnerID: commentFromFirebase[commentID]["Comment Owner"],
                CommentOwnerUsername: userFromFirebase[commentFromFirebase[commentID]["Comment Owner"]].R_Username,
                CommentOwnerPic: userFromFirebase[commentFromFirebase[commentID]["Comment Owner"]].R_UserPicture,
                CommentOwnerStatus: userFromFirebase[commentFromFirebase[commentID]["Comment Owner"]].R_User_Status
            }
    return comment;
}

const findComment = async (commentID) => {
    var comment = await get(query(ref(database, "Comments"), orderByKey(), equalTo(commentID)));
    return comment.val();
}

//finds the recipe owner with use R_RecipeOwnerID
const findRecipeOwner = async (userID) => {
    var search = await get(query(ref(database, "Users"), orderByKey(), equalTo(userID)));
    return search.val();
}
export {getComment};