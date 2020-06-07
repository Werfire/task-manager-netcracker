import React from 'react';
import logo from './logo.svg';
import './App.css';
import MaterialTable from "material-table";
import {forwardRef} from 'react';
import { authenticationService } from './services/authentification.service';
import AddBox from '@material-ui/icons/AddBox';
import ArrowDownward from '@material-ui/icons/ArrowDownward';
import Check from '@material-ui/icons/Check';
import ChevronLeft from '@material-ui/icons/ChevronLeft';
import ChevronRight from '@material-ui/icons/ChevronRight';
import Clear from '@material-ui/icons/Clear';
import DeleteOutline from '@material-ui/icons/DeleteOutline';
import Edit from '@material-ui/icons/Edit';
import FilterList from '@material-ui/icons/FilterList';
import FirstPage from '@material-ui/icons/FirstPage';
import LastPage from '@material-ui/icons/LastPage';
import Remove from '@material-ui/icons/Remove';
import SaveAlt from '@material-ui/icons/SaveAlt';
import Search from '@material-ui/icons/Search';
import ViewColumn from '@material-ui/icons/ViewColumn';

const tableIcons = {
    Add: forwardRef((props, ref) => <AddBox {...props} ref={ref}/>),
    Check: forwardRef((props, ref) => <Check {...props} ref={ref}/>),
    Clear: forwardRef((props, ref) => <Clear {...props} ref={ref}/>),
    Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref}/>),
    DetailPanel: forwardRef((props, ref) => <ChevronRight {...props} ref={ref}/>),
    Edit: forwardRef((props, ref) => <Edit {...props} ref={ref}/>),
    Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref}/>),
    Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref}/>),
    FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref}/>),
    LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref}/>),
    NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref}/>),
    PreviousPage: forwardRef((props, ref) => <ChevronLeft {...props} ref={ref}/>),
    ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref}/>),
    Search: forwardRef((props, ref) => <Search {...props} ref={ref}/>),
    SortArrow: forwardRef((props, ref) => <ArrowDownward {...props} ref={ref}/>),
    ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref}/>),
    ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref}/>)
};

function dateCast(dateStr) {
    let a = new Date();
    let arr = dateStr.split(' ');
    a.setFullYear(arr[0], arr[1].split('.')[1], arr[1].split('.')[0]);
    a.setHours(arr[2].split(':')[0], arr[2].split(':')[1]);
    return a
}

function dateCastToString(dateO) {
    if(typeof dateO === "string") dateO = new Date(dateO);
    return `${dateO.getFullYear()} ${("0" + dateO.getDate()).slice(-2)}.${("0" + (dateO.getMonth() + 1)).slice(-2)} ${("0" + dateO.getHours()).slice(-2)}:${("0" + dateO.getMinutes()).slice(-2)}`;
}


class App extends React.Component {
    // state = { data: []}
    constructor(props) {
        super(props);
        this.state = {
            columns: [
                {title: "Название", field: "name"},
                {title: "Описание", field: "description"},
                {title: "Дата создания", field: "creationDate", type: "datetime", initialEditValue: new Date()},
                {title: "Дата выполнения", field: "dueDate", type: "datetime"},
                {title: "Статус", field: "statusId", initialEditValue: 'In process'}
            ],
            data: []
        }

        this.state = {
            currentUser: authenticationService.currentUserValue,
            userFromApi: null
        };
        this.getTasks = this.getTasks.bind(this)
    }

    getTasks() {
        const URL = 'http://localhost:8080/rest/api/tasks';
        fetch(URL)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                let arr = [];
                for (let [key, value] of Object.entries(data)) {
                    value.creationDate = dateCast(value.creationDate);
                    value.dueDate = dateCast(value.dueDate);

                    arr.push(value);
                }
                this.setState({data: arr});
            });
    }

    componentDidMount() {
        this.getTasks();
        const { currentUser } = this.state;

        // function getById(id) {
        //     const requestOptions = { method: 'GET', headers: authHeader() };
        //     return fetch(`/users/${id}`, requestOptions);
        // }
        //
        // getById(currentUser.id).then(userFromApi => this.setState({ userFromApi }));
    }
    logout() {
        authenticationService.logout();
        localStorage.push('/login');
    }

    render() {
        return (
            <div style={{maxWidth: "100%"}}>
                <MaterialTable
                    title="Диспетчер задач"
                    columns={this.state.columns}
                    data={this.state.data}
                    icons={tableIcons}
                    editable={{
                        onRowAdd: newData => {
                            let body = {...newData};

                            body.dueDate = dateCastToString(body.dueDate);
                            body.creationDate = dateCastToString(body.creationDate);

                            return fetch('http://localhost:8080/rest/api/tasks', {
                                method: 'POST',
                                // mode: 'no-cors',
                                headers: {
                                    'Content-Type': 'application/json',
                                    // 'Authorization': authHeader()
                                    // 'Content-Type': 'text/plain'
                                },
                                body: JSON.stringify(body)
                            })
                                // .then(response => response.json())
                                .then(() => {
                                    console.log("A new task has been added")
                                    this.getTasks()
                                })
                        },
                        onRowUpdate: newData => {
                            let body = {...newData};

                            body.dueDate = dateCastToString(body.dueDate);
                            body.creationDate = dateCastToString(body.creationDate);

                            return fetch('http://localhost:8080/rest/api/tasks/' + newData.id, {
                                method: 'PUT',

                                headers: {
                                    'Content-Type': 'application/json',
                                    // 'Authorization': authHeader()
                                },
                                body: JSON.stringify(body)
                            })
                                .then(() => {
                                    console.log("This task has been edited")
                                    this.getTasks()
                                })
                        },
                        onRowDelete: oldData =>
                            fetch('http://localhost:8080/rest/api/tasks/' + oldData.id, {
                                method: 'DELETE',
                                headers: {
                                    // 'Authorization': authHeader()
                                }
                                // mode: 'no-cors',
                                // body: oldData.id
                            })
                                // .then(response => response.json())
                                .then(() => {
                                    console.log("This task has been deleted")
                                    this.getTasks()
                                })
                    }}
                />
            </div>
        );
    }
}

export default App;
