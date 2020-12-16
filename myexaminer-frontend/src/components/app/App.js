import React from 'react';
import { BrowserRouter as Router, Switch, Route} from 'react-router-dom';

import Landing from '../landing-page/Landing';
import Home from '../login-page/Home';

function App() {
  return (
    <div className="App">
      <Router>
        <Switch>
          <Route path="/landing">
            <Landing/>
          </Route>
          <Route path="/">
            <Home/>  
          </Route>
        </Switch>
      </Router>    
    </div>
  );
}

export default App;
