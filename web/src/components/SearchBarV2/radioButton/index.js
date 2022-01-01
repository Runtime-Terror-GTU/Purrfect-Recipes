import * as React from 'react';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';

export default function RowRadioButtonsGroup() {
  return (
    <FormControl component="fieldset">
      <FormLabel  component="legend">Search by</FormLabel>
      <RadioGroup row aria-label="Search by" name="row-radio-buttons-group">
        <FormControlLabel value="Recipe Name" control={ <Radio  color="default" />} label="Recipe Name" />
        <FormControlLabel value="Username" control={ <Radio  color="default" />} label="Username" />
      </RadioGroup>
    </FormControl>
  );
}