import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route } from "react-router-dom";
import AddProject from "./components/Project/AddProject";
import { Provider } from "react-redux";
import store from "./store";
import UpdateProject from "./components/Project/UpdateProject";
import ProjectBoard from "./components/ProjectBoard/ProjectBoard";
import AddProjectTasks from "./components/ProjectBoard/ProjectTasks/AddProjectTasks";
import UpdateProjectTasks from "./components/ProjectBoard/ProjectTasks/UpdateProjectTasks";

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className="App">
          <Header />
          <Route exact path="/dashboard" component={Dashboard} />
          <Route exact path="/addProject" component={AddProject} />
          <Route exact path="/updateProject/:id" component={UpdateProject} />
          <Route exact path="/projectBoard/:id" component={ProjectBoard} />
          <Route exact path="/addProjectTask/:id" component={AddProjectTasks} />
          <Route
            exact
            path="/updateProjectTask/:backlog_id/:pt_id"
            component={UpdateProjectTasks}
          />
        </div>
      </Router>
    </Provider>
  );
}

export default App;
