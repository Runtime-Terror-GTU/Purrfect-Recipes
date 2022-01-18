import CancelIcon from '@mui/icons-material/Cancel';
import "./modButtonElement.css"
import { removeMod } from '../../../backend/RecipeValueListener';
const ModButton = ({moderator}) => {
    
    //moderator button's cross part's event 
    const oldumubeee = e =>{
        //remove moderator
        removeMod(moderator);
        window.location.href = "/admin";
    }
    return (
        <div className="modButton">
            <div className="modName">
                <label>{moderator.R_Username}</label>
            </div>

            <div className="icon">
                <CancelIcon onClick={oldumubeee}/>
            </div>
        </div>
    );

}
export default ModButton;