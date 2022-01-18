import CancelIcon from '@mui/icons-material/Cancel';
import "./modButtonElement.css"

const ModButton = ({moderator}) => {
    console.log(moderator);

    const oldumubeee = e =>{
        
        

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