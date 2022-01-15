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
        console.log(recipe)
        this.state = {
            picture: false,
            src: recipe.R_RecipePicture
        //    difficulty: recipe.R_RecipeDifficulty
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
        //console.log(recipe.R_RecipeIngredientsOverview.toString().split("\\n"))
        let ingredientsOverview = ""
        recipe.R_RecipeIngredientsOverview.toString().split("\\n").map((ingredient, i) => (
            ingredientsOverview += (ingredient.toString() + "\n")
        ))
        //console.log(ingredientsOverview)
        let tags = [];
        for(let i=0; i<Object.keys(recipe.R_Recipe_Tags).length; i++){
            tags[i] = {};
            tags[i] = Object.keys(recipe.R_Recipe_Tags)[i]; 
        }
        {console.log(tags)}
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
        console.log(valuesTags)
        
        let allTagsArray = JSON.parse(localStorage.getItem("allTags"));
        let allTags = []
        for(let i=0; i<allTagsArray.length; i++){
            allTags[i] = {};
            allTags[i].value = allTagsArray[i].tagName;
            allTags[i].label = allTagsArray[i].tagName;
        }

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
                            <TextBox type='text' value={recipe.R_RecipeName} />
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
                            <select>
                            <option value="easy">Easy</option>
                            <option value="medium">Medium</option>
                            <option value="hard">Hard</option>
                            </select>                            
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
                            <input type='text' value={"Ingr"} />
                            </ColumnWrapper2>
                        </RowWrapper>
                    </div>
                    <br />
                    <hr />
                    <div>
                        <br />
                        <RowWrapper>
                            <ColumnWrapper1>
                            <TopLine>Recipe Ingredient Details</TopLine>
                            </ColumnWrapper1>
                            <ColumnWrapper2>
                            <TextAreaBox type='text' value={ingredientsOverview} rows="5" />
                            </ColumnWrapper2>
                        </RowWrapper>                        
                    </div>
                    <br />
                    <hr />
                    <div>
                    <br />
                        <RowWrapper>
                            <ColumnWrapper1>
                            <TopLine>Recipe Preparation</TopLine>
                            </ColumnWrapper1>
                            <ColumnWrapper2>
                            <input type='text' value={"prepara"} />
                            </ColumnWrapper2>
                        </RowWrapper> 
                    </div>
                    <br />
                    <hr />
                </form>
                
                <ButtonContainer>
                    <UploadButton>
                        İptal   Recipe
                    </UploadButton>
                    <UploadButton>
                        Update Recipe
                    </UploadButton>
                </ButtonContainer>

                    
            </AddEditContainer>
        );
    }
}