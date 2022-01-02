import './buttonShape.css';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import * as React from 'react';

const IngredientsButton = ({ingredients}) => {

    /* 
    
        Includes one ingredient and 2 check boxes {in line 6 ingredients array come from IngredientsButtons's index.js}
        ingredients hold all ingredients' list
    
    */

    /*
        ----------------Onchange Events-------------------

    const [checked, setChecked] = React.useState([true, false]);


    const rightcheck = (event) => {
        console.log("Sağ" +event.target.checked+"sol:"+checked[0]);
        setChecked([event.target.checked, checked[0]]);
        
    };

    const leftcheck = (event) => {
        console.log("Sol"+event.target.checked);
        setChecked([event.target.checked, checked[1]]);
      };
    */
    return (
        /*
        div calls container from css. Container bunları içinde tutacak yer. css de boyutları konumları ayarlayıp burada
        içine koyuyorum
        */
    <div  className='allIngredient'>
        <div className='leftCheckBox'>
        
        
        <FormControlLabel
        /* 
            Yanlış çalışıyor buradaki yorum satırındakiler onchange={buraya} yazdığın fonksiyon 
            check box a tıklanınca çağırılıyor event böyle atanıyor (ağzımdan dusurmedigim trigger bu)

            FormControl label üstüne yazamadım yorum o yüzden buraya yazdım line 41 den 50 ye kadar aynı component
        */
        label=""
        control={<Checkbox /*checked={checked[0]} onChange={rightcheck}*/ color='default'/>}
        />
        
        </div>

        <div className='IngredientName'>
            <label>{ingredients.ingredientName}</label>
        </div>
        <div className='rightCheckBox'>
        <FormControlLabel
        label=""
        control={<Checkbox /* checked={checked[1]} onChange={leftcheck}*/ color='default'/>}
        />
        </div>
    </div>
    );

}
export default IngredientsButton;