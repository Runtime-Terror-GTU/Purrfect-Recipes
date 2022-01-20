import React,{ useState, useEffect } from "react";
import "./adminElements.css";
import {addModerator, getModerators} from "../../backend/RecipeValueListener";
import ModButtons from "./modButtons";
import { FormInput,FormButton } from "./addModeratorElements";
import { v4 as uuidv4 } from 'uuid';

export default function  AdminPage () {
  
  const [moderators, setModerators] = useState([]);

  const onSubmit = async (e) => {

    if( 
      document.getElementById("Email").value !== "" 
      && document.getElementById("password").value !== "" 
      && document.getElementById("username").value !== ""
      ){
        
        let moderator = {
          id: uuidv4(),
          password: document.getElementById("password").value,
          email: document.getElementById("Email").value,
          username: document.getElementById("username").value
        }
        let deneme = await addModerator(moderator);
        if ( deneme === true)
          window.location.href = "/admin";
        else
          alert("Username must be unique");

      } 
    
    else{
        console.log("something empty");
        alert("Fields cannot be empty");
    }

  }

  useEffect(() => {
    (async function() {
        try {
            
            let moderators = await getModerators();
            setModerators(moderators);
            
        } catch (e) {
            console.error(e);
        }
    })();
  }, []);

    return (
     
        <div className ="allofit left right">

          <div className="split left">

            <div className= "header">
              <h1>Moderator List</h1>
            </div>

            <div className="modButton_">
              <ModButtons moderators={moderators}/>
            </div>

          </div>
  
          <div className="split right">

            <div className= "header">
              <h1>Add Moderator</h1>
            </div>

            <div className="addModerator">
              
              <div className="inputMod email">
              
                <FormInput id="Email" placeholder="Email" required/>
              </div>

              <div className="inputMod input">
                <FormInput id="username" placeholder="Moderator Name" required/>
              </div>

              <div className="inputMod input">
                <FormInput type='password' id="password" placeholder="Password" required/>
              </div>

              <div className="inputMod addButton">
                <FormButton type='button' onClick={onSubmit}>Add Moderator</FormButton>
              </div>

                
                
              
              

            </div>
          </div>

        </div>
      
      
      
    );
}
  


