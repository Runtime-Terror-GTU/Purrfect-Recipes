import * as React from 'react';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';


/*

  That is Easy Medium Hard Check Boxes's Codes 
  
  Onclick event || Onchange event will write after  
  <FormControlLabel control={<Checkbox statements 
    and implement above return statement ınder export default...
 
*/
export default function CheckboxLabels() {
  return (
    <FormGroup>
      <FormControlLabel control={<Checkbox defaultChecked color='default' />} label="Easy" />
      <FormControlLabel control={<Checkbox defaultChecked color='default'/>} label="Medium" />
      <FormControlLabel control={<Checkbox defaultChecked color='default'/>} label="Hard" />
    </FormGroup>
  );
}