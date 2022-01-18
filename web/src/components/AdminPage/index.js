import React,{ useState, useEffect } from "react";
import "./adminElements.css";
import {getModerators} from "../../backend/RecipeValueListener";
import ModButtons from "./modButtons";



export default function  AdminPage () {
  
  const [moderators, setModerators] = useState([]);

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
          </div>

        </div>
      
      
      
    );
}
  


