# REST-PermutationGenerator

#### Implemented most of given tasks. Users can:
  - choose max length of a String,
  - choose min length of a String,
  - choose how many String to be generated,
  - choose chars to generate from.
  
#### App works in parallel so it can take multiple request from users.
#### App is using H2 Database, we can connect to it through | localhost:8080/h2-console | when app is running
  - username: sa
  - password: password
##### Unfortunately no test included, it was my 1st time with multithreading and couldn't come up with correct test solutions
##### Besides test couldn't come up with permutation with repetition algo so it let users choose how many Strings he wants but there is no error message when it's out of possibilities.
##### Not sure if every request should be written in new file so for now it just overwriting existing file
##### To test my api i used Postman and we can send POST request as seen on screenshot
![apipostman](https://user-images.githubusercontent.com/46621470/198575553-2988fea5-5287-4669-a891-a46af490ff80.PNG)
