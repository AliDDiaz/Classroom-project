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
    @FXML private TextField fieldAdminPassword;

    private final UserService service = new UserService();

    @FXML
    private void handleLogin(){

        try{

            if(fieldId.getText().trim().isEmpty()){

                labelStatus.setText(
                        "Ingrese un ID."
                );

                return;
            }

            int id = Integer.parseInt(fieldId.getText().trim());

            if(service.login(id)){

                openUserDashboard(id);

            }else{

                labelStatus.setText("Usuario no encontrado. Verifica tu ID.");
            }

        }catch(NumberFormatException e){

            labelStatus.setText("Ingresa un ID numérico válido.");
        }
    }

    @FXML
    private void handleAdminLogin(){

        try{

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/ChatBotProject/views/AdminLoginView.fxml"
                            )
                    );

            Parent root = loader.load();

            Stage stage = new Stage();

            stage.setTitle("Login Administrador");

            stage.setScene(
                    new Scene(root,300,200)
            );

            stage.show();

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    private void openAdminPanel(){

        try{

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/ChatBotProject/views/MainView.fxml"
                            )
                    );

            Parent root = loader.load();

            Stage stage = new Stage();

            stage.setTitle(
                    "Panel Administrativo"
            );

            stage.setScene(
                    new Scene(root,800,600)
            );

            stage.show();

            Stage loginStage =
                    (Stage) fieldId.getScene().getWindow();

            loginStage.close();

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegister(){

        try{

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/ChatBotProject/views/RegisterView.fxml"
                            )
                    );

            Parent root = loader.load();

            Stage stage = new Stage();

            stage.setTitle(
                    "Registro"
            );

            stage.setScene(
                    new Scene(root)
            );

            stage.show();

        }catch(Exception e){

            e.printStackTrace();
        }
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

    private void openUserDashboard(int userId){

        try{

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/ChatBotProject/views/UserDashboard.fxml"
                            )
                    );

            Parent root = loader.load();

            UserDashboardController controller =
                    loader.getController();

            controller.setUserId(userId);

            Stage stage = new Stage();

            stage.setTitle("Panel Principal");

            stage.setScene(
                    new Scene(root)
            );

            stage.show();

            Stage loginStage =
                    (Stage) fieldId.getScene().getWindow();

            loginStage.close();

        }catch(Exception e){

            e.printStackTrace();

            labelStatus.setText(
                    "No fue posible abrir el panel."
            );
        }
    }
}