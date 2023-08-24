/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Student
 */
public class BookStore extends Application {
    private Label usernameT,passwordT;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button login, cancel;
    
    private Customer currentCustomer;
    TextField addTitle, addPrice, addUsername, addPassword, addPoints;
    TableView<Books> bookTable;
    TableView<Customer> customerTable;
    ObservableList<Books> books = FXCollections.observableArrayList();
    ObservableList<Customer> selectCustomers = FXCollections.observableArrayList();
    ObservableList<Books> selectBooks = FXCollections.observableArrayList();
    ObservableList<Customer> customers = FXCollections.observableArrayList();
    
    
   
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Arman's BookStore Login");
        
        Text title = new Text("Welcome to Arman's Bookstore!");
        title.setFont(Font.font("Monospaced", FontWeight.BOLD, 25));
        
        usernameT=new Label("Username");
        passwordT=new Label("Password");
        usernameField=new TextField();
        passwordField=new PasswordField();
        login=new Button("Login");
        cancel=new Button("Cancel");
        
        
        GridPane grid=new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5,5,5,5));
        Color color1 = Color.rgb(210, 180, 140); // Light Brown
        Color color2 = Color.rgb(188, 143, 143); // Rosy Brown
        LinearGradient gradient = new LinearGradient(
            0, 0, 0, 1,
            true, CycleMethod.NO_CYCLE,
            new Stop(0, color1),
            new Stop(1, color2));


        // Apply gradient background
        grid.setStyle("-fx-background-color: " + toCssString(gradient));
        grid.add(title,0,0,5,1);
        grid.add(usernameT,0,1);
        grid.add(usernameField,1,1);
        grid.add(passwordT,0,2);
        grid.add(passwordField,1,2);
        grid.add(login,0,3);
        grid.add(cancel,1,3);
        
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
        
        login.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                String username=usernameField.getText();
                String password=passwordField.getText();
                
                if(username.trim().isEmpty()||password.trim().isEmpty()){
                    showAlert("Please Fill in All Fields");   
                }
                
                else if(username.equals("admin")&&password.equals("admin")){
                   OwnerScreen(primaryStage); 
                }
                else if (Customer.verify(username, password)==true){
                    CustomerScreen(primaryStage,username);
                }
                else{
                    showAlert("Wrong Username or Password");
                }
            }
        });
        
        Scene scene = new Scene(grid,700,500);
        primaryStage.setScene(scene);
        primaryStage.show();
      
        
       
    }
    
    public void OwnerScreen(Stage primaryStage){
        
        primaryStage.setTitle("Admin Screen");
       
        Text Title = new Text("Home Screen");
        Title.setFont(Font.font("Monospaced", FontWeight.BOLD, 30));
        
        
        Button custm=new Button("Manage Customer");
        Button Books = new Button ("Manage Books");
        Button Logout = new Button("Logout");
        custm.setMinSize(300, 100);
        Books.setMinSize(300, 100);
        Logout.setMinSize(300, 100);
        Color color1 = Color.rgb(210, 180, 140); // Light Brown
        Color color2 = Color.rgb(188, 143, 143); // Rosy Brown
        LinearGradient gradient = new LinearGradient(
            0, 0, 0, 1,
            true, CycleMethod.NO_CYCLE,
            new Stop(0, color1),
            new Stop(1, color2));
        
        custm.setStyle("-fx-background-color: #eab676;");
        Books.setStyle("-fx-background-color: #eab676;");
        Logout.setStyle("-fx-background-color: #eab676;");
        
        
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: " + toCssString(gradient));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        HBox Titlebox = new HBox(Title);
        Titlebox.setAlignment(Pos.CENTER);
        grid.add(Titlebox,0,1,3,1);
        grid.add(custm,0,2,3,1);
        grid.add(Books,0,3,3,1);
        grid.add(Logout,0,4, 3, 1);
        
         custm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                manageCustomers(primaryStage);
                
            }
        });
          Books.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                manageBooks(primaryStage);
                
            }
        });
           Logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                start(primaryStage);
                
            }
        });
        
        Scene scene=new Scene(grid,700,500);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        
        
    }
    
    public void manageCustomers(Stage primaryStage){
        primaryStage.setTitle("Manage Customers");
        Color color1 = Color.rgb(210, 180, 140); // Light Brown
        Color color2 = Color.rgb(188, 143, 143); // Rosy Brown
        LinearGradient gradient = new LinearGradient(
            0, 0, 0, 1,
            true, CycleMethod.NO_CYCLE,
            new Stop(0, color1),
            new Stop(1, color2));
        
        Owner owner=new Owner("customers.txt");
        
        TextField addUsername = new TextField();
        addUsername.setPromptText("Username");
        addUsername.setMinWidth(100);

        //Password Input
        TextField addPassword = new TextField();
        addPassword.setPromptText("Password");
        addPassword.setMinWidth(100);
        
        TextField addPoints = new TextField();
        addPoints.setPromptText("Add Points");
        addPoints.setMinWidth(50);

        //Username Column
        TableColumn<Customer, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setMinWidth(200);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));

        //Password Column
        TableColumn<Customer, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setMinWidth(200);
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("Password"));

        //Points Column
        TableColumn<Customer, String> pointsColumn = new TableColumn<>("Points");
        pointsColumn.setMinWidth(100);
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("Points"));

        customerTable = new TableView<>();
        customerTable.setItems(owner.getUsers());
        customerTable.getColumns().addAll(usernameColumn, passwordColumn, pointsColumn);

        Button delete = new Button("Delete");
        Button back = new Button("Back");
        Button add = new Button("Add");

        back.setOnAction((ActionEvent e) -> {
            OwnerScreen(primaryStage);
        });

        add.setOnAction((ActionEvent e) -> {

            String usernameinput = addUsername.getText();
            String passwordinput = addPassword.getText();
            String pointsinput = addPoints.getText();
 
            try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
                String line = br.readLine();
                boolean usernameExists = false;
                while (line != null) {
                    String[] info2 = line.split(",");

                    if (info2[0].equals(usernameinput)) {
                        // user already exists
                        usernameExists = true;
                        break;
                    }
                    line = br.readLine();
                }
                br.close();
                
                if(usernameinput.trim().isEmpty()||passwordinput.trim().isEmpty()||!isInteger(pointsinput)){
                showAlert("Please Fill in All Fields Correctly");   
                }

                else if (usernameExists) {
                    System.out.println("User with that name already exists.");
                    showAlert("User with that name already exists.");
                } 
                else {
                    
                    owner.write( new Customer(addUsername.getText(), addPassword.getText(), Integer.parseInt(pointsinput)));
                    addUsername.clear();
                    addPassword.clear();
                    addPoints.clear();
                }

            } catch (IOException o) {
                o.printStackTrace();
            }
        });


        delete.setOnAction((ActionEvent e) -> {
            customers = customerTable.getItems();
            selectCustomers = customerTable.getSelectionModel().getSelectedItems();
            owner.delete( selectCustomers.get(0));
        });

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(addUsername, addPassword, addPoints, add);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(customerTable, hBox, delete, back);
        vBox.setPadding(new Insets(35, 35, 35, 35));
        vBox.setSpacing(10);
        vBox.setStyle("-fx-background-color: " + toCssString(gradient));
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void manageBooks(Stage PrimaryStage){
        PrimaryStage.setTitle("Manage Books");
        Color color1 = Color.rgb(210, 180, 140); // Light Brown
        Color color2 = Color.rgb(188, 143, 143); // Rosy Brown
        LinearGradient gradient = new LinearGradient(
            0, 0, 0, 1,
            true, CycleMethod.NO_CYCLE,
            new Stop(0, color1),
            new Stop(1, color2));
        
        BookShop BookShop = new BookShop("books.txt");
        
        //Username Column
        TableColumn<Books, String> TitleColumn = new TableColumn<>("Title");
        TitleColumn.setMinWidth(200);
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));

        //Password Column
        TableColumn<Books, String> AuthColumn = new TableColumn<>("Author");
        AuthColumn.setMinWidth(200);
        AuthColumn.setCellValueFactory(new PropertyValueFactory<>("Author"));

        //Points Column
        TableColumn<Books, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));

        bookTable = new TableView<>();
        try {
            bookTable.setItems(BookShop.read());
        } catch (IOException ex) {
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        bookTable.getColumns().addAll(TitleColumn, AuthColumn, priceColumn);
        
        Button addBook = new Button("Add");
        Button deleteBook = new Button("Delete");
        Button back = new Button("Back");
        
        
        TextField title = new TextField();
        title.setMinWidth(100);
        title.setPromptText("Title");
        
        TextField Author = new TextField();
        Author.setMinWidth(100);
        Author.setPromptText("Author");
        
        TextField Price = new TextField();
        Price.setMinWidth(50);
        Price.setPromptText("Price");
        
        addBook.setOnAction((ActionEvent e) -> {
            String titleInput =title.getText();
            String authorInput = Author.getText();
            String priceInput = Price.getText();
            
            if(titleInput.trim().isEmpty()||authorInput.trim().isEmpty()||priceInput.trim().isEmpty()||!isInteger(priceInput))
                showAlert("Please Fill in All Fields Correctly");
            
            else{
                BookShop.write(new Books(titleInput,Double.parseDouble(priceInput),authorInput));  
                title.clear();
                Author.clear();
                Price.clear();
            }   
        });
        
        back.setOnAction((ActionEvent e) -> {
            OwnerScreen(PrimaryStage);
        });
        
        deleteBook.setOnAction((ActionEvent e) -> {
            books = bookTable.getItems();
            selectBooks = bookTable.getSelectionModel().getSelectedItems();
            BookShop.delete( selectBooks.get(0));
        });
        
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(title,Author,Price,addBook);
        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(bookTable,hBox,deleteBook,back);
        vBox.setPadding(new Insets(35, 35, 35, 35));
        vBox.setSpacing(10);
        vBox.setStyle("-fx-background-color: " + toCssString(gradient));
        Scene scene = new Scene(vBox);
        PrimaryStage.setScene(scene);
        PrimaryStage.show();
      
    }
    
    
    public void CustomerScreen(Stage primaryStage, String username){
        primaryStage.setTitle("Customer Login Screen");
        Owner customer = new Owner("customers.txt");
        customers=customer.getUsers();
        
        for(int i =0;i<customers.size();i++){
            if(customers.get(i).getUsername().equals(username))
                currentCustomer=customers.get(i);  
        }
        Text title = new Text("Welcome, "+currentCustomer.getUsername());
        Text rank = new Text("Current Rank: "+currentCustomer.getStatus());
        Text points = new Text("Points: "+ Integer.toString(currentCustomer.getPoints()));
        
        Color color1 = Color.rgb(210, 180, 140); // Light Brown
        Color color2 = Color.rgb(188, 143, 143); // Rosy Brown
        LinearGradient gradient = new LinearGradient(
            0, 0, 0, 1,
            true, CycleMethod.NO_CYCLE,
            new Stop(0, color1),
            new Stop(1, color2));
        
        Button Browse = new Button("Browse Books");
        Button Logout = new Button ("Logout");
        Browse.setMinSize(300, 100);
        Logout.setMinSize(300, 100);
        Browse.setStyle("-fx-background-color: #eab676;");
        Logout.setStyle("-fx-background-color: #eab676;");
        title.setFont(Font.font("Monospaced", FontWeight.BOLD, 30));
        rank.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
        points.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
        
        Logout.setOnAction((ActionEvent e) -> {
            start(primaryStage);
        });
        
        Browse.setOnAction((ActionEvent e) -> {
            browseBooks(primaryStage);

        });
        
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: " + toCssString(gradient));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        HBox butt= new HBox();
        butt.setSpacing(10);
        butt.getChildren().addAll(Browse, Logout);
        grid.add(title,0,2,3,1);
        grid.add(rank,0,3,3,1);
        grid.add(points,0,4, 3, 1);
        grid.add(Browse,0,5,3,1);
        grid.add(Logout,0,6,3,1);
        grid.add(butt,0,4, 3, 1);
        Scene scene=new Scene(grid,700,500);
        primaryStage.setScene(scene);
        primaryStage.show();  
    }
    
    public void browseBooks(Stage primaryStage){
        primaryStage.setTitle("Browse");
        BookShop book = new BookShop("books.txt");
        Color color1 = Color.rgb(210, 180, 140); // Light Brown
        Color color2 = Color.rgb(188, 143, 143); // Rosy Brown
        LinearGradient gradient = new LinearGradient(
            0, 0, 0, 1,
            true, CycleMethod.NO_CYCLE,
            new Stop(0, color1),
            new Stop(1, color2));
        
        Button buy = new Button ("Buy");
        Button buyP = new Button("Buy With Points");
        Button back = new Button("Back");
        
        
        
        
         
        Text title = new Text("Book Catalogue");
        
        TableView<Books> bookTable;
        
         //Username Column
        TableColumn<Books, String> TitleColumn = new TableColumn<>("Title");
        TitleColumn.setMinWidth(200);
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));

        //Password Column
        TableColumn<Books, String> AuthColumn = new TableColumn<>("Author");
        AuthColumn.setMinWidth(200);
        AuthColumn.setCellValueFactory(new PropertyValueFactory<>("Author"));

        //Points Column
        TableColumn<Books, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
        TableColumn<Books, Boolean> selectColumn = new TableColumn<>("Select");
        selectColumn.setMinWidth(100);
        selectColumn.setCellValueFactory(new PropertyValueFactory<>("Check"));

        bookTable = new TableView<>();
        try {
            bookTable.setItems(book.read());
        } catch (IOException ex) {
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        bookTable.getColumns().addAll(TitleColumn, AuthColumn, priceColumn,selectColumn);
        
        books = bookTable.getItems();
        
        
            
        
        back.setOnAction((ActionEvent e) -> {
            CustomerScreen(primaryStage,currentCustomer.getUsername());
        });
        
        
        buy.setOnAction((ActionEvent e) -> {
            double transactionCost = 0;
            ObservableList<Books> Books = bookTable.getItems();
            selectBooks = currentCustomer.selectedBooks(Books);
            
        for(int i =0;i<selectBooks.size();i++)
            transactionCost=+selectBooks.get(i).getPrice();
        buyBook(primaryStage,true,transactionCost);
        });
        
        
        buyP.setOnAction((ActionEvent e) -> {
            double transactionCost = 0;
        for(int i =0;i<selectBooks.size();i++)
            transactionCost=+selectBooks.get(i).getPrice();
        buyBook(primaryStage,false,transactionCost);
          
        });
         
        
        HBox butt= new HBox();
        butt.setSpacing(10);
        butt.getChildren().addAll(buy,buyP,back);
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: " + toCssString(gradient));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(bookTable,0,0,10,1);
        grid.add(butt,0,1,10,1);
        Scene scene=new Scene(grid,700,500);
        primaryStage.setScene(scene);
        primaryStage.show();  
        
    }
    
    public void buyBook(Stage primaryStage, boolean value, double TC){
        BookShop bookshop = new BookShop("books.txt");
        primaryStage.setTitle("Checkout");
        Text title = new Text("Checkout");
        title.setFont(Font.font("Monospaced", FontWeight.BOLD, 30));
        Owner customer = new Owner("customers.txt");
        int oldPoints=currentCustomer.getPoints();
        
      
        
        if(value == true){
            Color color1 = Color.rgb(210, 180, 140); // Light Brown
            Color color2 = Color.rgb(188, 143, 143); // Rosy Brown
            LinearGradient gradient = new LinearGradient(
            0, 0, 0, 1,
            true, CycleMethod.NO_CYCLE,
            new Stop(0, color1),
            new Stop(1, color2));
            Text message = new Text("You Are About to Cash Out With Your Payment Method");
            Text cost = new Text("Transaction Cost: "+Double.toString(TC));
            Button confirm = new Button("Confirm");
            Button back = new Button("Back");
            
            VBox box = new VBox();
            HBox hbox= new HBox();
            hbox.setSpacing(10);
            hbox.getChildren().addAll(confirm,back);
            
            
            
            
            confirm.setOnAction((ActionEvent e) -> {
                currentCustomer.PayMoney(TC);
                showMessage("Puchase Confirmed Succesfully\n"+"You currently have: "+currentCustomer.getPoints()+" points");
                customer.editUser(currentCustomer.getUsername()+", "+currentCustomer.getPassword()+", "+ oldPoints,currentCustomer.getUsername()+", "+currentCustomer.getPassword()+", "+ currentCustomer.getPoints());
                browseBooks(primaryStage);
            });
            
            back.setOnAction((ActionEvent e) -> {
                browseBooks(primaryStage);
            });
            
            box.getChildren().addAll(message,cost,hbox);
            box.setPadding(new Insets(35, 35, 35, 35));
            box.setSpacing(10);
            box.setStyle("-fx-background-color: " + toCssString(gradient));
            Scene scene = new Scene(box);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        
        else{
            Color color1 = Color.rgb(210, 180, 140); // Light Brown
            Color color2 = Color.rgb(188, 143, 143); // Rosy Brown
            LinearGradient gradient = new LinearGradient(
            0, 0, 0, 1,
            true, CycleMethod.NO_CYCLE,
            new Stop(0, color1),
            new Stop(1, color2));
            Text messag = new Text("You Are About to Cash Out With Your Points");
            Text cos = new Text("Transaction Cost Before Points: "+Double.toString(TC));
            Button confir = new Button("Confirm");
            Button bac = new Button("Back");
            
            VBox box = new VBox();
            HBox hbox= new HBox();
            hbox.setSpacing(10);
            hbox.getChildren().addAll(confir,bac);
            
            
            
            
            confir.setOnAction((ActionEvent e) -> {
                showMessage("Puchase Confirmed Succesfully\n"+"Transaction Cost:"+currentCustomer.PayPoints(TC)+"\nYou currently have: "+currentCustomer.getPoints()+" points");
                customer.editUser(currentCustomer.getUsername()+", "+currentCustomer.getPassword()+", "+ oldPoints,currentCustomer.getUsername()+", "+currentCustomer.getPassword()+", "+ currentCustomer.getPoints());
                browseBooks(primaryStage);
            });
            
            bac.setOnAction((ActionEvent e) -> {
                browseBooks(primaryStage);
            });
            
            box.getChildren().addAll(messag,cos,hbox);
            box.setPadding(new Insets(35, 35, 35, 35));
            box.setSpacing(10);
            box.setStyle("-fx-background-color: " + toCssString(gradient));
            Scene scene = new Scene(box);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        
        
    }
    
        private String toCssString(LinearGradient gradient) {
        StringBuilder cssString = new StringBuilder("linear-gradient(to bottom, ");
        for (Stop stop : gradient.getStops()) {
            cssString.append(toCssString(stop.getColor())).append(" ").append(stop.getOffset() * 100).append("%, ");
        }
        cssString.delete(cssString.length() - 2, cssString.length()); // Remove the trailing comma and space
        cssString.append(");");
        return cssString.toString();
    }

    // Utility method to convert Color object to CSS string
    private String toCssString(Color color) {
         return String.format("rgba(%d, %d, %d, %f)",
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255),
            color.getOpacity());
    }
    
    private void showAlert(String x){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(x);
        
        alert.showAndWait();
    }
    private void showMessage(String x){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(x);
        
        alert.showAndWait();
    }
    
        private boolean isInteger(String pointsinput) {
        try{
        Integer.parseInt(pointsinput);
        return true;
        }
        catch(NumberFormatException e){
            return false;  
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    }

  
    
