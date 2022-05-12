# Grocery List Web App

This is an ongoing project for a simple grocery list web app that can be used to list down items to store 
in a list. The items can then be filtered by their names and compared to items of the
same name in different lists.

[Example image of the application](img/demo.png)

## Built with
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Vaadin Flow](https://vaadin.com/docs/latest/flow/overview)

## Running the application

The project is a standard Maven project. To run it from the command line,
type `mvnw` (Windows), or `./mvnw` (Mac & Linux), then open
http://localhost:8080 in your browser.

You can also import the project to your IDE of choice as you would with any
Maven project.

Lastly, you can also visit the deployed version of the application at 
https://personal-groceries-krz.herokuapp.com/

## Usage

1. Run the application via the methods mentioned above.
2. Navigate to `Edit grocery lists` from the sidebar and add a list via the `Add list` button.
3. Navigate to `Items` from the sidebar and select a list from the dropdown box.
4. Once a list has been selected, click on the `Add item` button to add items into the selected list.

## Deploying to Production

To create a production build, call `mvnw clean package -Pproduction` (Windows),
or `./mvnw clean package -Pproduction` (Mac & Linux).
This will build a JAR file with all the dependencies and front-end resources,
ready to be deployed. The file can be found in the `target` folder after the build completes.

Once the JAR file is built, you can run it using
`java -jar target/PersonalGroceryList-1.0-SNAPSHOT.jar`

## Project structure

- `MainLayout.java` in `src/main/java` contains the navigation setup (i.e., the
  side/top bar and the main menu). This setup uses
  [App Layout](https://vaadin.com/components/vaadin-app-layout).
- `views` package in `src/main/java` contains the server-side Java views of the grocery list application.
- `views` folder in `frontend/` contains the client-side JavaScript views of the grocery list application.
- `data` package in `src/main/java` contains the entities and repositries used to save and retrieve the data
of the grocery list application.
- `themes` folder in `frontend/` contains the custom CSS styles.

## License

Distributed under the MIT License. See `LICENSE.txt` for more information.
