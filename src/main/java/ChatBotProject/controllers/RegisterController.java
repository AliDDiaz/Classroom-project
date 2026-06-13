package ChatBotProject.controllers;

import ChatBotProject.entities.User;
import ChatBotProject.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

import java.util.ArrayList;

public class RegisterController {

    @FXML private TextField fieldName;
    @FXML private TextField fieldAge;
    @FXML private TextField fieldWeight;
    @FXML private TextField fieldHeight;
    @FXML private ComboBox<String> fieldGender;
    @FXML private Label statusLabel;

    private final UserService service =
            new UserService();

    @FXML
    public void initialize(){

        fieldGender.getItems().addAll(
                "Masculino",
                "Femenino"
        );
    }

    @FXML
    private void register(){

        try{

            int id =
                    service.generateNextId();

            String name =
                    fieldName.getText().trim();

            if(!name.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")){

                showError(
                        "El nombre solo puede contener letras."
                );

                return;
            }

            if(name.isEmpty()){

                statusLabel.setText(
                        "Ingrese un nombre."
                );

                return;
            }

            int age =
                    Integer.parseInt(
                            fieldAge.getText().trim()
                    );

            if(age < 10 || age > 120){

                statusLabel.setText(
                        "Edad fuera de rango."
                );

                return;
            }

            double weight =
                    Double.parseDouble(
                            fieldWeight.getText().trim()
                    );

            if(weight <= 0){

                statusLabel.setText(
                        "Peso inválido."
                );

                return;
            }

            double height =
                    Double.parseDouble(
                            fieldHeight.getText().trim()
                    );

            if(height <= 0){

                statusLabel.setText(
                        "Altura inválida."
                );

                return;
            }

            if(fieldGender.getValue() == null){

                statusLabel.setText(
                        "Seleccione un género."
                );

                return;
            }

            User user =
                    new User(
                            id,
                            name,
                            age,
                            weight,
                            height,
                            fieldGender.getValue(),
                            "",
                            new ArrayList<>(),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            0
                    );

            service.registerUser(user);

            statusLabel.setText(
                    "Cuenta creada. Tu ID es: " + id
            );

            fieldName.clear();
            fieldAge.clear();
            fieldWeight.clear();
            fieldHeight.clear();
            fieldGender.setValue(null);

        }catch(NumberFormatException e){

            statusLabel.setText(
                    "Edad, peso y altura deben ser números."
            );

        }catch(Exception e){

            statusLabel.setText(
                    e.getMessage()
            );
        }
    }

    private void showError(String message){

        Alert alert = new Alert(
                Alert.AlertType.ERROR
        );

        alert.setTitle("Error");
        alert.setHeaderText("Datos inválidos");
        alert.setContentText(message);

        alert.showAndWait();
    }

    private void showSuccess(String message){

        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setTitle("Éxito");
        alert.setHeaderText("Cuenta creada");
        alert.setContentText(message);

        alert.showAndWait();
    }
}