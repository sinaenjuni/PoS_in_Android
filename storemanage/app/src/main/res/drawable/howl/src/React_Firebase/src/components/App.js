import React from 'react';
import AppShell from './AppShell';

import {HashRouter as Router, Route} from "react-router-dom";
import Home from './Home';
import Texts from './Texts';
import Words from './Words';


class App extends React.Component {
    render() {
        return (
            <div>
                <Router>
                    <AppShell>
                        <div>
                            <Route exact path='/' component={Home}/>
                            <Route exact path='/texts' component={Texts}/>
                            <Route exact path='/words' component={Words}/>
                        </div>
                    </AppShell>
                </Router>
            </div>
        );
    }
}

export default App;
