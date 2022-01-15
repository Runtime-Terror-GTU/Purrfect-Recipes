import React, { useState, useEffect } from 'react';
import $ from 'jquery';
import {
    AddEditContainer,
    ButtonContainer,
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


let stringRecipe = localStorage.getItem("currentRecipe");
let recipe = JSON.parse(stringRecipe);

export default class EditRecipeScreen extends React.Component {
    constructor(props) {
        super(props);
        //console.log(recipe)
        stringRecipe = localStorage.getItem("currentRecipe");
        recipe = JSON.parse(stringRecipe);
        this.state = {
            picture: false,
            src: recipe.R_RecipePicture
        }
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

    upload() {
        var formData = new FormData();
        formData.append("file", this.state.picture);
        $.ajax({
            url: "/some/api/endpoint",
            method: "POST",
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            success: function(response) {
                // Code to handle a succesful upload
            }
        });
    }



    handleChange = (e, { value }) => this.setState({ value })

    render() {
        const { value } = this.state
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
        /*
        ingre'e eklediğim şeyi details a eklemeyebilirim??
        */
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



        let ingredientsPreparation = ""
        recipe.R_RecipePreparation.map((preparation, i) => {
            if( preparation != null ){
                ingredientsPreparation += (preparation.toString() + "\n")
            }
        })


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
                            <TextBox type='text' defaultValue={recipe.R_RecipeName} required/>

                            </ColumnWrapper2>
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
                            checked={recipe.R_RecipeDifficulty === 'Easy'}/> Easy 
                            <input type="radio" value="Medium" name="difficulty" 
                            checked={recipe.R_RecipeDifficulty === 'Medium'}/> Medium
                            <input type="radio" value="Hard" name="difficulty" 
                            checked={recipe.R_RecipeDifficulty === 'Hard'}/> Hard                          
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
                            defaultValue={valuesTags}
                            isMulti
                            name="Tags"
                            options={allTags}
                            className="basic-multi-select"
                            classNamePrefix="select"
                            />
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
                            defaultValue={valuesIngredients}
                            isMulti
                            name="Tags"
                            options={allIngredients}
                            className="basic-multi-select"
                            classNamePrefix="select"
                            />
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
                            <TextAreaBox type='text' defaultValue={ingredientsOverview} rows="5" />
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
                            <TextAreaBox type='text' defaultValue={ingredientsPreparation} rows="5" />
                            </ColumnWrapper2>
                        </RowWrapper> 
                    </div>
                    <br />
                    <hr />
                </form>
                
                <ButtonContainer>
                    <UploadButton>
                        Cancel
                    </UploadButton>
                    <UploadButton>
                        Update Recipe
                    </UploadButton>
                </ButtonContainer>

                    
            </AddEditContainer>
        );
    }
}