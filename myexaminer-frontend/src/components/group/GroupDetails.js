import React from "react";
import { Card, CardContent, Typography, Paper } from "@material-ui/core";
import { Table, TableBody, TableCell, TableContainer, TableRow } from "@material-ui/core";
import { Link } from "react-router-dom";
import styles from "./group.module.css";
import MenuBookIcon from "@material-ui/icons/MenuBook";
import { isLecturer } from "services/auth-service";

export default function GroupDetails({ group }) {
  const lecturer = group?.lecturer;
  const creationDate = new Date(group?.startingDate).toLocaleDateString();

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
                Data utworzenia
              </TableCell>
              <TableCell align="right">{creationDate || ""}</TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </TableContainer>
    </>
  );
}
