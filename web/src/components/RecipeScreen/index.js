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
    AddCommentColumn,
    PremiumImg,
    DeleteIcon,
    EditIcon,
    IconWrapper,
    IconLink
} from './RecipeScreenElements';
import Footer from '../HomePage/Footer';
import CommentBox from './CommentBox';
import AddComment from './AddComment';
import PremiumIcon from '../../images/premium_symbol.png';
import { IngredientList, TagList } from '../../backend/RecipeValueListener';

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
    if (typeof(recipe.R_RecipeComments) !== 'undefined' && recipe.R_RecipeComments != null) {
        for(let i=0; i<Object.keys(recipe.R_RecipeComments).length; i++){
            comments[i] = {};
            comments[i] = Object.keys(recipe.R_RecipeComments)[i]; 
        }
    } else {
        console.log('Undefined or Null')
    }

    const [allTags, setTags] = useState([]);
    const [allIngredients, setIngredients] = useState([]);

    const fetchTags = async() => {
        let data = await TagList();
        return data;
    }
    const fetchIngredients = async() => {
        let data = await IngredientList();
        return data;
    }
        
    useEffect(() => {
        (async function() {
            try {
                const allTags = await fetchTags();
                const allIngredients = await fetchIngredients();
                setTags(allTags);
                setIngredients(allIngredients);
            } catch (e) {
                console.error(e);
            }
        })();
    }, []);


    const deleteRecipe = () => {
        console.log("delete")
    }


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
                        <h1> by {recipe.R_RecipeOwner} 
                        {(() => {
                        if( recipe.R_RecipeOwnerStatus === "PREMIUM" ){
                            return(
                                <PremiumImg src={PremiumIcon}/>         
                            )
                        }               
                        })()}
                        </h1>
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
                        {(() => {
                        let stringUser = localStorage.getItem("currentUser");
                        let user = JSON.parse(stringUser);
                        if( recipe.R_RecipeOwner === user[Object.keys(user)].R_Username ){
                            return(
                                <>
                                <IconWrapper>
                                    <IconLink  onClick={deleteRecipe} to='/mainpage' >
                                        <DeleteIcon /> 
                                    </IconLink>
                                    <IconLink 
                                    onClick={(e) => {localStorage.setItem("allTags", JSON.stringify(allTags))
                                    localStorage.setItem("allIngredients", JSON.stringify(allIngredients))
                                    } }
                                    to='editrecipe'>
                                        <EditIcon /> 
                                    </IconLink>
                                </ IconWrapper>
                                </>
                            )   
                        }               
                        })()}

                    </Column2>
                </RecipeColumn>
            </RecipeWrapper>
        </RecipeContainer>

        <CommentsContainer>
            <Heading> Comments </Heading>
            {(() => {
            if( comments.length <= 0 ){
                return(
                    <CommentsWrapper>
                        <CommentsColumn>
                            <h1>no comments</h1>
                        </CommentsColumn>
                        <AddCommentColumn>
                            <AddComment />
                        </AddCommentColumn>
                    </CommentsWrapper>                   
                )
            } else{
                return(
                    <CommentsWrapper>
                        <CommentsColumn>
                            {
                            comments.map((comment, i) => {
                                if( comment !== null ){
                                    return(
                                        <div key={i}>
                                            <CommentBox commentID={comment}/>
                                        </div>
                                    )
                                }
                            })
                            }
                        </CommentsColumn>
                        <AddCommentColumn>
                            <AddComment />
                        </AddCommentColumn>
                    </CommentsWrapper>
                )
            }                
            })()}
        </CommentsContainer>

        <Footer />
        </>
    )   
}


export default RecipeScreen;
