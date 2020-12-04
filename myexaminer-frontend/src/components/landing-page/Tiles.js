import { Card, CardActionArea, CardContent, CardMedia, Grid, makeStyles, Typography } from '@material-ui/core'
import React from 'react'

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
  
  const tilesData = [
    {
      "header": "Grupy",
      "image": "group-of-students.jpg",
      "imgTitle": "group of students learning",
      "description": "Przejdz do strony grup",
      "path": ""
    },
    {
      "header": "Egzaminy",
      "image": "check.jpg",
      "imgTitle": "marking a checkbox",
      "description": "Egzaminy przydzielone przez lektora do bieżącej grupy",
      "path": ""
    },
    {
      "header": "Ogłoszenia",
      "image": "megaphone.jpg",
      "imgTitle": "megaphone with speech bubbles",
      "description": "Ogłoszenia nadane przez lektora do członków grupy",
      "path": ""
    },
    {
      "header": "Oceny i statystyki",
      "image": "grade.jpg",
      "imgTitle": "two students holding a board with A+",
      "description": "Oceny z napisanych egzaminów, wraz z punktacją i róznego rodzaju statystykami typu wykres ostatnich egzaminów, wynik na tle grupy",
      "path": ""
    },
    {
      "header": "Słownik / tłumacz",
      "image": "dictionary.jpg",
      "imgTitle": "dictonary meaning of positive",
      "description": "Słownik oxford / tłumacz google z poziomu aplikacji",
      "path": ""
    }
  ]

  return (
    <Grid container spacing={4} >
      {tilesData.map(tile => (
        <Grid item xs={12} sm={6} lg={4}>
          <Card className={classes.root} key={tile.description} elevation={12}>
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
