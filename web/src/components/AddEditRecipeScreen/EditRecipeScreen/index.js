import React, { useState, useEffect } from 'react';
import {
    AddEditContainer,
    ButtonContainer,
    CancelButton,
    ColumnWrapper1,
    ColumnWrapper2,
    Img, 
    PictureContainer, 
    RowWrapper, 
    Subtitle, 
    TextAreaBox, 
    TextBox, 
    TopLine, 
    UploadButton
} from '../AddEditRecipeElements';
import { Button } from '../../ButtonElements';
import Select from 'react-select';
import { updateRecipe, findRecipebyID } from '../../../backend/RecipeValueListener';

let stringRecipe;
let recipe;

export default class EditRecipeScreen extends React.Component {
    constructor(props) {
        super(props);
        //console.log(recipe)
        stringRecipe = localStorage.getItem("currentRecipe");
        recipe = JSON.parse(stringRecipe);
        let ingredientsOverview = ""
        recipe.R_RecipeIngredientsOverview.toString().split("\\n").map((ingredient, i) => (
            ingredientsOverview += (ingredient.toString() + "\n")
        ))
        let tags = [];
        for(let i=0; i<Object.keys(recipe.R_Recipe_Tags).length; i++){
            tags[i] = {};
            tags[i] = Object.keys(recipe.R_Recipe_Tags)[i]; 
        }
        let valuesTags = []
        let index = 0
        tags.map((tag, i) => {
            if( tag != null ){
                valuesTags[index] = {};
                valuesTags[index].value = tag;
                valuesTags[index].label = tag;
                index++;
            }
        }) 
        let allTagsArray = JSON.parse(localStorage.getItem("allTags"));
        let allTags = []
        for(let i=0; i<allTagsArray.length; i++){
            allTags[i] = {};
            allTags[i].value = allTagsArray[i].tagName;
            allTags[i].label = allTagsArray[i].tagName;
        }
        let recipeIngredients = [];
        for(let i=0; i<Object.keys(recipe.R_RecipeIngredients).length; i++){
            recipeIngredients[i] = {};
            recipeIngredients[i] = Object.keys(recipe.R_RecipeIngredients)[i]; 
        }
        let valuesIngredients = []
        index = 0
        recipeIngredients.map((ingredient, i) => {
            if( ingredient != null ){
                valuesIngredients[index] = {};
                valuesIngredients[index].value = ingredient;
                valuesIngredients[index].label = ingredient;
                index++;
            }
        })        
        let allIngredientsArray = JSON.parse(localStorage.getItem("allIngredients"));
        let allIngredients = []
        for(let i=0; i<allIngredientsArray.length; i++){
            allIngredients[i] = {};
            allIngredients[i].value = allIngredientsArray[i].ingredientName;
            allIngredients[i].label = allIngredientsArray[i].ingredientName;
        }
        let recipePreparation = ""
        recipe.R_RecipePreparation.map((preparation, i) => {
            if( preparation != null ){
                recipePreparation += (preparation.toString() + "\n")
            }
        })
        this.state = {
            picture: false,
            src: recipe.R_RecipePicture,
            recipeName: recipe.R_RecipeName,
            recipeDifficulty: recipe.R_RecipeDifficulty,
            valuesTags: valuesTags,
            valuesIngredients: valuesIngredients,
            allIngredients: allIngredients, 
            allTags: allTags,
            recipePreparation: recipePreparation,
            ingredientsOverview: ingredientsOverview,
            nameError:"",
            tagError:"",
            ingrError:"",
            ingrDetailError:"",
            prepError:""
        }
        recipe.PictureFlag = false;
        //console.log(this.state)
    }

    handlePictureSelected(event) {
        var picture = event.target.files[0];
        //console.log("src")
        var src     = URL.createObjectURL(picture);
        //console.log(src)
        this.setState({
            picture: picture,
            src: src
        });
    }

    renderPreview() {
        if(this.state.src) {
            return (
                <Img src={this.state.src} />
            );
        } else {
            return (
                <p>
                No Preview
                </p>
            );
        }
    }

