import './Recipe';
import { ref, set, get, query, orderByKey, equalTo, update, orderByChild, remove } from "firebase/database";
import { getDownloadURL, getStorage, ref as sRef, uploadBytes, deleteObject } from "firebase/storage";
import { database } from "./firebase";
import { v4 as uuidv4 } from 'uuid';
import { findUser } from './UserService';


const getRecipes = async () => {
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
        recipesArray[i].R_RecipeOwnerStatus = recipeOwner[recipesArray[i].R_RecipeOwnerID].R_User_Status;
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
//Add Moderator
const addModerator = async (moderator) => {
    console.log(moderator);
    let users = await findUser(moderator);
    if(users === null){
        set(ref(database, "Users/" + moderator.id),{
            R_Username : moderator.username,
            R_UserEmail : moderator.email,
            R_UserPassword : moderator.password,
            R_User_Status: "MODERATOR"
        })
        return true;
    }
    return false;
}
//Read Ingredients From firebase
const getIngredients = async () => {

    var ingredients = await get(query(ref(database, "Ingredients")));
    
    return ingredients.val();

    
}
//Delete Moderators
const removeMod = async (moderator) => {
    remove(ref(database,"Users/"+moderator.ModID));
}

//Return Moderators
const getModerators = async () =>{
        let userStatus = "MODERATOR";
        let moderators = await findModerator(userStatus);
        var countModerator = Object.keys(moderators).length;
   
        let moderatorObjects = Object.keys(moderators);
        var moderatorArray = [];
        for(let i=0; i<countModerator; i++){
            moderatorArray[i] = {};
            moderatorArray[i].ModID = moderatorObjects[i];
            moderatorArray[i].R_UserEmail = moderators[moderatorObjects[i]].R_UserEmail;    
            moderatorArray[i].R_UserPassword=  moderators[moderatorObjects[i]].R_UserPassword;
            moderatorArray[i].R_User_Status=  moderators[moderatorObjects[i]].R_User_Status;
            moderatorArray[i].R_Username= moderators[moderatorObjects[i]].R_Username;
        }

        return moderatorArray;

}
//Find Moderators
const findModerator = async (userStatus) => {
    let search = await get(query(ref(database, "Users"), orderByChild("R_User_Status"), equalTo(userStatus)));
    return search.val();
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

//updates recipe's info
const updateRecipe = async(recipeID, updatedRecipe) => {
    let picURL = updatedRecipe.R_RecipePicture;
    //update picture
    if(updatedRecipe.PictureFlag){
        // Create a root reference
        const storage = getStorage();
        // Create a reference to 'Recipe Picture/recipeID.jpg'
        var path = "Recipe Pictures/" + recipeID + '';
        const recipePicRef = sRef(storage, path);
        //console.log(recipePicRef)
        await uploadBytes(recipePicRef, updatedRecipe.R_RecipePicture).then((snapshot) => {
            console.log('Uploaded a blob or file!');
        });
        picURL = await getDownloadURL(recipePicRef)
    }
    let stepsPreparation;
    if(typeof updatedRecipe.R_RecipePreparation ==  "string"){
        let arraysPrepation = updatedRecipe.R_RecipePreparation.split('\n');
        let index = 1;
        stepsPreparation = [];
        stepsPreparation[index-1] = null;
        for(let i=0; i<arraysPrepation.length; i++){
            if( arraysPrepation[i].length > 0 ){
                stepsPreparation[index] = arraysPrepation[i]
                index++;
            } 
        }
    } else{
        stepsPreparation = updatedRecipe.R_RecipePreparation;
    }
    update(ref(database, "Recipes/" + recipeID), {
        R_RecipeName: updatedRecipe.R_RecipeName,
        R_RecipePicture: picURL,
        R_RecipeDifficulty: updatedRecipe.R_RecipeDifficulty,
        R_Recipe_Tags: updatedRecipe.R_Recipe_Tags,
        R_RecipeIngredients: updatedRecipe.R_RecipeIngredients,
        R_RecipeIngredientsOverview: updatedRecipe.R_RecipeIngredientsOverview.replaceAll("\n","\\n"),
        R_RecipePreparation: stepsPreparation
    });
}

//Call the Recipe from firebase database (according to Recipe ID)
const findRecipebyID = async (recipeID) => {
    //console.log(recipeID)
    //console.log(typeof  recipeID)

    var search = await get(query(ref(database, "Recipes"), orderByKey(), equalTo(recipeID.toString())));
    recipeID = Object.keys(search.val());
    let recipeObj = search.val()[recipeID];
    var recipe = {}

    recipe.R_RecipeName = recipeObj.R_RecipeName;
    recipe.RecipeID = recipeID;
    recipe.R_RecipeOwnerID = recipeObj.R_RecipeOwner;

    //finds the recipe owner with use R_RecipeOwnerID
    let recipeOwner = await findRecipeOwner(recipe.R_RecipeOwnerID);
    //finds the recipe owner's username and put the array
    recipe.R_RecipeOwner = recipeOwner[recipe.R_RecipeOwnerID].R_Username;
    recipe.R_RecipeOwnerStatus = recipeOwner[recipe.R_RecipeOwnerID].R_User_Status;
    recipe.R_RecipePicture = recipeObj.R_RecipePicture;
    recipe.R_RecipePurrfectedCount = recipeObj.R_RecipePurrfectedCount;
    recipe.R_RecipeIngredients = recipeObj.R_RecipeIngredients;
    recipe.R_RecipeIngredientsOverview = recipeObj.R_RecipeIngredientsOverview;
    recipe.R_RecipeDifficulty = recipeObj.R_RecipeDifficulty;
    recipe.R_Recipe_Tags = recipeObj.R_Recipe_Tags;
    recipe.R_RecipeComments = recipeObj.R_RecipeComments;
    recipe.R_RecipePreparation = recipeObj.R_RecipePreparation;
    return recipe;
}

//add recipe
const addRecipe = async(user, newRecipe) => {
    newRecipe.R_RecipeOwnerID = Object.keys(user);
    let picURL = newRecipe.R_RecipePicture;
    //update picture
    if(newRecipe.PictureFlag){
        // Create a root reference
        const storage = getStorage();
        // Create a reference to 'Recipe Picture/recipeID.jpg'
        var path = "Recipe Pictures/" + newRecipe.recipeID + '';
        const recipePicRef = sRef(storage, path);
        //console.log(recipePicRef)
        await uploadBytes(recipePicRef, newRecipe.R_RecipePicture).then((snapshot) => {
            console.log('Uploaded a blob or file!');
        });
        picURL = await getDownloadURL(recipePicRef)
    }
    /** */
    let stepsPreparation;
    let arraysPrepation = newRecipe.R_RecipePreparation.split('\n');
    let index = 1;
    stepsPreparation = [];
    stepsPreparation[index-1] = null;
    for(let i=0; i<arraysPrepation.length; i++){
        if( arraysPrepation[i].length > 0 ){
            stepsPreparation[index] = arraysPrepation[i]
            index++;
        } 
    }
    let recipeID = uuidv4();
    
    set(ref(database, "Recipes/" + recipeID), {
        R_RecipeName: newRecipe.R_RecipeName,
        R_RecipePicture: picURL,
        R_RecipeDifficulty: newRecipe.R_RecipeDifficulty,
        R_Recipe_Tags: newRecipe.R_Recipe_Tags,
        R_RecipeIngredients: newRecipe.R_RecipeIngredients,
        R_RecipeIngredientsOverview: newRecipe.R_RecipeIngredientsOverview.replaceAll("\n","\\n"),
        R_RecipePreparation: stepsPreparation,
        R_RecipeOwner: newRecipe.R_RecipeOwnerID.toString(),
        R_RecipePurrfectedCount: newRecipe.R_RecipePurrfectedCount
    });
      
    if( user[Object.keys(user)].R_AddedRecipes !== undefined ){
        user[Object.keys(user)].R_AddedRecipes[recipeID] = true;
        update(ref(database, "Users/" + Object.keys(user) + "/R_AddedRecipes/"),
                user[Object.keys(user)].R_AddedRecipes );
        //update firebase
    } else{
        //set
        set(ref(database, "Users/" + Object.keys(user) + "/R_AddedRecipes/"),
            {[recipeID]: true} );
    }
}

const purrfectedRecipe = async(user, recipe, purrfectFlag) => {
    /**
     * flag === false => zaten begenmisim o zaman begenmicem artık
     */

    /**
     * flag == true => //begenmemisim henuz.begenmem lazim
     */
    if( purrfectFlag ){
        //ben bunu henuz begenmedim
        //once count arttırıcam
        //ardından profilimdeki purrfectedrecipes'a eklicem
        let num = await get(query(ref(database, "Recipes/" + recipe.RecipeID + "/R_RecipePurrfectedCount")))
        await update(ref(database, "Recipes/" + recipe.RecipeID ), {
            "R_RecipePurrfectedCount": (parseInt(num.val())+1).toString()
        } );
        num = await get(query(ref(database, "Recipes/" + recipe.RecipeID + "/R_RecipePurrfectedCount")))
        //oldu hadi purrfected a ekleyelim
        let currentPurrfectedsTemp = await get(query(ref(database, "Users/" + Object.keys(user) + "/R_PurrfectedRecipes/")))
        let currentPurrfecteds = currentPurrfectedsTemp.val();
        let newPurrfecteds = {}
        for(let i=0; i<Object.keys(currentPurrfecteds).length; i++){
            let key = Object.keys(currentPurrfecteds)[i];
            let value = currentPurrfecteds[key];
            newPurrfecteds[key] = value;
        }
       newPurrfecteds[recipe.RecipeID] = true;
       await update(ref(database, "Users/" + Object.keys(user) + "/R_PurrfectedRecipes/"), newPurrfecteds);

    } else{
        let num = await get(query(ref(database, "Recipes/" + recipe.RecipeID + "/R_RecipePurrfectedCount")))
        await update(ref(database, "Recipes/" + recipe.RecipeID ), {
            "R_RecipePurrfectedCount": (parseInt(num.val())-1).toString()
        } );
        num = await get(query(ref(database, "Recipes/" + recipe.RecipeID + "/R_RecipePurrfectedCount")))
        //oldu hadi purrfected ten silelim
        let currentPurrfectedsTemp = await get(query(ref(database, "Users/" + Object.keys(user) + "/R_PurrfectedRecipes/")))
        let currentPurrfecteds = currentPurrfectedsTemp.val();
        let newPurrfecteds = {}
        for(let i=0; i<Object.keys(currentPurrfecteds).length; i++){
            if( recipe.RecipeID.toString() !== Object.keys(currentPurrfecteds)[i].toString() ){
                let key = Object.keys(currentPurrfecteds)[i];
                let value = currentPurrfecteds[key];
                newPurrfecteds[key] = value;
            }
        }
        await set(ref(database, "Users/" + Object.keys(user) + "/R_PurrfectedRecipes/"), newPurrfecteds);
    }
    let newrecipe = await findRecipebyID(recipe.RecipeID);
    return newrecipe;
}

const deleteRecipeFromFirebase = async(user, recipe) => {
    console.log(user)
    console.log(recipe)
    //delete pic from firebase (if it is not default pic)
    let defaultPicURL = "https://firebasestorage.googleapis.com/v0/b/purrfect-recipes.appspot.com/o/Recipe%20Pictures%2Fno_photo.jpg?alt=media&token=e4eff4ad-f601-47b9-9916-1d1cfb908552"
    if( recipe.R_RecipePicture !==  defaultPicURL ){
        const storage = getStorage();
        const desertRef = sRef(storage, recipe.R_RecipePicture);
        deleteObject(desertRef);
    }
    //delete recipe comments(if exists) from all comments
    if( recipe.R_RecipeComments !== null && recipe.R_RecipeComments !== undefined ){
        var searchAllComments = await get(query(ref(database, "Comments"), orderByKey()));
        var allComments = searchAllComments.val();
        var recipesComments = recipe.R_RecipeComments;
        var newComments = {};
        //console.log(Object.keys(allComments).length)
        for(let i=0; i<Object.keys(recipesComments).length; i++){
            for(let j=0; j<Object.keys(allComments).length; j++){
                let recipeComment = Object.keys(recipesComments)[i].toString();
                let currentComment = Object.keys(allComments)[j].toString();
                if( recipeComment !== currentComment ){
                    let key = Object.keys(allComments)[j];
                    let value = allComments[key];
                    newComments[key] = value;
                } 
            }
        }
        //console.log(Object.keys(newComments).length)
        await set(ref(database, "Comments/"), newComments);
    }
    //delete the recipe from user's added recipes
    let currentAddedsTemp = await get(query(ref(database, "Users/" + Object.keys(user) + "/R_AddedRecipes/")));
    let currentAddeds = currentAddedsTemp.val();
    let newAddeds = {}
    for(let i=0; i<Object.keys(currentAddeds).length; i++){
        if( recipe.RecipeID.toString() !== Object.keys(currentAddeds)[i].toString() ){
            let key = Object.keys(currentAddeds)[i];
            let value = currentAddeds[key];
            newAddeds[key] = value;
            console.log(key)
        }
    }
    await set(ref(database, "Users/" + Object.keys(user) + "/R_AddedRecipes/"), newAddeds);
    //delete the recipe from a user's purrfected recipes
    //tüm userların R_PurrfectedRecipes ref ini al ve guncelle mlsf ki aklıma geleen ilk yok bu
    let allUsersTemp = await get(query(ref(database, "Users/" )));
    let allUsers = allUsersTemp.val();
    for(let i=0; i<Object.keys(allUsers).length; i++){
        let userPurrfectedTemp = await get(query(ref(database, "Users/" + Object.keys(allUsers)[i] + "/R_PurrfectedRecipes/")));
        let userPurrfected = userPurrfectedTemp.val();
        let newPurrfecteds = {}
        let newPurrfectedsFlag = false;
        if( userPurrfected !== null && userPurrfected !== undefined ){
            for(let j=0; j<Object.keys(userPurrfected).length; j++){
                if( recipe.RecipeID.toString() !== Object.keys(userPurrfected)[j].toString() ){
                    let key = Object.keys(userPurrfected)[j];
                    let value = userPurrfected[key];
                    newPurrfecteds[key] = value;
                } else{
                    newPurrfectedsFlag = true;
                }
            }
            if( newPurrfectedsFlag ){
                //there is change so update
                await set(ref(database, "Users/" + Object.keys(allUsers)[i] + "/R_PurrfectedRecipes/"), newPurrfecteds);
            }
        }
    }
    console.log("Recipes/"+recipe.RecipeID.toString())
    remove(ref(database,"Recipes/"+recipe.RecipeID.toString()));
}

export {deleteRecipeFromFirebase,getRecipes,IngredientList,TagList,updateRecipe,findRecipebyID,addRecipe,getModerators,removeMod,addModerator,purrfectedRecipe,findRecipeOwner};

