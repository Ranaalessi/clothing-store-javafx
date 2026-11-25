package com.example.clothes;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginController {
   private Stage primaryStage;

   public LoginController(Stage stage) {
        this.primaryStage = stage;
   }
    public Scene inint(){

        Text title = new Text("Create an Account");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.DARKBLUE);


        Label nameLabel = new Label("Full Name:");
        nameLabel.setTextFill(Color.DARKBLUE);
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your full name");

        Label emailLabel = new Label("Email:");
        emailLabel.setTextFill(Color.DARKBLUE);
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setTextFill(Color.DARKBLUE);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.setTextFill(Color.DARKBLUE);
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm your password");

        Button signupButton = new Button("Sign Up");
        signupButton.setStyle("-fx-background-color: #4682B4; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-radius: 20px;");
        signupButton.setOnAction(e -> {


            DoLogin(nameField.getText(),emailField.getText(),passwordField.getText(),confirmPasswordField.getText());
        });

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(emailLabel, 0, 1);
        gridPane.add(emailField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(confirmPasswordLabel, 0, 3);
        gridPane.add(confirmPasswordField, 1, 3);

        VBox vbox = new VBox(20, title, gridPane, signupButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom, #E0F7FA, #B3E5FC);");

        Scene scene = new Scene(vbox, 400, 500);

        return scene;

    }

    private void DoLogin(String name, String email, String password, String confPass) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confPass.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Wirring");
            alert.setHeaderText("Empty Filds");
            alert.setContentText("please enter all the data");
            alert.showAndWait();
            return;
        }
        try (Connection connection = MyDB.getConnection()) {

            String sql = "INSERT INTO user (name,email, password) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);


                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int newId = generatedKeys.getInt(1);

                            User user = new User(newId, name, email, password);
                            SESL.setCurrentUser(user);

                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Wirring");
                            alert.setHeaderText("doned");
                            alert.setContentText("user: "+ user.getName());
                            alert.showAndWait();

                            HomeController welecomPage = new HomeController(primaryStage);
                            Scene PageScene = welecomPage.HomeCreate();
                            primaryStage.setScene(PageScene);

                        }
                    }




                } else {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Wirring");
                    alert.setHeaderText("Empty Filds");
                    alert.setContentText("please enter all the data");
                    alert.showAndWait();                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }



}
