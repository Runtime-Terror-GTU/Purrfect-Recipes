import { ref, set, get, query, orderByKey, equalTo,remove } from "firebase/database";
import { database } from "./firebase";
//finds recipe of the day and returns
const getRecipeOfTheDay = async () => {
    //find current date
    var todayObject = new Date();
    var dd = String(todayObject.getDate()).padStart(2, '0');
    var mm = String(todayObject.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = todayObject.getFullYear();
    var today = dd + ' ' + mm + ' ' + yyyy;
    //finds the Recipe of The Date object that holds recipe id and date
    let data = await findRecipeOfTheDay(today)
    let recipeOfTheDayObject = [];
    let recipeID;
    if( data !== null ){ //already exists in firebase 
        //get the recipe's id
        recipeID = data[today];
    } else{//there is no recipe of the day at firebase so we'll choose it
        //and we'll delete last recipe of the day because it is useless anymore
        //delete the recipe of yesterday from firebase
        remove(ref(database,"Recipe of The Day/"));
        //create new recipe of the day and write it to firebase
        //get all recipes and create objects
        let recipes = await findRecipes();
        let recipesObjects = Object.keys(recipes);
        //get num of recipes
        let numberOfRecipes = recipesObjects.length;
        //create random integer
        //returns a random integer from 0 to numberOfRecipes:
        let index = Math.floor(Math.random() * numberOfRecipes);
        //find this random index's ID
        recipeID = recipesObjects[index];
        //create an object to send to firebase
        let jsonObject = {
            [today]: recipeID
        }
        //send it to firebase
        set(ref(database, "Recipe of The Day/"), jsonObject)
    }
    //finds the recipe with the id
    let recipeOfTheDay = await findRecipebyID(recipeID);
    //finds the recipe owner
    let recipeOwnerID = recipeOfTheDay[recipeID].R_RecipeOwner;
    let recipeOwner = await findRecipeOwner(recipeOfTheDay[recipeID].R_RecipeOwner);
    //create recipe object
    recipeOfTheDayObject = {R_RecipeName: recipeOfTheDay[recipeID].R_RecipeName,
                            RecipeID: recipeID,
                            R_RecipeOwnerID: recipeOfTheDay[recipeID].R_RecipeOwner,
                            R_RecipeOwner: recipeOwner[recipeOwnerID].R_Username,
                            R_RecipeOwnerStatus: recipeOwner[recipeOwnerID].R_User_Status,
                            R_RecipePicture: recipeOfTheDay[recipeID].R_RecipePicture,
                            R_RecipePurrfectedCount: recipeOfTheDay[recipeID].R_RecipePurrfectedCount,
                            R_RecipeIngredients: recipeOfTheDay[recipeID].R_RecipeIngredients,
                            R_RecipeIngredientsOverview: recipeOfTheDay[recipeID].R_RecipeIngredientsOverview,
                            R_RecipeDifficulty: recipeOfTheDay[recipeID].R_RecipeDifficulty,
                            R_Recipe_Tags: recipeOfTheDay[recipeID].R_Recipe_Tags,
                            R_RecipeComments:  recipeOfTheDay[recipeID].R_RecipeComments,
                            R_RecipePreparation:  recipeOfTheDay[recipeID].R_RecipePreparation
                        };
                        
    //returns the recipe
    //console.log("recipeofthedayid: ", recipeID);
    return recipeOfTheDayObject;
}
//Call all Recipes from firebase database
const findRecipes = async () => {
    var recipes = await get(query(ref(database, "Recipes")))
    return recipes.val();
}
//Call the User from firebase database (according to USER ID)
const findRecipeOwner = async (userID) => {
    var search = await get(query(ref(database, "Users"), orderByKey(), equalTo(userID)));
    return search.val();
}
//Call the Recipe of The Day from firebase database (according to today's date)
const findRecipeOfTheDay = async (today) => {
    var search = await get(query(ref(database, "Recipe of The Day"), orderByKey(), equalTo(today)));
    return search.val();
}
//Call the Recipe from firebase database (according to Recipe ID)
const findRecipebyID = async (recipeID) => {
    var search = await get(query(ref(database, "Recipes"), orderByKey(), equalTo(recipeID)));
    return search.val();
}

export {getRecipeOfTheDay};