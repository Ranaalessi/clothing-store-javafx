# Clothing Store (JavaFX)

A desktop clothing store application built with **Java 17, JavaFX and MySQL**.

## Features
- User login connected to a MySQL database
- Home page with clothing categories and product images
- Order creation and an order details page
- MVC-style structure: models (`ClothesModel`, `CategoryModel`, `Order`, `User`), controllers and FXML views

## Tech stack
Java 17 · JavaFX 17 (controls, FXML) · MySQL (JDBC) · Maven

## Run locally
1. Create a MySQL database named `clothes`.
2. Set your MySQL password in `src/main/java/com/example/clothes/MyDB.java`.
3. Run:
```
./mvnw clean javafx:run
```

## Author
Rana Alessi – Computer Science, Umm Al-Qura University
