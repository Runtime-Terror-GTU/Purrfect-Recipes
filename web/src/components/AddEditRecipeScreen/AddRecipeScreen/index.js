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
import Select from 'react-select';
import { addRecipe } from '../../../backend/RecipeValueListener';

let recipe = [];

export default class EditRecipeScreen extends React.Component {
    constructor(props) {
        super(props);        
        let allTagsArray = JSON.parse(localStorage.getItem("allTags"));
        let allTags = []
        for(let i=0; i<allTagsArray.length; i++){
            allTags[i] = {};
            allTags[i].value = allTagsArray[i].tagName;
            allTags[i].label = allTagsArray[i].tagName;
        }
               
        let allIngredientsArray = JSON.parse(localStorage.getItem("allIngredients"));
        let allIngredients = []
        for(let i=0; i<allIngredientsArray.length; i++){
            allIngredients[i] = {};
            allIngredients[i].value = allIngredientsArray[i].ingredientName;
            allIngredients[i].label = allIngredientsArray[i].ingredientName;
        }

        this.state = {
            picture: false,
            src: "https://firebasestorage.googleapis.com/v0/b/purrfect-recipes.appspot.com/o/Recipe%20Pictures%2Fno_photo.jpg?alt=media&token=e4eff4ad-f601-47b9-9916-1d1cfb908552",
            allIngredients: allIngredients, 
            allTags: allTags,
            nameError:"",
            tagError:"",
            ingrError:"",
            ingrDetailError:"",
            prepError:""
        }
        recipe.PictureFlag = false;
        recipe.R_RecipeDifficulty = "Easy";
    }

    handlePictureSelected(event) {
        var picture = event.target.files[0];
        var src     = URL.createObjectURL(picture);
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

        if( this.state.recipeName === undefined ||
            this.state.recipeName.length === 0 ){
            nameError = "Cannot be empty"
        }

        if( this.state.valuesTags === undefined ||
            Object.keys(this.state.valuesTags).length === 0 ){
            tagError = "Cannot be empty"
        }

        if( this.state.valuesIngredients === undefined ||
            Object.keys( this.state.valuesIngredients).length === 0 ){
            ingrError = "Cannot be empty"
        }

        if( this.state.ingredientsOverview === undefined ||
            this.state.ingredientsOverview.length === 0 ){
            ingrDetailError = "Cannot be empty"
        }

        if( this.state.recipePreparation === undefined ||
            this.state.recipePreparation.length === 0 ){
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
            if( this.state.picture !== false ){
                recipe.R_RecipePicture = this.state.picture;
                recipe.PictureFlag = true;
            } else{
                recipe.R_RecipePicture = this.state.src;
            }
            recipe.R_RecipePurrfectedCount = 0;
            (async function() {
                try {
                    let stringUser = localStorage.getItem("currentUser");
                    let user = JSON.parse(stringUser);
                    await addRecipe(user, recipe)
                    //window.location.href="/mainpage";
                } catch (e) {   
                    console.error(e);   
                }  
            })();
        } else{
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
                            checked='Easy'/> Easy 
                            <input type="radio" value="Medium" name="difficulty" 
                            onChange={this.handleRecipeDifficulty} 
                            /> Medium
                            <input type="radio" value="Hard" name="difficulty"
                            onChange={this.handleRecipeDifficulty} 
                            /> Hard                          
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
                            rows="5" 
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
                            rows="5" 
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
                    <CancelButton to='/mainpage'>
                        Cancel
                    </CancelButton>
                    <UploadButton onClick={this.upload.bind(this)} >
                        Add Purrfect Recipe
                    </UploadButton>
                </ButtonContainer>

                    
            </AddEditContainer>
        );
    }
}