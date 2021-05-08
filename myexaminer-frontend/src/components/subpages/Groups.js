import { Avatar, Card, CardContent, CardMedia, Grid, TextField, Typography } from "@material-ui/core";
import React from "react";
import AddIcon from "@material-ui/icons/Add";
import { groupsForAccountUrl } from "router/urls";
import { getCurrentAccount } from "services/auth-service";
import authHeader from "services/auth-header";
import { useState } from "react";
import { useEffect } from "react";
import { generateShortcut, generateHexColor } from "utils/stringUtils";

export default function Groups() {
  const [groups, setGroups] = useState([]);

  useEffect(() => {
    async function fetchData() {
      const account = getCurrentAccount();

      const response = await fetch(groupsForAccountUrl(account.id), {
        method: "GET",
        headers: authHeader(),
      });

      const data = await response.json();

      setGroups(data);
    }

    fetchData();
  }, []);

  return (
    <Grid container spacing={2} alignItems="stretch">
      {groups.length !== 0 &&
        groups.map((group) => (
          <Grid item xs={2} key={`${group.name}`}>
            <Card style={{ height: "100%" }} elevation={6}>
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
                <Typography align="center">{group.name}</Typography>
              </CardContent>
              {/* </CardActionArea> */}
            </Card>
          </Grid>
        ))}

      <Grid item xs={2}>
        <Card elevation={6}>
          {/* <CardActionArea> */}
          <CardContent>
            <Grid container direction="column" alignItems="center">
              <AddIcon style={{ fontSize: "120px" }} />
              <TextField
                id="outlined-basic"
                label="Kod grupy"
                variant="outlined"
                inputProps={{ style: { textAlign: "center" } }}
                InputLabelProps={{ style: { textAlign: "center" } }}
              />
            </Grid>
          </CardContent>
          {/* </CardActionArea> */}
        </Card>
      </Grid>
    </Grid>
  );
}
