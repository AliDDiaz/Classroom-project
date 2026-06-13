package ChatBotProject.controllers;

import ChatBotProject.entities.User;
import ChatBotProject.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
                    fieldName.getText();

            int age =
                    Integer.parseInt(
                            fieldAge.getText()
                    );

            double weight =
                    Double.parseDouble(
                            fieldWeight.getText()
                    );

            double height =
                    Double.parseDouble(
                            fieldHeight.getText()
                    );

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
                    "Cuenta creada. Tu ID es: "
                            + id
            );

        }catch(Exception e){

            statusLabel.setText(
                    "Datos inválidos."
            );
        }
    }
}