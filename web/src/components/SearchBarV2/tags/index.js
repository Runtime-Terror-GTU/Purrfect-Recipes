import { TagList } from '../../../backend/RecipeValueListener';
import React, { useState, useEffect } from 'react';
import { Grid } from 'semantic-ui-react';
import Tag from '../tag';


export default function Tags(){

    const [tags, setTags] = useState([]);
    
           

    const test = async (data) => {
       
        let countTags=Object.keys(data).length;

       
            

        for(let i=0; i<countTags; i++){
            console.log(data[i].tagName);
        }
        
    }
    

    //For Call Ingredients from RecipeValueListener
    useEffect(() => {
        (async function() {
            try {
                
                const tags = await TagList();
                setTags(tags);
                await test(tags);
            } catch (e) {
                console.error(e);
            }
        })();
    }, []);

    return (
        <Grid>
            
            {
                tags.map((tags ,i) => (
                    <Grid key={i}>
                        <Tag tags={tags}/>
                    </Grid>
                ))
            }
            
        </Grid>
      );





}