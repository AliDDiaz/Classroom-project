package ChatBotProject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class AdminLoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    private static final String ADMIN_PASSWORD = "FitBot2026";

    @FXML
    private void loginAdmin(){

        if(passwordField.getText().equals(ADMIN_PASSWORD)){

            try{

                FXMLLoader loader =
                        new FXMLLoader(
                                getClass().getResource(
                                        "/ChatBotProject/views/MainView.fxml"
                                )
                        );

                Parent root = loader.load();

                Stage stage = new Stage();

                stage.setTitle("Panel Administrativo");

                stage.setScene(
                        new Scene(root,800,600)
                );

                stage.show();

                ((Stage)passwordField
                        .getScene()
                        .getWindow())
                        .close();

            }catch(Exception e){

                e.printStackTrace();
            }

        }else{

            statusLabel.setText(
                    "Contraseña incorrecta."
            );
        }
    }
}