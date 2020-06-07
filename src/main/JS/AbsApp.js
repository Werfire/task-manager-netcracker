import React from 'react';
import './App.css';
import MaterialTable from "material-table";
import {forwardRef} from 'react';
import { authenticationService } from './services/authentification.service';
import { LoginPage } from './AuthApp';
import { PrivateRoute } from './services/privateRoute';
import {BrowserRouter as Router, Link, Route} from "react-router-dom";

import App from "./App";


class AbsApp extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            currentUser: null
        };
    }

    componentDidMount() {
        authenticationService.currentUser.subscribe(x => this.setState({
            currentUser: x,
        }));
    }

    logout() {
        authenticationService.logout();
        localStorage.push('/login');
    }

    render() {
        const {currentUser} = this.state;
        return (
            <Router history={localStorage}>
                <div>
                    {currentUser &&
                    <nav className="navbar navbar-expand navbar-dark bg-dark">
                        <div className="navbar-nav">
                            <a onClick={this.logout} className="nav-item nav-link">Logout</a>
                        </div>
                    </nav>
                    }
                    <div className="jumbotron">
                        <div className="container">
                            <div className="row">
                                <div className="col-md-6 offset-md-3">
                                    <Route path="/login" component={LoginPage}/>
                                    <PrivateRoute exact path="/" component={App}/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </Router>
        );
    }

}

    export { AbsApp };
