import React, { useState, useEffect } from 'react';
import { 
    RecipeContainer, 
    RecipeWrapper,
    ImgWrap,
    Img,
    RecipeColumn,
    Column1, 
    Column2, 
    Heading,
    TopLine,
    PurrfectedRow,
    PurrfectedIcon,
    PurrfectedColumn1,
    PurrfectedColumn2,
    CommentsContainer,
    CommentsWrapper,
    CommentsColumn,
} from './RecipeScreenElements';
import Footer from '../HomePage/Footer';
import CommentBox from './CommentBox';
import AddComment from './AddComment';

export const RecipeScreen = () => {
    let stringRecipe = localStorage.getItem("currentRecipe");
    //alert("The Value Received is " + b);
    let recipe = JSON.parse(stringRecipe);

    let tags = [];
    for(let i=0; i<Object.keys(recipe.R_Recipe_Tags).length; i++){
        tags[i] = {};
        tags[i] = Object.keys(recipe.R_Recipe_Tags)[i]; 
    }
    let comments = [];
    for(let i=0; i<Object.keys(recipe.R_RecipeComments).length; i++){
        comments[i] = {};
        comments[i] = Object.keys(recipe.R_RecipeComments)[i]; 
    }

            
        console.log("cildirmamak")
        console.log(recipe.R_RecipeComments)
        console.log("elde")
        console.log("degil")
        

    if( recipe.length <= 15 ){
        return(
            <>
                <h1>Recipe not found</h1>
            </>
        )
    } else{
        return (
            <>  
            <RecipeContainer>
                <RecipeWrapper>
                    <ImgWrap>
                        <Img src={recipe.R_RecipePicture} alt="Recipe Picture" />
                    </ImgWrap>
                    <RecipeColumn>
                        <Column1>
                            <Heading> {recipe.R_RecipeName} </Heading>
                            <TopLine> Ingredients </TopLine>
                            {
                                recipe.R_RecipeIngredientsOverview.toString().split("\\n").map((ingredient, i) => (
                                    <div key={i}>
                                        <li> {ingredient} </li>
                                    </div>
                                ))
                            }
                            <TopLine> Preparations </TopLine>
                            {
                                recipe.R_RecipePreparation.map((preparation, i) => {
                                    if( preparation != null ){
                                        return(
                                            <div key={i}>
                                                <li> {preparation} </li>
                                            </div>
                                        )
                                    }
                                })
                            }
                        </Column1>
                        <Column2>
                            <h1> by {recipe.R_RecipeOwner} </h1>
                            <TopLine> Difficulty: {recipe.R_RecipeDifficulty} </TopLine>
                            <TopLine> Tags </TopLine>
                            {
                                tags.map((tag, i) => {
                                    if( tag != null ){
                                        return(
                                            <div key={i}>
                                                <li> {tag} </li>
                                            </div>
                                        )
                                    }
                                })
                            }
                            <PurrfectedRow>
                                <PurrfectedColumn1>
                                    <PurrfectedIcon />
                                </PurrfectedColumn1>
                                <PurrfectedColumn2>
                                    <h1> {recipe.R_RecipePurrfectedCount} </h1>
                                </PurrfectedColumn2>
                            </PurrfectedRow>
                        </Column2>
                    </RecipeColumn>
                </RecipeWrapper>
            </RecipeContainer>

            <CommentsContainer>
                <Heading> Comments </Heading>
                <CommentsWrapper>

                    <CommentsColumn>
                        {
                        comments.map((comment, i) => {
                            if( comment != null ){
                                return(
                                    <div key={i}>
                                        <CommentBox commentID={comment}/>
                                    </div>
                                )
                            }
                        })
                        }
                    </CommentsColumn>

                    <AddComment commentID={"34"}/>
 
                </CommentsWrapper>
            </CommentsContainer>

            <Footer />
            </>
        )   
    }
}

export default RecipeScreen;
