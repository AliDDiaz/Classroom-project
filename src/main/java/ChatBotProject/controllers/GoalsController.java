package ChatBotProject.controllers;

import ChatBotProject.entities.*;
import ChatBotProject.service.UserService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class GoalsController {

    @FXML private Label labelUser;
    @FXML private Label labelStatus;
    @FXML private ComboBox<String> comboMainGoal;
    @FXML private CheckBox checkStress;
    @FXML private CheckBox checkSleep;
    @FXML private CheckBox checkEnergy;
    @FXML private CheckBox checkHabits;
    @FXML private TextArea resultArea;

    private final UserService service = new UserService();
    private int userId;

    @FXML
    public void initialize(){

        comboMainGoal.setItems(
                FXCollections.observableArrayList(
                        "Perder peso",
                        "Ganar músculo",
                        "Mantenerse en forma",
                        "Mejorar la resistencia",
                        "Aumentar la flexibilidad"
                )
        );
    }

    public void setUserId(int userId){

        this.userId = userId;

        labelUser.setText("Usuario #" + userId);

        showCurrentGoals();
    }

    @FXML
    private void assignMainGoal(){

        User user = service.findUser(userId);

        if(user == null){
            setStatus("Usuario no encontrado.");
            return;
        }

        String goal = comboMainGoal.getValue();

        if(goal == null){
            setStatus("Selecciona un objetivo principal.");
            return;
        }

        service.assignMainGoal(userId, goal);

        showCurrentGoals();

        setStatus("Objetivo principal asignado correctamente.");
    }

    @FXML
    private void addSecondaryGoals(){

        User user = service.findUser(userId);

        if(user == null){
            setStatus("Usuario no encontrado.");
            return;
        }

        boolean anySelected = false;

        if(checkStress.isSelected()){
            service.addSecondaryGoal(userId, new StressReductionGoal());
            anySelected = true;
        }

        if(checkSleep.isSelected()){
            service.addSecondaryGoal(userId, new SleepImprovementGoal());
            anySelected = true;
        }

        if(checkEnergy.isSelected()){
            service.addSecondaryGoal(userId, new EnergyBoostGoal());
            anySelected = true;
        }

        if(checkHabits.isSelected()){
            service.addSecondaryGoal(userId, new HealthyHabitsGoal());
            anySelected = true;
        }

        if(!anySelected){
            setStatus("Selecciona al menos un objetivo secundario.");
            return;
        }

        clearCheckBoxes();
        showCurrentGoals();
        setStatus("Objetivos secundarios actualizados.");
    }

    @FXML
    private void showCurrentGoals(){

        User user = service.findUser(userId);

        if(user == null){
            resultArea.setText("Usuario no encontrado.");
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("===== OBJETIVOS ACTUALES =====\n");

        String mainGoal = user.getMainGoal();

        if(mainGoal == null || mainGoal.isEmpty()){
            sb.append("Objetivo principal: (sin asignar)\n");
        }else{
            sb.append("Objetivo principal: ").append(mainGoal).append("\n");
        }

        sb.append("\nObjetivos secundarios:\n");

        if(user.getSecondaryGoals().isEmpty()){
            sb.append("(sin objetivos secundarios)\n");
        }else{
            for(SecondaryGoals goal : user.getSecondaryGoals()){
                sb.append("- ").append(goal.getName()).append("\n");
            }
        }

        resultArea.setText(sb.toString());
    }

    @FXML
    private void showRoutine(){

        User user = service.findUser(userId);

        if(user == null){
            resultArea.setText("Usuario no encontrado.");
            return;
        }

        if(user.getMainGoal() == null || user.getMainGoal().isEmpty()){
            resultArea.setText("No has asignado un objetivo principal.");
            return;
        }

        resultArea.setText(service.generateRoutine(userId));
    }

    private void clearCheckBoxes(){
        checkStress.setSelected(false);
        checkSleep.setSelected(false);
        checkEnergy.setSelected(false);
        checkHabits.setSelected(false);
    }

    private void setStatus(String message){
        labelStatus.setText(message);
    }
}