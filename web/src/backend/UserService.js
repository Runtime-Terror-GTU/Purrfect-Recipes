import { ref, set, get, query, orderByChild, equalTo } from "firebase/database";
import { database } from "./firebase";


const signUp = async (user)=> {
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
}

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

export {signIn, signUp};