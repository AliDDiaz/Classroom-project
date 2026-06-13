package ChatBotProject.controllers;

import ChatBotProject.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField fieldId;
    @FXML private Label labelStatus;

    private final UserService service = new UserService();

    @FXML
    private void handleLogin(){

        try{

            int id = Integer.parseInt(fieldId.getText().trim());

            if(service.login(id)){

                openMainView();

            }else{

                labelStatus.setText("Usuario no encontrado. Verifica tu ID.");
            }

        }catch(NumberFormatException e){

            labelStatus.setText("Ingresa un ID numérico válido.");
        }
    }

    @FXML
    private void handleRegister(){

        openMainView();
    }

    private void openMainView(){

        try{

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/ChatBotProject/views/MainView.fxml"
                    )
            );

            Parent root = loader.load();

            Stage stage = new Stage();

            stage.setTitle("Entrenador Personal - ChatBot");

            stage.setScene(new Scene(root, 800, 600));

            stage.show();

            // Cierra la ventana de login
            Stage loginStage = (Stage) fieldId.getScene().getWindow();

            loginStage.close();

        }catch(Exception e){

            e.printStackTrace();

            labelStatus.setText("No fue posible abrir la aplicación.");
        }
    }
}