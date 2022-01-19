import { ref, get, set, update, query, orderByKey, equalTo, remove } from "firebase/database";
import { database } from "./firebase";
import { v4 as uuidv4 } from 'uuid';
import { findRecipebyID } from "./RecipeValueListener";

const getComment = async(commentID) => {
    let commentFromFirebase = await findComment(commentID);
    if( commentID !== null && commentID !== undefined){
        let userFromFirebase = await findRecipeOwner(commentFromFirebase[commentID]["Comment Owner"]);

        let comment = [];
        comment = {
                CommentID: commentID,
                CommentContent: commentFromFirebase[commentID]["Comment Content"],
                CommentOwnerID: commentFromFirebase[commentID]["Comment Owner"],
                CommentOwnerUsername: userFromFirebase[commentFromFirebase[commentID]["Comment Owner"]].R_Username,
                CommentOwnerPic: userFromFirebase[commentFromFirebase[commentID]["Comment Owner"]].R_UserPicture,
                CommentOwnerStatus: userFromFirebase[commentFromFirebase[commentID]["Comment Owner"]].R_User_Status
            }
        return comment;
    }
}

const findComment = async(commentID) => {
    var comment = await get(query(ref(database, "Comments"), orderByKey(), equalTo(commentID)));
    return comment.val();
}

//finds the recipe owner with use R_RecipeOwnerID
const findRecipeOwner = async(userID) => {
    var search = await get(query(ref(database, "Users"), orderByKey(), equalTo(userID)));
    return search.val();
}

const addComment = async(comment, userID, recipeID) => {
    //comment içinde (commentid) comment content + comment owner
    let commentID = uuidv4();
    set(ref(database, "Comments/" + commentID), {
        "Comment Content": comment,
        "Comment Owner": userID[0]
    } );
    //recipe--> R_RecipeComments --> commentid
    let recipe = await findRecipebyID(recipeID);
    if( recipe.R_RecipeComments !== undefined ){
        recipe.R_RecipeComments[commentID] = true;
        update(ref(database, "Recipes/" + recipeID + "/R_RecipeComments/"),
                {[commentID]: true} );
        //update firebase
    } else{
        //set
        set(ref(database, "Recipes/" + recipeID + "/R_RecipeComments/"),
                {[commentID]: true} );
    }
    recipe = await findRecipebyID(recipeID);
    return recipe;
}

const deleteComment = async(comment, userID, recipeID) => {
    console.log("deletecomment")
    console.log(comment)
    console.log("Comments/"+comment.CommentID)
    remove(ref(database,"Comments/"+comment.CommentID));
    remove(ref(database,"Recipes/"+recipeID+"/R_RecipeComments/"+comment.CommentID));
    //comment içinde (commentid) comment content + comment owner
    /*
    let commentID = uuidv4();
    set(ref(database, "Comments/" + commentID), {
        "Comment Content": comment,
        "Comment Owner": userID[0]
    } );
    //recipe--> R_RecipeComments --> commentid
    let recipe = await findRecipebyID(recipeID);
    if( recipe.R_RecipeComments !== undefined ){
        recipe.R_RecipeComments[commentID] = true;
        update(ref(database, "Recipes/" + recipeID + "/R_RecipeComments/"),
                {[commentID]: true} );
        //update firebase
    } else{
        //set
        set(ref(database, "Recipes/" + recipeID + "/R_RecipeComments/"),
                {[commentID]: true} );
    }
    */
    let recipe = await findRecipebyID(recipeID);
    return recipe;
}

export {getComment, addComment, deleteComment};