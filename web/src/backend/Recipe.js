import './Comment';
import { ref, set, get, query, orderByChild, equalTo } from "firebase/database";
import { database } from "./firebase";

/* 
Recipe class implementation
*/
class Recipe{

    constructor(){
        //Hold Recipe' ID(sorr yeşime)
        this.RecipeID="";

        //Hold Recipe Name
        this.R_RecipeName="";

        //Hold Recipe Owner' ID
        this.R_RecipeOwner="";

        //hold comments
       // Comments = new Comment();

        //Hold Picture' firebase link(adresi işte)
        this.R_RecipePicture="";

        this.R_RecipePurrfectedCount="";

        //I do not know how hold this maybe we code a Ingredients class and check it
        this.R_RecipesIngredient="?";

        //Hold REcipe'  difficulty like -> Medium || Easy || Hard 
        this.R_RecipeDifficulty="";

        //Again I do not know how we hold this...
        this.R_Recipe_Tags="";
            
    }

    //??????????????
    //Return All Comments of this Recipe'
    getComments(){
        return this.comment;
    }

    getRecipeID(){
        return this.RecipeID;
    }

    getR_RecipeName(){
        return this.R_RecipeName;
    }

    getR_RecipeOwner(){
        return this.R_RecipeOwner;
    }

    getRecipePicture(){
        return this.R_RecipePicture;
    }

    getPurrfectedCounts(){
        return this.R_RecipePurrfectedCount;
    }

    //??????????????
    getRecipeIngredient(){
        return this.R_RecipesIngredient;
    }

    getRecipeDifficulty(){
        return this.R_RecipeDifficulty;
    }

    getRecipeTags(){
        return this.R_Recipe_Tags;
    }

     /*
        Call All Comments From Firebase and save in Comments object
        (İt will be an array or arraylist but now I do not know)
    */
    setComments(){
       /* I can not write now */
    }

    //Add a comment in Recipe and Update Firebase
    addComments(){
            //Kalbim kadar temiz :DDDDD:
    }

    //Add Tags in Recipe and Update Firebase
    addTags(){
            //Boooooooooooooooooooooooooooooossssssssssssssssssss
    }

    //Add Ingredient in Recipe and Update Firebase
    addIngredients(){
        //Boooooooooosssssssssss
    }

    //Remove a comment in Recipe and Update Firebase
    removeComment(commentID){
        //bbbbbbbbbbboooooooooooooooooosssssssssssssssss
    }

    //Remove a Tags in Recipe and Update Firebase
    removeTag(tag){
        //boooooooooooosssssssssssss
    }

    //Remove an Ingredient in Recipe and Update Firebase
    removeIngredient(Ingredient){
        //booooooooooooooooosssssssss
    }

    //Check Recipe Have This Tag If It Have Return True Else Return False
    isRecipeTag(Tag){
       // return noName ?true : false;
    }

    //Check Recipe Have This Ingredient If It Have Return True Else Return False
    isRecipesIngredients(Ingredient){
        //return noName ?true : false;
    }

    //Check Recipe Have This Comment If It Have Return True Else Return False
    isRecipesIngredients(commentID){
       // return noName ?true : false;
    }
}

