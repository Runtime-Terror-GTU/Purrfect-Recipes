import './tagShape.css';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import * as React from 'react';

const Tag = ({tags}) => {

    // eventlerin controllable olması için react.js de bu yapı kullanılıyormus hata verip duruyordu
    const [checked, setChecked] = React.useState([true, false]);

    //içine onclick devam edilecek buradaki check i doğru alıyor
    const onClick = (event) => {
        console.log("Sağ" +event.target.checked+"sol:"+checked[0]);
        setChecked([event.target.checked, checked[0]]);
        
    };

   

    return (
    //css şeyleri container ın içinde çağırıyorum sırayla 
    <div  className='allTags'>
        <div className='leftCheckBox'>
        <FormControlLabel
        label=""
        control={<Checkbox checked={checked[0]} onChange={onClick} color='default'/>}
        />
        
        </div>

        <div className='tagName'>
            <label>{tags.tagName}</label>
        </div>
       
    </div>
    );

}
export default Tag;