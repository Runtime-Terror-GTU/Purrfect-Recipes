import React, { useState } from "react";
import "./SearchBar.css";
import AddIcon from '@mui/icons-material/Add';

import IngredientsButtons from "./IngredientsButtons";
import RowRadioButtonsGroup from './radioButton';
import  CheckboxLabels from './checkBox';
import Tags from "./tags";
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

  
  /*
  
  line 40 search container
  line 42 arama yazılan yer 
  line 50 o + nın olduğu yer
  line 58 search barın sonu 
  line 62 search by radio buttonları 
  line 63 easy medium hard check box larını çağırmak için 
  line 66 ingerdients ın başladığı yer
  line 68 border içinde(çerçeve içindeki) ingredients yazısı
  line 72 ingerdients buttons ve ingredients yazısı(label ı) container başlangıcı .scroll'un css i 
  line 73 ingredients butonlarının çağrıldığı yer

  line 80 tags buttons ve tags yazısı (label ı) conteiner ı başlangıcı

  */
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
            <IngredientsButtons/>
        </div>
      
      </div>

      <div className="allOfit">
    
        <div className="label">
          <label>Tags</label>
        </div>

        <div className="Ingredients">
            <Tags/>
        </div>
      
      </div>

      
    </div>
    
    
    
  );
   
  
}

export default SearchBar;
