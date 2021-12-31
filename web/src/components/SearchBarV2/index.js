import React, { useState } from "react";
import "./SearchBar.css";
import AddIcon from '@mui/icons-material/Add';

import FormLabel from '@mui/material/FormLabel';
import RowRadioButtonsGroup from './radioButton';
import  CheckboxLabels from './checkBox';
function SearchBar({ placeholder}) {
  
  const [input, setInput] = useState("");

  //search e tıklandığında tetiklenir

  const handleFilter = e => {
     // When form submited, clear input, close the searchbar and do something with input
     e.preventDefault();
     setInput("");
     // After form submit, do what you want with the input value
     console.log(`Form was submited with input: ${input}`);
  };

  

  return (
    <div className="search">
      <div className="searchInputs">
        <input
          id = "searchh"
          type="text"
          placeholder={placeholder}
          //search bar üzerinde yazı yazıldığında tetiklenir
          onChange={e => setInput(e.target.value)}
          value={input}
        />
        <div className="AddIcon">
          {input.length === 0 ? (
            <AddIcon />
          ) : (
            //Belki yazı yazılırken search şeklini değiştirmek isteriz
            <AddIcon onClick={handleFilter} />
          )}
          
        </div>
      </div>


      <RowRadioButtonsGroup />
      <CheckboxLabels/>

      <div className="allOfit">
    
        <div className="label">
          <label>Ingredients</label>
        </div>

        <div className="Ingredients">
            <CheckboxLabels/>
            <CheckboxLabels/>
        </div>
      
      </div>
      
    </div>
    
    
    
  );
   
  
}

export default SearchBar;
