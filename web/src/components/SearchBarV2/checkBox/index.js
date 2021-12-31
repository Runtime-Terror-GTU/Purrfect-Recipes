import * as React from 'react';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';

export default function CheckboxLabels() {
  return (
    <FormGroup>
      <FormControlLabel control={<Checkbox defaultChecked color='default' />} label="Easy" />
      <FormControlLabel control={<Checkbox defaultChecked color='default'/>} label="Medium" />
      <FormControlLabel control={<Checkbox defaultChecked color='default'/>} label="Hard" />
    </FormGroup>
  );
}