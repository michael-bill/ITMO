import Header from "../components/Header";
import UserInput from "../components/UserInput";
import Graph from "../components/Graph";
import HitsTable from "../components/HitsTable";
import {useDispatch, useSelector} from "react-redux";
import {getPointsTable} from "../../service/Service";
import {setHits} from "../../store/userSlice";
import ErrorMessage from "../components/ErrorMessage";
import {Navigate} from "react-router-dom";

const MainPage = () => {
    const dispatch = useDispatch();
    const token = useSelector(state => state.user.token)

    if (!token) {
        return <Navigate to="/login" />;
    }

    console.log("Get all hits")

    getPointsTable(token).then( res => {
        if (Array.isArray(res)) dispatch(setHits(res));
    })

    return (
        <>
            <Header />
            <div className="row">
                <Graph />
                <UserInput />
                <HitsTable />
            </div>
            <ErrorMessage />
        </>
    )
}
export default MainPage;