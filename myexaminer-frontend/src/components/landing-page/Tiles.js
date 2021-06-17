import { Card, CardActionArea, CardContent, CardMedia, Grid, makeStyles, Typography } from '@material-ui/core'
import { useHistory, useRouteMatch } from 'react-router-dom';
import React from 'react'
import { isLecturer } from 'services/auth-service';

const useStyles = makeStyles({
  root: {
    minWidth: 200
  },
  media: {
    height: 140
  },
});

export default function Tiles(props) {
  const classes = useStyles();
  const history = useHistory();
  let match = useRouteMatch();
  const tilesData = [
    {
      "header": "Grupy",
      "image": "group-of-students.jpg",
      "imgTitle": "group of students learning",
      "description": "Przejdz do strony grup",
      "path": "groups"
    },
    {
      "header": "Egzaminy",
      "image": "check.jpg",
      "imgTitle": "marking a checkbox",
      "description": "Egzaminy przydzielone przez lektora do bieżącej grupy",
      "path": "exams"
    },
    {
      "header": "Ogłoszenia",
      "image": "megaphone.jpg",
      "imgTitle": "megaphone with speech bubbles",
      "description": "Ogłoszenia nadane przez lektora do członków grupy",
      "path": "announcments"
    },
    {
      "header": "Oceny i statystyki",
      "image": "grade.jpg",
      "imgTitle": "two students holding a board with A+",
      "description": "Oceny z napisanych egzaminów, wraz z punktacją i róznego rodzaju statystykami typu wykres ostatnich egzaminów, wynik na tle grupy",
      "path": "grades"
    },
    {
      "header": "Słownik / tłumacz",
      "image": "dictionary.jpg",
      "imgTitle": "dictonary meaning of positive",
      "description": "Słownik oxford / tłumacz google z poziomu aplikacji",
      "path": "dictionary"
    },
    {
      "header": "Sprawdzanie egzaminów",
      // source: https://www.stockvault.net/photo/155289/teacher
      "image": "checking_exams.jpg",
      "imgTitle": "teacher checking exams",
      "description": "Sprawdź egzaminy napisane przez studentów.",
      "path": "checking",
      "showOnlyLecturer": true
    }
  ]

  const shouldShow = (tile) => {
    if (tile.showOnlyLecturer === true && !isLecturer()) {
      return false;
    }

    return true;
  }

  return (
    <Grid container spacing={4} alignItems="stretch">
      {tilesData.map((tile, index) => shouldShow(tile) && (
        <Grid item xs={12} sm={6} lg={4} key={tile.path}>
          <Card className={classes.root} elevation={12} style={{height: '100%'}}
            onClick={() => {
              history.push(`${match.path}/${tile.path}`)
              props.setSelectedIndex(index + 1)
          }}>
            <CardActionArea>
              <CardMedia
                className={classes.media}
                component="img"
                src={require('../../assets/images/tiles/' + tile.image).default}
                title={tile.imgTitle}
              />
              <CardContent>
                <Typography gutterBottom variant="h5" component="h2">
                  {tile.header}
                </Typography>
                <Typography variant="body2" color="textSecondary" component="p">
                  {tile.description}
                </Typography>
              </CardContent>
            </CardActionArea>
          </Card>
        </Grid>
      ))}
    </Grid>
  )
}
