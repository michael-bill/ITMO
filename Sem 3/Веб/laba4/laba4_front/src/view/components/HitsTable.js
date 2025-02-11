import {useDispatch, useSelector} from "react-redux";

import {Button} from "primereact/button";
import {clearPointsTable} from "../../service/Service";
import {clearHits} from "../../store/userSlice";
import {showError} from "../../store/errorSlice";

import "../../resources/HitsTable.css"

function HitsTable() {
    const dispatch = useDispatch();

    const userInfo = useSelector(state => state.user);

    const Hit = (hit) => {
        return (
            <tr>
                <td>{hit.x}</td>
                <td>{hit.y}</td>
                <td>{hit.r}</td>
                <td>{hit.time}</td>
                <td style={{ color: hit.hit ?"greenyellow" : "red" }}>{hit.hit ? "True" : "False"}</td>
            </tr>
        );
    };

    const handleClear = async () => {
        const response = await clearPointsTable();
        if (response.error) {
            dispatch(showError({ detail: response.message }));
            return;
        }
        dispatch(clearHits());
    }

    return (
        <div className="table-dark col-md">
            <table id="results-table">
                <thead>
                <tr>
                    <th width="20%">X</th>
                    <th width="20%">Y</th>
                    <th width="15%">R</th>
                    <th width="40%">Time</th>
                    <th width="20%">Result</th>
                </tr>
                </thead>
                <tbody>
                    {
                        userInfo.hits.map(hit => {
                            return (
                                <Hit {...hit}/>
                            );
                        })
                    }
                </tbody>
            </table>
            <div id="clear-button-block">
                <Button label="Clear" id="clear-button" onClick={handleClear} />
            </div>
        </div>
    );
}

export default HitsTable;