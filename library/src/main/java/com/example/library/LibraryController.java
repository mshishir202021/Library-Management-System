package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LibraryController {

    @FXML
    private Button close;

    @FXML
    private Button loginBtn;

    @FXML
    private Button minimize;

    @FXML
    private PasswordField password;

    @FXML
    private TextField studentNumber;


   //database
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private double x = 0;
    private double y = 0;




    public LibraryController() {
    }

    @FXML
    public void exit() {
    System.exit(0);
    }

    @FXML
    public void login() {
        String sql = "SELECT * FROM student WHERE studentNumber = ? and password = ?";
        connect = Database.connectDB();
        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, studentNumber.getText());
            prepare.setString(2, password.getText());
            result = prepare.executeQuery();

            Alert alert;

            if(studentNumber.getText().isEmpty() || password.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message:");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields.");
                alert.showAndWait();
            }else{
                if(result.next()){

                    getData.studentNumber = studentNumber.getText();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Admin Message:");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Logged in!");
                    alert.showAndWait();

                    loginBtn.getScene().getWindow().hide();



                    //dashboard

                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    root.setOnMousePressed((MouseEvent event) ->{

                        x = event.getSceneX();
                        y = event.getSceneY();

                    });

                    root.setOnMouseDragged((MouseEvent event) ->{

                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);

                    });

                    stage.initStyle(StageStyle.TRANSPARENT);





                    stage.setScene(scene);
                    stage.show();



                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Admin Message:");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong user id or password");
                    alert.showAndWait();

                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void minimize(ActionEvent event) {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);

    }

    @FXML

    void numbersOnly(KeyEvent e) {
        if(e.getCharacter().matches("[^\\e\t\r\\d+$]")){
            e.consume();
            studentNumber.setStyle("-fx-border-color:#e04040");
        }else{
            studentNumber.setStyle("-fx-border-color:#fff");
        }

    }

}
