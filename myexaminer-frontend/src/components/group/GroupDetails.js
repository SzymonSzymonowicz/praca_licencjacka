import React from "react";
import { Card, CardContent, Typography, Paper, Box, Button } from "@material-ui/core";
import { Table, TableBody, TableCell, TableContainer, TableRow } from "@material-ui/core";
import { Link, useHistory } from "react-router-dom";
import styles from "./group.module.css";
import MenuBookIcon from "@material-ui/icons/MenuBook";
import { isLecturer } from "services/auth-service";
import authHeader from "services/auth-header";
import { groupByIdUrl } from "router/urls";
import EditIcon from "@material-ui/icons/Edit";
import DeleteConfirmButton from "components/reusable/button/DeleteConfirmButton";
import Modal from "components/reusable/modal/Modal";
import EditGroupForm from "./EditGroupForm";

export default function GroupDetails({ group, getGroup }) {
  const lecturer = group?.lecturer;
  const startingDate = new Date(group?.startingDate).toLocaleDateString();
  const groupId = group?.id;

  const history = useHistory();

  const deleteGroup = (groupId) => {
    fetch(groupByIdUrl(groupId), {
      method: "DELETE",
      headers: authHeader()
    })
    .then(res => {
      if (res.status === 200) {
        history.goBack();
      } else {
        console.log("Something went wrong");
        console.log(res);
      }
    })
    .catch(err => console.error(err))
  }

  return (
    <>
      <Card className={styles.grayBackground}>
        <CardContent style={{ padding: "16px" }}>
          <div className={styles.lecturerCard}>
            <div className={styles.iconSection}>
              <MenuBookIcon className={styles.lecturerIcon} />
            </div>
            <div className={styles.lecturerDetails}>
              <Typography gutterBottom variant="h5" component="h2">
                {lecturer?.firstName + " " + lecturer?.lastName}
              </Typography>
              <Typography variant="body2" color="initial" component="p">
                Email kontaktowy: {lecturer?.contactEmail}
              </Typography>
              <Typography variant="body2" color="initial" component="div">
                Strona domowa: {" "}
                <Link to={lecturer?.homePage}>{lecturer?.homePage}</Link>
              </Typography>
            </div>
          </div>
        </CardContent>
      </Card>

      <TableContainer component={Paper}>
        <Table aria-label="simple table">
          <TableBody>
            <TableRow>
              <TableCell component="th" scope="row">
                Nazwa grupy
              </TableCell>
              <TableCell align="right">{group?.name || ""}</TableCell>
            </TableRow>
            {isLecturer() &&
              <TableRow>
                <TableCell component="th" scope="row">
                  Kod dostępu
                </TableCell>
                <TableCell align="right">{group?.accessCode || ""}</TableCell>
              </TableRow>
            }
            <TableRow>
              <TableCell component="th" scope="row">
                Data rozpoczęcia
              </TableCell>
              <TableCell align="right">{startingDate || ""}</TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </TableContainer>

      <Box display="flex" justifyContent="space-between" style={{ paddingTop: "20px" }}>
        <Modal input={
          <Button color="primary" type="submit" variant="contained" startIcon={<EditIcon />}>
            Edytuj grupę
          </Button >
        }>
          <EditGroupForm group={group} getGroup={ getGroup }/>
        </Modal>

        <DeleteConfirmButton text={"Usuń grupę"} action={() => deleteGroup(groupId)} />
      </Box>


    </>
  );
}
