package ChatBotProject.controllers;

import ChatBotProject.entities.Habit;
import ChatBotProject.entities.User;
import ChatBotProject.service.UserService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class HabitsController {

    @FXML private Label labelUser;
    @FXML private Label labelStatus;
    @FXML private TextField fieldHabitName;
    @FXML private ComboBox<String> comboCategory;
    @FXML private DatePicker dateHabit;
    @FXML private CheckBox checkCompleted;
    @FXML private TextField fieldHabitId;
    @FXML private TextArea resultArea;

    private final UserService service = new UserService();
    private int userId;

    @FXML
    public void initialize(){
        comboCategory.setItems(
                FXCollections.observableArrayList(
                        "Ejercicio",
                        "Alimentacion",
                        "Descanso"
                )
        );

        comboCategory.getSelectionModel().selectFirst();
        dateHabit.setValue(LocalDate.now());
    }

    public void setUserId(int userId){
        this.userId = userId;
        labelUser.setText("Usuario #" + userId);
        showHabits();
    }

    @FXML
    private void registerHabit(){
        User user = service.findUser(userId);

        if(user == null){
            setStatus("Usuario no encontrado.");
            return;
        }

        String name = fieldHabitName.getText().trim();
        String category = comboCategory.getValue();
        LocalDate date = dateHabit.getValue();

        if(name.isEmpty()){
            setStatus("Ingrese el nombre del habito.");
            return;
        }

        if(category == null || category.isEmpty()){
            setStatus("Seleccione una categoria.");
            return;
        }

        if(date == null){
            setStatus("Seleccione una fecha.");
            return;
        }

        Habit habit = new Habit(
                service.generateHabitId(userId),
                userId,
                name,
                category,
                checkCompleted.isSelected(),
                date
        );

        service.addHabit(userId, habit);
        clearRegisterFields();
        showHabits();
        setStatus("Habito registrado correctamente.");
    }

    @FXML
    private void completeHabit(){
        Integer habitId = readHabitId();

        if(habitId == null){
            return;
        }

        if(findHabit(habitId) == null){
            setStatus("No existe un habito con ese ID.");
            return;
        }

        service.completeHabit(userId, habitId);
        showHabits();
        setStatus("Habito marcado como completado.");
    }

    @FXML
    private void deleteHabit(){
        Integer habitId = readHabitId();

        if(habitId == null){
            return;
        }

        if(findHabit(habitId) == null){
            setStatus("No existe un habito con ese ID.");
            return;
        }

        service.deleteHabit(userId, habitId);
        fieldHabitId.clear();
        showHabits();
        setStatus("Habito eliminado.");
    }

    @FXML
    private void showHabits(){
        resultArea.setText(
                service.showHabits(userId)
        );
    }

    @FXML
    private void showStreak(){
        resultArea.setText(
                service.calculateStreak(userId)
        );
    }

    @FXML
    private void showStatistics(){
        resultArea.setText(
                service.habitsStatistics(userId)
        );
    }

    private Integer readHabitId(){
        try{
            int habitId = Integer.parseInt(
                    fieldHabitId.getText().trim()
            );

            if(habitId <= 0){
                setStatus("Ingrese un ID mayor que cero.");
                return null;
            }

            return habitId;

        }catch(NumberFormatException e){
            setStatus("Ingrese un ID de habito valido.");
            return null;
        }
    }

    private Habit findHabit(int habitId){
        User user = service.findUser(userId);

        if(user == null){
            return null;
        }

        for(Habit habit : user.getHabits()){
            if(habit.getId() == habitId){
                return habit;
            }
        }

        return null;
    }

    private void clearRegisterFields(){
        fieldHabitName.clear();
        comboCategory.getSelectionModel().selectFirst();
        dateHabit.setValue(LocalDate.now());
        checkCompleted.setSelected(false);
    }

    private void setStatus(String message){
        labelStatus.setText(message);
    }
}
