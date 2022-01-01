import './tagShape.css';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import * as React from 'react';

const Tag = ({tags}) => {

    const [checked, setChecked] = React.useState([true, false]);


    const onClick = (event) => {
        console.log("Sağ" +event.target.checked+"sol:"+checked[0]);
        setChecked([event.target.checked, checked[0]]);
        
    };

   

    return (
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