package ChatBotProject.controllers;

import ChatBotProject.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class HealthController {

    @FXML
    private TextArea resultArea;

    private UserService service = new UserService();
    private int userId;

    public void setUserId(int userId){

        this.userId = userId;
    }

    @FXML
    private void showBMI(){

        double imc =
                service.calculateIMC(userId);

        resultArea.setText(
                String.format(
                        "IMC: %.2f",
                        imc
                )
        );
    }

    @FXML
    private void showHistory(){

        resultArea.setText(
                service.showWeightHistory(userId)
        );
    }

    @FXML
    private void showProgress(){

        resultArea.setText(
                service.showProgress(userId)
        );
    }
}