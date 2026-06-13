package ChatBotProject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ChatBotProject.entities.User;
import ChatBotProject.service.UserService;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UserDashboardController {

    private final UserService service = new UserService();

    @FXML
    private Label labelWelcome;

    private int userId;
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField inputField;

    @FXML
    private void showWelcomeMessage(){

        User user = service.findUser(userId);

        chatArea.setText(
                "🤖 Hola " + user.getName() + "\n\n" +
                        "Soy FitBot, tu entrenador personal virtual.\n\n" +
                        "Puedo ayudarte a:\n" +
                        "- Calcular tu IMC\n" +
                        "- Generar rutinas\n" +
                        "- Gestionar hábitos\n" +
                        "- Controlar tu progreso\n" +
                        "- Configurar metas de peso\n"
        );
    }

    public void setUserId(int userId){

        this.userId = userId;

        User user = service.findUser(userId);

        if(user != null){

            labelWelcome.setText(
                    "Bienvenido " + user.getName()
            );

            chatArea.setText(
                    "🤖 Hola " + user.getName() + "\n\n" +
                            "Soy FitBot, tu entrenador personal virtual.\n\n" +
                            "¿Qué deseas hacer hoy?"
            );

        }else{

            labelWelcome.setText(
                    "Bienvenido Usuario"
            );
        }
    }

    @FXML
    private void openHealth(){

        try{

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/ChatBotProject/views/HealthView.fxml"
                            )
                    );

            Parent root = loader.load();

            HealthController controller =
                    loader.getController();

            controller.setUserId(userId);

            Stage stage = new Stage();

            stage.setTitle("Salud");

            stage.setScene(new Scene(root));

            stage.show();

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    @FXML
    private void openHabits(){

        try{

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/ChatBotProject/views/HabitsView.fxml"
                            )
                    );

            Parent root = loader.load();

            HabitsController controller =
                    loader.getController();

            controller.setUserId(userId);

            Stage stage = new Stage();

            stage.setTitle("Hábitos");

            stage.setScene(new Scene(root));

            stage.show();

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    @FXML
    private void openGoals(){

        try{

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/ChatBotProject/views/GoalsView.fxml"
                            )
                    );

            Parent root = loader.load();

            GoalsController controller =
                    loader.getController();

            controller.setUserId(userId);

            Stage stage = new Stage();

            stage.setTitle("Objetivos");

            stage.setScene(new Scene(root));

            stage.show();

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    @FXML
    private void sendMessage(){

        String message =
                inputField.getText().trim().toLowerCase();

        if(message.isEmpty()){
            return;
        }

        String response;

        if(
                message.contains("hola")
                        || message.contains("buenas")
                        || message.contains("hey")
                        || message.contains("saludos")
        ){

            User user = service.findUser(userId);

            response =
                    "Hola " + user.getName() +
                            " 👋\n\n" +
                            "¿Cómo puedo ayudarte hoy?";

        }
        else if(
                message.contains("gracias")
                        || message.contains("muchas gracias")
        ){

            response =
                    "¡Con gusto! 💪\n\n" +
                            "Estoy aquí para ayudarte a alcanzar tus objetivos.";

        }
        else if(
                message.contains("adios")
                        || message.contains("hasta luego")
                        || message.contains("nos vemos")
        ){

            response =
                    "Hasta luego 👋\n\n" +
                            "Recuerda mantener hábitos saludables.";

        }
        else if(
                message.contains("quien soy")
                        || message.contains("mis datos")
                        || message.contains("mi informacion")
        ){

            User user = service.findUser(userId);

            response =
                    "📋 Tus datos actuales:\n\n" +
                            "Nombre: " + user.getName() + "\n" +
                            "Edad: " + user.getAge() + "\n" +
                            "Peso: " + user.getWeight() + " kg\n" +
                            "Altura: " + user.getHeight() + " m\n" +
                            "Género: " + user.getGender();

        }
        else if(
                message.contains("ayuda")
                        || message.contains("que puedes hacer")
        ){

            response =
                    "🤖 Puedo ayudarte con:\n\n" +
                            "• Calcular IMC\n" +
                            "• Calcular calorías\n" +
                            "• Mostrar progreso\n" +
                            "• Mostrar historial de peso\n" +
                            "• Generar rutinas\n" +
                            "• Consultar metas\n" +
                            "• Ver tus datos";

        }
        else if(
                message.contains("imc")
                        || message.contains("indice de masa corporal")
                        || message.contains("peso ideal")
        ){

            response =
                    String.format(
                            "📊 Tu IMC es %.2f\n\n%s",
                            service.calculateIMC(userId),
                            service.bmiRecommendation(userId)
                    );

        }
        else if(
                message.contains("caloria")
                        || message.contains("calorias")
                        || message.contains("alimentacion")
                        || message.contains("comer")
                        || message.contains("consumir")
        ){

            response =
                    "🔥 " +
                            service.caloriesRecommendation(userId);

        }
        else if(
                message.contains("historial")
                        || message.contains("peso anterior")
                        || message.contains("pesos")
        ){

            response =
                    service.showWeightHistory(userId);

        }
        else if(
                message.contains("progreso")
                        || message.contains("avance")
                        || message.contains("evolucion")
        ){

            response =
                    service.showProgress(userId);

        }
        else if(
                message.contains("rutina")
                        || message.contains("ejercicio")
                        || message.contains("entrenamiento")
        ){

            response =
                    service.generateRoutine(userId);

        }
        else if(
                message.contains("meta")
                        || message.contains("objetivo de peso")
        ){

            response =
                    service.showGoalProgress(userId);

        }else if(
                message.contains("racha")
                        || message.contains("streak")
                        || message.contains("constancia")
        ){
            response =
                    service.calculateStreak(userId);
        }
        else if(
                message.contains("estadistica")
                        || message.contains("estadísticas")
                        || message.contains("estadisticas")
                        || message.contains("cumplimiento")
                        || message.contains("rendimiento")
        ){
            response =
                    service.habitsStatistics(userId);
        }
        else{

            response =
                    "🤔 No entendí tu solicitud.\n\n" +
                            "Prueba con preguntas como:\n\n" +
                            "• ¿Cuál es mi IMC?\n" +
                            "• ¿Cuántas calorías debo consumir?\n" +
                            "• Muéstrame mi progreso\n" +
                            "• Genera una rutina\n" +
                            "• ¿Cómo va mi meta?\n" +
                            "• ¿Cuáles son mis datos?";

        }

        chatArea.appendText(
                "\n\n👤 Tú: " + inputField.getText() +
                        "\n\n🤖 FitBot: " + response
        );

        inputField.clear();
    }

}