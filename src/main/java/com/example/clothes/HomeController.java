package com.example.clothes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeController {
    private Stage primaryStage;
    GridPane grid = new GridPane();
    private User user ;
    List<CategoryModel> categoryModelList = new ArrayList<>();
    List<ClothesModel> clothesModelList = new ArrayList<>();

    private HBox categoryBox = new HBox(4);
    public HomeController(Stage stage) {
        this.primaryStage = stage;
        user = SESL.getCurrentUser();
        loadCatogrysFromDatabase();
        loadClothesFromDatabase();
    }


    public Scene HomeCreate() {


         createCategorySection();

         HBox nav =createNavBar();

        createProductScrollPane(1);


        ScrollPane productScrollPane= new ScrollPane();
        productScrollPane.setContent(grid);
        productScrollPane.setFitToWidth(true);
        productScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #E0F7FA, #B3E5FC);");
        root.getChildren().addAll(nav, productScrollPane);



        Scene scene = new Scene(root, 800, 600);


        return scene;
    }

    private void createCategorySection() {
        categoryBox.setAlignment(Pos.CENTER);

        for (CategoryModel category : categoryModelList) {
            Button categoryButton = new Button(category.getName());
            categoryButton.setStyle("-fx-background-color: #4682B4; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 9px 9px; -fx-background-radius: 20px;");
            categoryButton.setOnAction(e -> displayClothesInCategory(category.getId()));
            categoryBox.getChildren().add(categoryButton);
        }

    }

    private void displayClothesInCategory(int categoryId) {
        createProductScrollPane(categoryId);

    }
    private void createProductScrollPane(int cat_id) {

        grid.getChildren().clear();

        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(8,4,8,4));

        int row = 0;
        int col = 0;


        if(cat_id == 0 ){

            for (ClothesModel product : clothesModelList) {


                    VBox productBox = createProductBox(product);


                    grid.add(productBox, col, row);


                    col++;
                    if (col == 4) {
                        col = 0;
                        row++;
                    }
                }





        }
        else{
            for (ClothesModel product : clothesModelList) {

                if(product.getCategoryId()==cat_id){
                    VBox productBox = createProductBox(product);


                    grid.add(productBox, col, row);


                    col++;
                    if (col == 4) {
                        col = 0;
                        row++;
                    }
                }



            }
        }








    }
    private HBox createNavBar() {
        Label titleLabel = new Label("Welcome to the Fashion Store");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        titleLabel.setTextFill(Color.DARKBLUE);

        Button logoutButton = new Button("CART");

        logoutButton.getStyleClass().add("logut-buttn");

        HBox leftSection = new HBox(10, titleLabel,categoryBox);
        leftSection.setAlignment(Pos.CENTER_LEFT);

        HBox rightSection = new HBox(10, logoutButton);
        rightSection.setAlignment(Pos.CENTER_RIGHT);

        HBox navBar = new HBox(leftSection, rightSection);

        navBar.setPadding(new Insets(10));
        navBar.getStyleClass().add("nav-bar");
        HBox.setMargin(leftSection, new Insets(0, 0, 0, 10));
        HBox.setMargin(rightSection, new Insets(0, 10, 0, 0));
        navBar.setAlignment(Pos.CENTER);
        navBar.setHgrow(leftSection, javafx.scene.layout.Priority.ALWAYS);


        logoutButton.setOnAction((ActionEvent e) -> {

            Stage stage = new Stage();
            OrderDetelsPage orderDetelsPage = new OrderDetelsPage();
            stage.setScene(orderDetelsPage.crate());
            stage.setTitle("Order");
            stage.show();
        });
        navBar.setStyle("-fx-background-color: linear-gradient(to bottom, #E0F7FA, #B3E5FC);");
        return navBar;
    }



    private VBox createProductBox(ClothesModel product) {
        VBox productBox = new VBox(15);
        productBox.setAlignment(Pos.CENTER);


        productBox.setStyle(
                "-fx-border-radius: 20px;\n" +
                        "-fx-border-color: #e0e0e0;\n" +
                        "-fx-border-width: 1px;\n" +
                        "-fx-background-color: linear-gradient(to bottom, #E0F7FA, #B3E5FC);\n" +
                        "-fx-padding: 10px;\n" +
                        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 10, 0, 2, 2);"
        );

        ImageView productImage = new ImageView(new Image(getClass().getResourceAsStream(product.getImgUrl())));
        productImage.setFitHeight(150);
        productImage.setFitWidth(150);
        productImage.setPreserveRatio(true);

        productImage.setStyle("-fx-fit-height: 150px; -fx-fit-width: 150px;");

        Label productName = new Label(product.getName());
        productName.setStyle("-fx-font-size: 16px;-fx-font-weight: bold;-fx-text-fill: #333333;-fx-padding: 5px;");

        Label productPrice = new Label("$" + product.getPrice());
        productPrice.setStyle(" -fx-font-size: 14px;-fx-text-fill: blue;-fx-font-weight: bold;-fx-padding: 5px;");

        Button addToCartButton = new Button();
        addToCartButton.setStyle("-fx-background-color: #4682B4; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 9px 9px; -fx-background-radius: 20px;");

        SVGPath cartIcon = new SVGPath();
        cartIcon.setContent("M11,9h2L13,6h3L16,4h-3L13,1h-2v3L8,4v2h3v3zM7,18c-1.1,0 -1.99,0.9 -1.99,2S5.9,22 7,22s2,-0.9 2,-2 -0.9,-2 -2,-2zM17,18c-1.1,0 -1.99,0.9 -1.99,2s0.89,2 1.99,2 2,-0.9 2,-2 -0.9,-2 -2,-2zM7.17,14.75l0.03,-0.12 0.9,-1.63h7.45c0.75,0 1.41,-0.41 1.75,-1.03l3.86,-7.01L19.42,4h-0.01l-1.1,2 -2.76,5L8.53,11l-0.13,-0.27L6.16,6l-0.95,-2 -0.94,-2L1,2v2h2l3.6,7.59 -1.35,2.45c-0.16,0.28 -0.25,0.61 -0.25,0.96 0,1.1 0.9,2 2,2h12v-2L7.42,15c-0.13,0 -0.25,-0.11 -0.25,-0.25z");
        cartIcon.setFill(Color.BLACK);
        cartIcon.setScaleX(1.5);
        cartIcon.setScaleY(1.5);

        addToCartButton.setGraphic(cartIcon);
        addToCartButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addToCart(product);
            }
        });


        HBox buttonBox = new HBox(addToCartButton);
        buttonBox.setAlignment(Pos.CENTER);

        productBox.getChildren().addAll(productImage, productName, productPrice, buttonBox);

        return productBox;
    }


    public void addToCart(ClothesModel product) {
        String checkQuery = "SELECT quantity FROM cart WHERE user_id = ? AND clothes_id = ?";
        String insertQuery = "INSERT INTO cart (user_id, clothes_id,  price, quantity, state) VALUES (?, ?, ?, ?, ?)";
        String updateQuery = "UPDATE cart SET quantity = quantity + 1 WHERE user_id = ? AND prodact_name = ?";
        try (Connection connection = MyDB.getConnection()) {
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setInt(1, user.getId());
            checkStmt.setInt(2, product.getId());

            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
                updateStmt.setInt(1, user.getId());
                updateStmt.setInt(2, product.getId());
                updateStmt.executeUpdate();
                makeAlert("تم إضافة المنتج: " + product.getName() + " إلى السلة.");
            } else {
                PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
                insertStmt.setInt(1, user.getId());
                insertStmt.setInt(2, product.getId());
                insertStmt.setDouble(3, product.getPrice());

                insertStmt.setInt(4, 1);
                insertStmt.setString(5, "1");
                insertStmt.executeUpdate();
                makeAlert("تم إضافة المنتج: " + product.getName() + " إلى السلة.");


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void makeAlert(String messeg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(messeg);
        alert.showAndWait();
    }
    private void loadCatogrysFromDatabase() {
        String query = "SELECT * FROM category";

        try (Connection connection = MyDB.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");


                categoryModelList.add(new CategoryModel(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void loadClothesFromDatabase() {
        String query = "SELECT * FROM clothes";

        try (Connection connection = MyDB.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String about = resultSet.getString("about");
                String img_url = resultSet.getString("img_url");
                double price = resultSet.getDouble("price");
                int cat_id = resultSet.getInt("category_id");



                clothesModelList.add(new ClothesModel(id, name,about,img_url,price,cat_id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

//-- إدخال بيانات للرجال
//    INSERT INTO clothes (name, about, img_url, price, category_id) VALUES
//('Men\'s T-Shirt', 'Comfortable cotton t-shirt for men', '1.jpg', 19.99, 1),
//        ('Men\'s Jeans', 'Stylish blue jeans for men', '2.jpg', 49.99, 1),
//        ('Men\'s Jacket', 'Warm and cozy jacket for men', '3.jpg', 89.99, 1),
//        ('Men\'s Hoodie', 'Soft hoodie for casual wear', '3.jpg', 39.99, 1),
//        ('Men\'s Shorts', 'Comfortable shorts for hot weather', '4.jpg', 29.99, 1),
//        ('Men\'s Sneakers', 'Sporty sneakers for men', '5.jpg', 69.99, 1);
//
//-- إدخال بيانات للنساء
//    INSERT INTO clothes (name, about, img_url, price, category_id) VALUES
//('Women\'s Dress', 'Elegant evening dress for women', 'w1.jpg', 79.99, 2),
//        ('Women\'s Blouse', 'Casual blouse for women', 'w2.jpg', 29.99, 2),
//        ('Women\'s Skirt', 'Trendy skirt for women', 'w3.jpg', 39.99, 2),
//        ('Women\'s Jacket', 'Chic jacket for women', 'w4.jpg', 59.99, 2);
