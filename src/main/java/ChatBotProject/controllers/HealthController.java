package ChatBotProject.controllers;

import ChatBotProject.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HealthController {

    @FXML
    private TextArea resultArea;

    @FXML
    private TextField fieldTargetWeight;

    @FXML
    private Label labelStatus;

    private UserService service = new UserService();
    private int userId;

    public void setUserId(int userId){

        this.userId = userId;
    }

    @FXML
    private void showBMI(){

        double imc =
                service.calculateIMC(userId);

        String result =
                "===== IMC =====\n"
                        + String.format("IMC: %.2f\n\n", imc)
                        + service.bmiRecommendation(userId);

        resultArea.setText(result);
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

    @FXML
    private void showCalories(){

        double calories =
                service.calculateCalories(userId);

        String result =
                "===== CALORÍAS =====\n"
                        + String.format("Calorías diarias estimadas: %.2f kcal\n\n", calories)
                        + service.caloriesRecommendation(userId);

        resultArea.setText(result);
    }

    @FXML
    private void configureTargetWeight(){

        try{

            double targetWeight =
                    Double.parseDouble(
                            fieldTargetWeight.getText().trim()
                    );

            if(targetWeight <= 0){

                setStatus("La meta debe ser mayor que cero.");
                return;
            }

            service.setTargetWeight(userId, targetWeight);

            fieldTargetWeight.clear();

            setStatus("Meta de peso configurada correctamente.");

        }catch(NumberFormatException e){

            setStatus("Ingrese un peso válido.");
        }
    }

    @FXML
    private void showGoalProgress(){

        resultArea.setText(
                service.showGoalProgress(userId)
        );
    }

    private void setStatus(String message){

        if(labelStatus != null){
            labelStatus.setText(message);
        }
    }
}