import { Avatar, Button, Card, CardActionArea, CardContent, CardMedia, Grid, TextField, Typography } from "@material-ui/core";
import React from "react";
import GroupAddIcon from '@material-ui/icons/GroupAdd';
import AddIcon from '@material-ui/icons/Add';
import { groupsForAccountUrl } from "router/urls";
import { getCurrentAccount, isLecturer } from "services/auth-service";
import authHeader from "services/auth-header";
import { useState } from "react";
import { useEffect } from "react";
import { generateShortcut, generateHexColor } from "utils/stringUtils";
import { groupsStudentsUrl } from "router/urls";
import { useHistory } from "react-router-dom";
import CreateGroupModal from "components/group/CreateGroupModal";
import CreateGroupForm from "components/group/CreateGroupForm";

export default function Groups() {
  const [groups, setGroups] = useState([]);
  const history = useHistory();

  const addToGroup = (accessCode) => {
    fetch(groupsStudentsUrl, {
      method: 'POST',
      headers: {
        ...authHeader(),
        "Content-Type": "application/json",
        'Accept': '*/*',
      },
      body: accessCode
    }).then((response) => response.text()
    ).then(data => {
      console.log(data);
      getGroups();
    }).catch((error) => {
      console.log("error");
    });
  }

  const handleKeyPressed = (event) => {
    var code = event.keyCode || event.which;
    if (code === 13) {
      console.log("Enter pressed");
      addToGroup(event.target.value);
      event.target.value = "";
    }
  }

  const getGroups = async () => {
    const account = getCurrentAccount();

    const response = await fetch(groupsForAccountUrl(account.id), {
      method: "GET",
      headers: authHeader(),
    });

    const data = await response.json();

    setGroups(data);
  }

  useEffect(() => {
    getGroups();
  }, []);

  return (
    <Grid container spacing={2} alignItems="stretch">
      {groups.length !== 0 &&
        groups.map((group) => (
          <Grid item xs={2} key={`${group.name}`}>
            <Card style={{ height: "100%" }} elevation={6}
              onClick={() => {
                history.push(`group/${group.id}`);
              }}
            >
              <CardActionArea style={{ height: "100%" }}>
                <CardMedia style={{ height: "70%" }}>
                  <Avatar
                    variant={"square"}
                    style={{
                      backgroundColor: generateHexColor(group.name),
                      height: "100%",
                      width: "100%",
                      fontSize: "60px",
                      color: "black",
                    }}
                  >
                    {generateShortcut(group.name)}
                  </Avatar>
                </CardMedia>
                <CardContent>
                  <Typography align="center" style={{ padding: "15px 0px" }}>{group.name}</Typography>
                </CardContent>
              </CardActionArea>
            </Card>
          </Grid>
        ))}

      <Grid item xs={2}>
        <Card elevation={6}>
          <CardContent>
            <Grid container direction="column" alignItems="center">
              <GroupAddIcon style={{ fontSize: "140px" }} />
              <Typography style={{ padding: "10px", margin: "10px 0px" }}>Dołącz do grupy</Typography>
              <TextField
                id="outlined-basic"
                label="Kod grupy"
                variant="outlined"
                inputProps={{ style: { textAlign: "center" } }}
                InputLabelProps={{ style: { textAlign: "center" } }}
                onKeyPress={handleKeyPressed}
              />
            </Grid>
          </CardContent>
        </Card>
      </Grid>
      {isLecturer() && <Grid item xs={2}>
        <Card elevation={6}>
          <CardContent>
            <Grid container direction="column" alignItems="center">
              <AddIcon style={{ fontSize: "140px" }} />
              <Typography style={{ padding: "10px", margin: "10px 0px" }}>Utwórz grupę</Typography>
              <CreateGroupModal>
                <CreateGroupForm getGroups={getGroups}/>
              </CreateGroupModal>
            </Grid>
          </CardContent>
        </Card>
      </Grid>}
    </Grid>
  );
}
