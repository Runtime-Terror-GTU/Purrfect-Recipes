import { ref, set, get, query, orderByChild, equalTo } from "firebase/database";
import { database } from "./firebase";
/** 
 * Read Recipes' data from firebase
 * & write to firebase (soon)
*/

const getRecipes = async () => {



    //class var class'a yazmak olabilir belki
    console.log("nasiyani3");  

    let recipes = await findRecipes();
    var countRecipes = Object.keys(recipes).length;
    let recipesObjects = Object.keys(recipes);
    var recipesArray = [];
    for(let i=0; i<countRecipes; i++){
        recipesArray[i] = {};
        recipesArray[i].recipeName = recipes[recipesObjects[i]].R_RecipeName;
        //console.log( recipes[recipesObjects[i]].R_RecipeName );
    }
    //console.log(recipesArray);
    console.log("nasiyani2");  

    return recipesArray;
}

const findRecipes = async () => {
    var recipes = await get(query(ref(database, "Recipes")))
    console.log("nasiyani");  

    return recipes.val();
}

export {getRecipes};