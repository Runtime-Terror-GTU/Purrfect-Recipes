import { ref, get, query, orderByKey, equalTo } from "firebase/database";
import { database } from "./firebase";

//finds recipe of the day and returns
const getRecipeOfTheDay = async () => {
    //find current date
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = today.getFullYear();
    today = dd + ' ' + mm + ' ' + yyyy;
    //finds the Recipe of The Date object that holds recipe id and date
    let data = await findRecipeOfTheDay(today)
    //get the recipe's id
    let recipeID = data[today];
    //finds the recipe with the id
    let recipeOfTheDay = await findRecipebyID(recipeID);
    //finds the recipe owner
    let recipeOwnerID = recipeOfTheDay[recipeID].R_RecipeOwner;
    let recipeOwner = await findRecipeOwner(recipeOfTheDay[recipeID].R_RecipeOwner);
    //create recipe object
    let recipeOfTheDayObject = {R_RecipeName: recipeOfTheDay[recipeID].R_RecipeName, 
                                R_RecipeOwner: recipeOwner[recipeOwnerID].R_Username, 
                                R_RecipePicture: recipeOfTheDay[recipeID].R_RecipePicture};
    //returns the recipe
    return recipeOfTheDayObject;
}

const findRecipeOwner = async (userID) => {
    var search = await get(query(ref(database, "Users"), orderByKey(), equalTo(userID)));
    return search.val();
}

const findRecipeOfTheDay = async (today) => {
    var search = await get(query(ref(database, "Recipe of The Day"), orderByKey(), equalTo(today)));
    return search.val();
}

const findRecipebyID = async (recipeID) => {
    var search = await get(query(ref(database, "Recipes"), orderByKey(), equalTo(recipeID)));
    return search.val();
}

export {getRecipeOfTheDay};