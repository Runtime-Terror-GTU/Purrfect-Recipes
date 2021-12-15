import {ref, set, get, query, } from "firebase/database"
import {database} from "./firebase"


const signUp = (user)=> {
    //TODO: call hasUserSignUpAlready and return null go on
    set(ref(database, "Users/" + user.id), {
        R_UserPassword: user.password,
        R_UserEmail: user.email,
        R_Username: user.username
    })
}


const signIn = async (user)=> {
    hasUserSignUpAlready(user)
}

const hasUserSignUpAlready = async (user) => {
    var search = await get(query(ref(database, "Users")))
    //TODO: filter user.username or email
    console.log(search.val())
}

export {signIn, signUp};