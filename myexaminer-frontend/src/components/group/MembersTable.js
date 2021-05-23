import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TablePagination from "@material-ui/core/TablePagination";
import TableRow from "@material-ui/core/TableRow";
import { isLecturer } from "services/auth-service";
import DeleteConfirmButton from "components/reusable/button/DeleteConfirmButton";
import authHeader from "services/auth-header";
import { removeStudentFromGroupUrl } from "router/urls";

const columns = [
  {
    id: "firstName",
    label: "Imię",
    minWidth: 170,
  },
  {
    id: "lastName",
    label: "Nazwisko",
    minWidth: 100,
  },
  {
    id: "faculty",
    label: "Wydział",
    minWidth: 100,
    align: "center",
  },
  {
    id: "fieldOfStudy",
    label: "Kierunek",
    minWidth: 170,
    align: "center",
  },
  {
    id: "index",
    label: "Indeks",
    minWidth: 170,
    align: "center",
    // currently not used, left as example
    format: (value) => (isLecturer ? value : "------"),
    onlyLecturer: true
  },
];

const useStyles = makeStyles({
  root: {
    width: "100%",
  },
  container: {
    maxHeight: 440,
  },
});

export default function MembersTable(props) {
  const { students, groupId } = props;
  const rows = students || [];

  const classes = useStyles();
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  const shouldShow = (column) => {
    if (column.onlyLecturer === true && !isLecturer()) {
      return false;
    }

    return true;
  }

  const removeStudentFromGroup = (groupId, studentId) => {
    fetch(removeStudentFromGroupUrl(groupId, studentId), {
      method: "DELETE",
      headers: authHeader() 
    })
    .then(res => {
      if (res.status === 200) {
        props?.getGroup(groupId);
      } else {
        console.log("Could not remove student.");
      }
    })
    .catch(err => console.error(err))
  }

  return (
    <Paper className={classes.root}>
      <TableContainer className={classes.container}>
        <Table stickyHeader aria-label="sticky table">
          <TableHead>
            <TableRow>
              {columns.filter(shouldShow).map((column) => (
                <TableCell
                  key={column.id}
                  align={column.align}
                  style={{ minWidth: column.minWidth }}
                >
                  {column.label}
                </TableCell>
              ))}
              {isLecturer() &&
                <TableCell
                  align="center"
                  style={{ minWidth: "50px" }}
                >
                  Usuń
                </TableCell>}
            </TableRow>
          </TableHead>
          <TableBody>
            {rows?.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
              .map((row) => {
                return (
                  <TableRow hover role="checkbox" tabIndex={-1} key={row.id}>
                    {columns.filter(shouldShow).map((column) => {
                      const value = row[column.id];
                      return (
                        <TableCell key={column.id} align={column.align}>
                          {column.format ? column.format(value) : value}
                        </TableCell>
                      );
                    })}
                    
                    {isLecturer() && <TableCell key="remove" align="center">
                      <DeleteConfirmButton text="" action={() => removeStudentFromGroup(groupId, row?.id)} onlyIcon={true}/>
                    </TableCell>}
                  </TableRow>
                );
              })}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        rowsPerPageOptions={[10, 25, 100]}
        component="div"
        count={rows.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onChangePage={handleChangePage}
        onChangeRowsPerPage={handleChangeRowsPerPage}
      />
    </Paper>
  );
}
