# Discussion Board

[Discussion board - demo video](https://youtu.be/bCqB5F68KEw)

## OO patterns in code

- Singleton
- MVC
- Proxy
- Mediator
- State

## Techstack

- **Language** - Java
- **Frontend** - Materialize components and Thymeleaf
- **Backend** - Spring Framework and its components
- **Data Source** - MySQl using XAMPP


## Steps to run code

**Prerequisite**
- Have Gradle 3.5.1 installed and setup
- IntelliJ installed and setup with Open JDK 1.8 to easily run the code
- XAMPP software installed to run MySQL server

**Run Application**
- Open XAMPP and start MySQl server and Apache webserver.
- Navigate to just ```localhost``` in chrome, go to phpMyAdmin tab, create a new database called ```discussion``` from control column on the left.
- Validate that both Apache Web server and MySQl server are running.
- Download the code from GitHub.
- Open it in intelliJ, setup the correct Java version under Project structure.
- Add the 'Specified location' to the installed gradle under project preferences.
- Now run the gradle.build file to auto download and install dependencies, alongside building the code.
- Once the build files are generated, Run the DiscussionBoard file under ```src/main/java/com.discussionboard/```.
- This will bring up Spring boot, create all the beans, setup connection with the database, create tables if missing, load up all the endpoints and boot up the application in ```localhost:8080```.
- Navigate to this on any browser and you should now be able to interact with the application.


### Note

- JDK 1.8 is mandatory to run
- Internet connection is required to load some components
