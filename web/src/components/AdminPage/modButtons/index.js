import ModButton from "../modButton";
import { Grid } from 'semantic-ui-react';

export default function ModButtons({moderators}){
    
    return(
        <Grid>
            
        {
            
            moderators.map((moderator ,i) => (
                <Grid key={i}>
                    <ModButton moderator={moderator}/>
                </Grid>
            ))
        }
        
    </Grid>
    );
}