    handleRecipeName = (event) => {
        this.state.recipeName = event.target.value;
        recipe.R_RecipeName = event.target.value;
    }

    handleRecipeDifficulty = (event) => {
        this.setState({
            recipeDifficulty: event.target.value
        })
        recipe.R_RecipeDifficulty = event.target.value;
        console.log(recipe)
    }

    handleRecipeIngredientDetails = (event) => {
        this.setState({
            ingredientsOverview: event.target.value
        })
        recipe.R_RecipeIngredientsOverview = event.target.value;
    }

    handleRecipePreparation = (event) => {
        this.setState({
            recipePreparation: event.target.value
        })
        recipe.R_RecipePreparation = event.target.value;
    }

    handleRecipeTags = (event) => {
        console.log("tag")
        console.log(event)

        let tags = {};
        for(let i=0; i<event.length; i++){
            tags[event[i].value] = true;
        }
        this.setState({
            valuesTags: tags
        })
        recipe.R_Recipe_Tags = tags;
    }

    handleRecipeIngredients = (event) => {
        console.log("ingrrr")
        console.log(event)
        let ingredients = {};
        for(let i=0; i<event.length; i++){
            ingredients[event[i].value] = true;
        }
        this.setState({
            valuesIngredients: ingredients
        })
        recipe.R_RecipeIngredients = ingredients;
    }

    validate = () => {
        let nameError = "";
        let tagError = "";
        let ingrError = "";
        let ingrDetailError = "";
        let prepError = "";

        if( this.state.recipeName.length === 0 ){
            nameError = "Cannot be empty"
        }

        if( Object.keys(this.state.valuesTags).length === 0 ){
            tagError = "Cannot be empty"
        }

        if( Object.keys( this.state.valuesIngredients).length === 0 ){
            ingrError = "Cannot be empty"
        }

        if( this.state.ingredientsOverview.length === 0 ){
            ingrDetailError = "Cannot be empty"
        }

        if( this.state.recipePreparation.length === 0 ){
            prepError = "Cannot be empty"
        }

        if( nameError || tagError || ingrError || ingrDetailError || prepError ){
            this.setState({ nameError, tagError, ingrError, ingrDetailError, prepError });
            return false;
        }

        return true;
    }

    upload() {
        const isValid = this.validate();
        if( isValid ){
            console.log("valid")
            if( this.state.picture !== false ){
                recipe.R_RecipePicture = this.state.picture;
                recipe.PictureFlag = true;
                //console.log("deneme")
            } 
            (async function() {
                try {
                    await updateRecipe(recipe.RecipeID, recipe)
                    let newRecipe = await findRecipebyID(recipe.RecipeID)
                    localStorage.setItem("currentRecipe", JSON.stringify(newRecipe))
                    window.location.href="/recipe";
                } catch (e) {   
                    console.error(e);   
                }  
            })();
        } else{
            console.log("invalid")
            this.state.nameError = "";
            this.state.tagError = "";
            this.state.ingrError = "";
            this.state.ingrDetailError = "";
            this.state.prepError = "";
        }
    }


