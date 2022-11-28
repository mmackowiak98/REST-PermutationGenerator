# REST-PermutationGenerator

#### Implemented most of given tasks. Users can:
  - choose max length of a String,
  - choose min length of a String,
  - choose how many String to be generated,
  - choose chars to generate from,
  - get saved words from database,
  - get active threads count.
  
#### App works in parallel so it can take multiple request from users.
#### Maximum pool of threads can be changed in AsyncConfiguration file - for now it's 3.
#### App is using H2 Database, we can connect to it through | localhost:8080/h2-console | when app is running
  - username: sa
  - password: password

##### To test my api i used Postman and we can send POST request as seen on screenshot
![apipostman](https://user-images.githubusercontent.com/46621470/198575553-2988fea5-5287-4669-a891-a46af490ff80.PNG)
