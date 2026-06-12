package ChatBotProject.controllers;

import ChatBotProject.entities.Habit;
import ChatBotProject.entities.User;
import ChatBotProject.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class MainController {

    // ── Tabla ──────────────────────────────────────────────────────────────
    @FXML private TableView<User>           userTable;
    @FXML private TableColumn<User, Integer> colId;
    @FXML private TableColumn<User, String>  colName;
    @FXML private TableColumn<User, Integer> colAge;
    @FXML private TableColumn<User, Double>  colWeight;
    @FXML private TableColumn<User, Double>  colHeight;
    @FXML private TableColumn<User, String>  colGender;
    @FXML private TableColumn<User, String>  colGoal;

    // ── Campos del formulario ──────────────────────────────────────────────
    @FXML private TextField fieldId;
    @FXML private TextField fieldName;
    @FXML private TextField fieldAge;
    @FXML private TextField fieldWeight;
    @FXML private TextField fieldHeight;
    @FXML private TextField fieldGender;

    // ── Botones y estado ──────────────────────────────────────────────────
    @FXML private Label labelStatus;

    // ── Lógica ────────────────────────────────────────────────────────────
    private final UserService service = new UserService();
    private final ObservableList<User> tableData = FXCollections.observableArrayList();

    // Se ejecuta automáticamente al cargar el FXML
    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colGoal.setCellValueFactory(new PropertyValueFactory<>("mainGoal"));

        userTable.setItems(tableData);

        // Al hacer clic en una fila, llenar el formulario automáticamente
        userTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) fillForm(newVal);
                }
        );

        refreshTable();
        setStatus("Listo.");
    }

    // ── CRUD ──────────────────────────────────────────────────────────────

    @FXML
    private void handleCreate() {
        try {
            int id         = Integer.parseInt(fieldId.getText().trim());
            String name    = fieldName.getText().trim();
            int age        = Integer.parseInt(fieldAge.getText().trim());
            double weight  = Double.parseDouble(fieldWeight.getText().trim());
            double height  = Double.parseDouble(fieldHeight.getText().trim());
            String gender  = fieldGender.getText().trim();

            ArrayList<Double> history = new ArrayList<>();
            history.add(weight);
            ArrayList<Habit> habits = new ArrayList<>();

            User user = new User(id, name, age, weight, height, gender,
                    "", new ArrayList<>(), history, habits, 0);

            if (service.registerUser(user)) {
                refreshTable();
                handleClear();
                setStatus("Usuario " + name + " registrado correctamente.");
            } else {
                setStatus("Error: ID ya existe o datos inválidos.");
            }

        } catch (NumberFormatException e) {
            setStatus("Error: verifica que ID, edad, peso y altura sean números.");
        }
    }

    @FXML
    private void handleDelete() {
        try {
            int id = Integer.parseInt(fieldId.getText().trim());
            if (service.deleteUser(id)) {
                refreshTable();
                handleClear();
                setStatus("Usuario #" + id + " eliminado.");
            } else {
                setStatus("No existe un usuario con ID " + id + ".");
            }
        } catch (NumberFormatException e) {
            setStatus("Error: ingresa un ID numérico.");
        }
    }

    @FXML
    private void handleUpdate() {
        try {
            int id        = Integer.parseInt(fieldId.getText().trim());
            double weight = Double.parseDouble(fieldWeight.getText().trim());
            service.updateWeight(weight, id);
            refreshTable();
            setStatus("Peso actualizado para usuario #" + id + ".");
        } catch (NumberFormatException e) {
            setStatus("Error: ID y peso deben ser numéricos.");
        }
    }

    @FXML
    private void handleSearch() {
        try {
            int id   = Integer.parseInt(fieldId.getText().trim());
            User user = service.findUser(id);
            if (user != null) {
                fillForm(user);
                userTable.getSelectionModel().select(user);
                setStatus("Usuario encontrado: " + user.getName());
            } else {
                setStatus("No se encontró un usuario con ID " + id + ".");
            }
        } catch (NumberFormatException e) {
            setStatus("Error: ingresa un ID numérico.");
        }
    }

    @FXML
    private void handleClear() {
        fieldId.clear();
        fieldName.clear();
        fieldAge.clear();
        fieldWeight.clear();
        fieldHeight.clear();
        fieldGender.clear();
        userTable.getSelectionModel().clearSelection();
        setStatus("Campos limpiados.");
    }

    // ── Utilidades ────────────────────────────────────────────────────────

    private void refreshTable() {
        tableData.setAll(service.getAllUser());
    }

    private void fillForm(User user) {
        fieldId.setText(String.valueOf(user.getId()));
        fieldName.setText(user.getName());
        fieldAge.setText(String.valueOf(user.getAge()));
        fieldWeight.setText(String.valueOf(user.getWeight()));
        fieldHeight.setText(String.valueOf(user.getHeight()));
        fieldGender.setText(user.getGender());
    }

    private void setStatus(String msg) {
        labelStatus.setText(msg);
    }
}