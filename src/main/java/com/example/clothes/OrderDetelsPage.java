package com.example.clothes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetelsPage {

    private User user ;
    double total = 0.0;
    List<Order> orders = new ArrayList<>();

    public OrderDetelsPage() {

        user = SESL.getCurrentUser();
        loadOrderItems();
    }



    public Scene crate(){
        Label userIdLabel = new Label("Phone: " + user.getId());
        userIdLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: #333;");

        Label userNameLabel = new Label("Name: " + user.getName());
        userNameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: #333;");
        for (Order cart : orders){
            total +=cart.getPrice();
        }

        Label totalLabel = new Label("Total : " + total);
        totalLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: #333;");

        HBox userInfoBox = new HBox(20, userIdLabel, userNameLabel,totalLabel);
        userInfoBox.setPadding(new Insets(10));
        userInfoBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");

        TableView<Order> cartTable = createCartTable(getCartData(orders));



        VBox root = new VBox(20, userInfoBox, cartTable);
        root.setPadding(new Insets(20));

        VBox layout = new VBox(root);
        layout.setSpacing(5);
        Scene scene = new Scene(layout, 600, 400);


        return scene;
    }


    private ObservableList<Order> getCartData(List<Order> existingCartList) {
        return FXCollections.observableArrayList(existingCartList);
    }

    public static TableView<Order> createCartTable(ObservableList<Order> cartData) {
        TableView<Order> cartTable = new TableView<>();

        TableColumn<Order, Integer> cartIdColumn = new TableColumn<>("Order ID");
        cartIdColumn.setCellValueFactory(cart ->
                new SimpleIntegerProperty(cart.getValue().getId()).asObject()
        );

        TableColumn<Order, String> tableCollectionColumn = new TableColumn<>("Item");
        tableCollectionColumn.setCellValueFactory(cart -> {
            ClothesModel tablecollection = cart.getValue().getClothes();
            return new SimpleStringProperty(
                    tablecollection != null ? tablecollection.getName() : "Unknown"
            );
        });

        TableColumn<Order, Integer> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setCellValueFactory(cart ->
                new SimpleIntegerProperty(cart.getValue().getUserId()).asObject()
        );

        TableColumn<Order, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cart ->
                new SimpleDoubleProperty(cart.getValue().getPrice()).asObject()
        );

        TableColumn<Order, Integer> dateColumn = new TableColumn<>("quantity");
        dateColumn.setCellValueFactory(cart ->
                new SimpleIntegerProperty(cart.getValue().getQuantity()).asObject()
        );

        TableColumn<Order, String> stateColumn = new TableColumn<>("State");
        stateColumn.setCellValueFactory(cart ->
                new SimpleStringProperty(cart.getValue().getState())
        );

        cartTable.getColumns().addAll(cartIdColumn, tableCollectionColumn, userIdColumn, priceColumn, dateColumn, stateColumn);

        cartTable.setItems(cartData);

        return cartTable;
    }

    private void loadOrderItems() {

        String query = "SELECT c.id, c.user_id, c.price,c.quantity,c.state, tc.id AS clothes_id, tc.name, tc.about, tc.img_url " +
                "FROM cart c " +
                "JOIN clothes tc ON c.clothes_id = tc.id " +
                "WHERE c.user_id = ? AND c.state = ?";

        try (Connection connection = MyDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, "0");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {



                int t_id =resultSet.getInt("tablecollection_id");
                String t_name = resultSet.getString("name");
                String about = resultSet.getString("about");
                double price = resultSet.getDouble("price");
                String imgs = resultSet.getString("img_url");
                int qq = resultSet.getInt("quantity");




                ClothesModel tableCollection = new ClothesModel(t_id,t_name,about,imgs,price,1);

                Order cart = new Order(resultSet.getInt("id"),user.getId(),tableCollection,qq,price,"0");

                orders.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
