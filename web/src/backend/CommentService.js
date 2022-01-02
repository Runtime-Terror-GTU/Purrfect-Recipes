import { ref, set, get, query, orderByKey, equalTo } from "firebase/database";
import { database } from "./firebase";

const getComment = async (commentID) => {
    let commentFromFirebase = await findComment(commentID);
    console.log("commentFromFirebase[commentID]")
    let userFromFirebase = await findRecipeOwner(commentFromFirebase[commentID]["Comment Owner"]);
    console.log(userFromFirebase)

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
    /*
    let users = await findUser(user);
    console.log(users);
    if(users === null) {
        set(ref(database, "Users/" + user.id), {
            R_UserBio: "Insert Bio Here",
            R_UserEmail: user.email,
            R_UserPassword: user.password,
            R_UserPicture: "Insert Picture URL here",
            R_User_Status: "UNVERIFIED",
            R_Username: user.username
        })
        return true;
    }
    return false;
    */
}
/*
const signIn = async (user, password) => {
    let fUser =  await findUser(user);
    let uuid = Object.keys(fUser); //read id
    let fPassword = fUser[uuid[0]].R_UserPassword; //read password
    if(user !== null && fPassword === password){
        return true;
    }
    return false;
}

const findUser = async (user) => {
    var search = await get(query(ref(database, "Users"), orderByChild("R_Username"), equalTo(user.username)))
    return search.val();
}
*/
//finds the recipe owner with use R_RecipeOwnerID
const findRecipeOwner = async (userID) => {
    var search = await get(query(ref(database, "Users"), orderByKey(), equalTo(userID)));
    return search.val();
}
export {getComment};