    render() {  
        /*
        ingre'e eklediğim şeyi details a eklemeyebilirim??
        */
        return (
            <AddEditContainer>
                
                <hr />
                <br />
                <TopLine>Recipe Picture</TopLine>
                <br />
                <input
                type="file"
                onChange={this.handlePictureSelected.bind(this)}
                />
                <br />
                <br />
                <PictureContainer>
                {this.renderPreview()}
                </PictureContainer>
                <br />
                <hr />
                
                <form onSubmit={this.handleSubmit} className='form'>
                    <div>
                        <br />
                        <RowWrapper>
                            <ColumnWrapper1>
                            <TopLine>Recipe Name</TopLine>
                            </ColumnWrapper1>
                            <ColumnWrapper2>
                            <TextBox 
                            onChange={this.handleRecipeName}
                            type='text' 
                            defaultValue={this.state.recipeName} 
                            />
                            </ColumnWrapper2>
                            <div
                            style={{ fontSize: 16, color: "pink" }}
                            >
                                {this.state.nameError}
                            </div>
                        </RowWrapper>
                    </div>
                    <br />
                    <hr />
                    <div>
                        <br />
                        <RowWrapper>
                            <ColumnWrapper1>
                            <TopLine>Recipe Difficulty</TopLine>
                            </ColumnWrapper1>
                            <ColumnWrapper2>
                            <input type="radio" value="Easy" name="difficulty"
                            onChange={this.handleRecipeDifficulty} 
                            checked={this.state.recipeDifficulty === 'Easy'}/> Easy 
                            <input type="radio" value="Medium" name="difficulty" 
                            onChange={this.handleRecipeDifficulty} 
                            checked={this.state.recipeDifficulty === 'Medium'}/> Medium
                            <input type="radio" value="Hard" name="difficulty"
                            onChange={this.handleRecipeDifficulty} 
                            checked={this.state.recipeDifficulty === 'Hard'}/> Hard                          
                            </ColumnWrapper2>
                        </RowWrapper>
                    </div>
                    <br />
                    <hr />
                    <div>
                        <br />
                        <RowWrapper>
                            <ColumnWrapper1>
                            <TopLine>Recipe Tags</TopLine>
                            </ColumnWrapper1>
                            <ColumnWrapper2>
                            <Select
                            defaultValue={this.state.valuesTags}
                            isMulti
                            name="Tags"
                            options={this.state.allTags}
                            className="basic-multi-select"
                            classNamePrefix="select"
                            onChange={this.handleRecipeTags}
                            />
                            <div
                            style={{ fontSize: 16, color: "pink" }}
                            >
                                {this.state.tagError}
                            </div>
                            </ColumnWrapper2>
                        </RowWrapper>
                    </div>
                    <br />
                    <hr />
                    <div>
                        <br />
                        <RowWrapper>
                        <ColumnWrapper1>
                            <TopLine>Recipe Ingredients</TopLine>
                            </ColumnWrapper1>
                            <ColumnWrapper2>
                            <Select
                            defaultValue={this.state.valuesIngredients}
                            isMulti
                            name="Tags"
                            options={this.state.allIngredients}
                            className="basic-multi-select"
                            classNamePrefix="select"
                            onChange={this.handleRecipeIngredients}
                            />
                            <div
                            style={{ fontSize: 16, color: "pink" }}
                            >
                                {this.state.ingrError}
                            </div>
                            </ColumnWrapper2>
                        </RowWrapper>
                    </div>
                    <br />
                    <hr />
                    <div>
                        <br />
                        <Subtitle>Please add a new line between details</Subtitle>
                        <RowWrapper>
                            <ColumnWrapper1>
                            <TopLine>Recipe Ingredient Details</TopLine>
                            </ColumnWrapper1>
                            <ColumnWrapper2>
                            <TextAreaBox type='text' 
                            onChange={this.handleRecipeIngredientDetails}
                            defaultValue={this.state.ingredientsOverview} rows="5" 
                            />
                            <div
                            style={{ fontSize: 16, color: "pink" }}
                            >
                                {this.state.ingrDetailError}
                            </div>
                            </ColumnWrapper2>
                        </RowWrapper>                        
                    </div>
                    <br />
                    <hr />
                    <div>
                        <br />
                        <Subtitle>Please add a new line between steps</Subtitle>
                        <RowWrapper>
                            <ColumnWrapper1>
                            <TopLine>Recipe Preparation</TopLine>
                            </ColumnWrapper1>            
                            <ColumnWrapper2>
                            <TextAreaBox type='text'
                            onChange={this.handleRecipePreparation}
                            defaultValue={this.state.recipePreparation} rows="5" 
                            />
                            <div
                            style={{ fontSize: 16, color: "pink" }}
                            >
                                {this.state.prepError}
                            </div>
                            </ColumnWrapper2>
                        </RowWrapper> 
                    </div>
                    <br />
                    <hr />
                </form>
                
                <ButtonContainer>
                    <CancelButton to='/recipe'>
                        Cancel
                    </CancelButton>
                    <UploadButton onClick={this.upload.bind(this)} >
                        Update Recipe
                    </UploadButton>
                </ButtonContainer>

                    
            </AddEditContainer>
        );
    }
}