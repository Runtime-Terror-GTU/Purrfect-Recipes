import './Recipe';
import { ref, set, get, query, orderByKey, equalTo } from "firebase/database";
import { database } from "./firebase";
import { getRecipeOfTheDay } from './RecipeOfTheDayServices';

const getRecipes = async () => {
    //call recipe of the day method
    getRecipeOfTheDay();

    //hold recipes
    let recipes = await findRecipes();

    //hold a number of recipes
    var countRecipes = Object.keys(recipes).length;

    //hold recipes ids
    let recipesObjects = Object.keys(recipes);

    var recipesArray = [];

    //call recipe info from firebase and save in an array
    for(let i=0; i<countRecipes; i++){
        recipesArray[i] = {};
        recipesArray[i].R_RecipeName = recipes[recipesObjects[i]].R_RecipeName;
        recipesArray[i].RecipeID = recipesObjects[i];
        recipesArray[i].R_RecipeOwnerID = recipes[recipesObjects[i]].R_RecipeOwner;
        //finds the recipe owner with use R_RecipeOwnerID
        let recipeOwner = await findRecipeOwner(recipesArray[i].R_RecipeOwnerID);
        //finds the recipe owner's username and put the array
        recipesArray[i].R_RecipeOwner = recipeOwner[recipesArray[i].R_RecipeOwnerID].R_Username;
        recipesArray[i].R_RecipePicture= recipes[recipesObjects[i]].R_RecipePicture;
        recipesArray[i].R_RecipePurrfectedCount=recipes[recipesObjects[i]].R_RecipePurrfectedCount;
        recipesArray[i].R_RecipeIngredients=recipes[recipesObjects[i]].R_RecipeIngredients;
        recipesArray[i].R_RecipeDifficulty=recipes[recipesObjects[i]].R_RecipeDifficulty;
        recipesArray[i].R_Recipe_Tags=recipes[recipesObjects[i]].R_Recipe_Tags;
        //printtrecipeINfo(recipesArray[i]);
    }
    console.log(recipesArray)
    return recipesArray;
}

//test function for print recipe information
const printtrecipeINfo= async (data_) => {
    console.log( data_.RecipeID );
    console.log( data_.R_RecipeName );
    console.log( data_.R_RecipeOwner );
    console.log(data_.R_RecipePicture);
    console.log(data_.R_RecipePurrfectedCount);
    console.log(data_.R_RecipeIngredients);
    console.log(data_.R_RecipeDifficulty);
    console.log(data_.R_Recipe_Tags);
}

//Call all Recipes from firebase database
const findRecipes = async () => {
    var recipes = await get(query(ref(database, "Recipes")))
    return recipes.val();
}

//finds the recipe owner with use R_RecipeOwnerID
const findRecipeOwner = async (userID) => {
    var search = await get(query(ref(database, "Users"), orderByKey(), equalTo(userID)));
    return search.val();
}








export {getRecipes};