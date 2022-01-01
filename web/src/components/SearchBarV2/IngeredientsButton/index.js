import './buttonShape.css';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import * as React from 'react';

const IngredientsButton = ({ingredients}) => {

    const [checked, setChecked] = React.useState([true, false]);


    const rightcheck = (event) => {
        console.log("Sağ" +event.target.checked+"sol:"+checked[0]);
        setChecked([event.target.checked, checked[0]]);
        
    };

    const leftcheck = (event) => {
        console.log("Sol"+event.target.checked);
        setChecked([event.target.checked, checked[1]]);
      };

    return (
    <div  className='allIngredient'>
        <div className='leftCheckBox'>
        <FormControlLabel
        label=""
        control={<Checkbox checked={checked[0]} onChange={rightcheck} color='default'/>}
        />
        
        </div>

        <div className='IngredientName'>
            <label>{ingredients.ingredientName}</label>
        </div>
        <div className='rightCheckBox'>
        <FormControlLabel
        label=""
        control={<Checkbox checked={checked[1]} onChange={leftcheck} color='default'/>}
        />
        </div>
    </div>
    );

}
export default IngredientsButton;