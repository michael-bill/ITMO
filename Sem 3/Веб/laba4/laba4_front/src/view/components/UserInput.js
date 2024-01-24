import {useDispatch, useSelector} from "react-redux";
import {useState} from "react";
import {Slider} from "primereact/slider";
import { Button } from 'primereact/button';

import "primereact/resources/themes/lara-light-indigo/theme.css";
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";

import {addHit, changeR} from "../../store/userSlice";
import {checkPoint} from "../../service/Service";
import {showError} from "../../store/errorSlice";

import "../../resources/UserInput.css"

const UserInput = () => {
    const dispatch = useDispatch();

    const userInfo = useSelector(state => state.user);

    const [point, setPoint] = useState({
        x: 0.0,
        y: 0.0,
        r: userInfo.r,
    });

    const [isLoading, setLoading] = useState(false);

    const handleRChange = (e) => {
        dispatch(changeR(e.value))
        setPoint({ ...point, r: e.value })
    };

    const handleSubmit = async () => {
        setLoading(true);

        const response = await checkPoint(point);
        if (response.message) {
            dispatch(showError({ detail: response.message }));
            setLoading(false);
            return;
        }
        dispatch(addHit(response));
        setLoading(false);
    }

    return (
        <div className="controller col-md">
            <label id="controller-title">Control pane</label>

            <div className="coordinateInput">
                <label className="coordinateName">X</label>
                <button className="button-num" onClick={() => setPoint({ ...point, x: -4 })}>-4</button>
                <button className="button-num" onClick={() => setPoint({ ...point, x: -3 })}>-3</button>
                <button className="button-num" onClick={() => setPoint({ ...point, x: -2 })}>-2</button>
                <button className="button-num" onClick={() => setPoint({ ...point, x: -1 })}>-1</button>
                <button className="button-num" onClick={() => setPoint({ ...point, x: 0 })}>0</button>
                <button className="button-num" onClick={() => setPoint({ ...point, x: 1 })}>1</button>
                <button className="button-num" onClick={() => setPoint({ ...point, x: 2 })}>2</button>
                <button className="button-num" onClick={() => setPoint({ ...point, x: 3 })}>3</button>
                <button className="button-num" onClick={() => setPoint({ ...point, x: 4 })}>4</button>
                <span className="coordinateValue">{point.x}</span>
            </div>
            <div className="coordinateInput">
                <label className="coordinateName">Y</label>
                <Slider value={point.y} min={-3} max={5} step={0.01} onChange={e => setPoint({ ...point, y: e.value })}/>
                <span className="coordinateValue">{point.y}</span>
            </div>
            <div className="coordinateInput">
                <label className="coordinateName">R</label>
                <button className="button-num" onClick={() => { handleRChange({ value: -4 }); }}>-4</button>
                <button className="button-num" onClick={() => { handleRChange({ value: -3 }); }}>-3</button>
                <button className="button-num" onClick={() => { handleRChange({ value: -2 }); }}>-2</button>
                <button className="button-num" onClick={() => { handleRChange({ value: -1 }); }}>-1</button>
                <button className="button-num" onClick={() => { handleRChange({ value: 0 }); }}>0</button>
                <button className="button-num" onClick={() => { handleRChange({ value: 1 }); }}>1</button>
                <button className="button-num" onClick={() => { handleRChange({ value: 2 }); }}>2</button>
                <button className="button-num" onClick={() => { handleRChange({ value: 3 }); }}>3</button>
                <button className="button-num" onClick={() => { handleRChange({ value: 4 }); }}>4</button>
                <span className="coordinateValue">{point.r}</span>
            </div>

            <div className="submitButton coordinateInput">
                <Button label="Check" icon="pi pi-times-circle" iconPos="right" loading={isLoading} onClick={handleSubmit} />
            </div>
            {/* <p>Current data. X: {point.x}, Y: {point.y}, R: {point.r}</p> */}
        </div>
    );
}

export default UserInput;