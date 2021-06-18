# About:

Our web application's purpose is to be a way of support for lecturers in learning students. Our backend is a REST API powered by Java with Spring framework and the frontend is written in React 17.0.1 using functional components and hooks.

## Authors:
- Mikołaj Stempin
- Szymon Szymonowicz

## Roles:
- student
- lecturer (added by admin, through database for now)

## Features:
- teaching groups
- lectures in groups, added by lecturers
- taking exams
- checking exams by lecturer
- checked exam results with students answers and lecturer's comments (for student)
- every student and lecturer got their own notepad
- token based login
- creating / editing / deleting groups
- joining to groups by access code
- lessons with CRUD
- chapters in lessons with CRUD and WYSIWYG [editor](https://github.com/mkhstar/suneditor-react)
- creating exams
- changing exam status

## Ideas for new features:
- checking grades by student and lecturer aswell
- grades charts
- lecturer creator
- exams time restriction kickout with save exam
- google translator (?) or other dictionary API
- announcments from lecturer in the group

# Technologies

## Backend
- Java SE 11
- Spring Boot, Data, Web, Security

## Database
- MySql

## Frontend
- Javascript
- React 17.0.1
- Components from [material-ui](https://material-ui.com/)
