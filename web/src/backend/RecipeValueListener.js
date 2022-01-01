import './Recipe';
import { ref, get, query, orderByKey, equalTo } from "firebase/database";
import { database } from "./firebase";
import { getRecipeOfTheDay } from './RecipeOfTheDayServices';

const getRecipes = async () => {
    //call recipe of the day method
    //getRecipeOfTheDay();

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
        recipesArray[i].R_RecipeIngredientsOverview=recipes[recipesObjects[i]].R_RecipeIngredientsOverview;
        /*
        console.log("cildirmamak")
        console.log(recipes[recipesObjects[i]].R_RecipeIngredientsOverview)
        console.log("elde")
        console.log(recipesArray[i].R_RecipeIngredientsOverview)
        console.log("degil")
        */
        recipesArray[i].R_RecipeDifficulty=recipes[recipesObjects[i]].R_RecipeDifficulty;
        recipesArray[i].R_Recipe_Tags=recipes[recipesObjects[i]].R_Recipe_Tags;
        recipesArray[i].R_RecipeComments=recipes[recipesObjects[i]].R_RecipeComments;
        recipesArray[i].R_RecipePreparation=recipes[recipesObjects[i]].R_RecipePreparation;

        //printtrecipeINfo(recipesArray[i]);
    }
    //console.log(recipesArray)
    return recipesArray;
}

//Return Ingredients
const IngredientList = async () => {

    let ingredients = await getIngredients();
    var countIngredients = Object.keys(ingredients).length;
   
    let ingredientsObjects = Object.keys(ingredients);
    

    var ingredientsArray = [];
    for(let i=0; i<countIngredients; i++){
        ingredientsArray[i] = {};
        ingredientsArray[i].ingredientName = ingredientsObjects[i];        
    }
    
    return ingredientsArray;

}

//Read Ingredients From firebase
const getIngredients = async () => {

    var ingredients = await get(query(ref(database, "Ingredients")));
    
    return ingredients.val();

    
}

//Return Tags
const TagList = async () => {
    let tags = await getTags();
    var countTags = Object.keys(tags).length;
   
    let tagsObjects = Object.keys(tags);
    

    var tagsArray = [];
    for(let i=0; i<countTags; i++){
        tagsArray[i] = {};
        tagsArray[i].tagName = tagsObjects[i];        
    }
    
    return tagsArray;
}

//Read Tags from Firebase
const getTags = async () => {
    var tags = await get(query(ref(database, "Tags")));
    
    return tags.val();
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

export {getRecipes,IngredientList,TagList};