package ChatBotProject.repositories;

import ChatBotProject.entities.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import ChatBotProject.utils.RuntimeTypeAdapterFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserRepository {

    private static final String PATH = "data/users.json";

    private final Gson gson;

    public UserRepository() {

        RuntimeTypeAdapterFactory<SecondaryGoals> goalsAdapter =
                RuntimeTypeAdapterFactory
                        .of(SecondaryGoals.class, "type")
                        .registerSubtype(StressReductionGoal.class,  "StressReductionGoal")
                        .registerSubtype(SleepImprovementGoal.class, "SleepImprovementGoal")
                        .registerSubtype(EnergyBoostGoal.class,      "EnergyBoostGoal")
                        .registerSubtype(HealthyHabitsGoal.class,    "HealthyHabitsGoal");

        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapterFactory(goalsAdapter)
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>)   (src, t, ctx) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, t, ctx) -> LocalDate.parse(json.getAsString()))
                .create();
    }

    // ── Lectura / escritura JSON ──────────────────────────────────────────

    private ArrayList<User> load() {
        File file = new File(PATH);
        if (!file.exists()) return new ArrayList<User>();
        try (Reader r = new FileReader(file)) {
            JsonObject root = JsonParser.parseReader(r).getAsJsonObject();
            Type type = new TypeToken<ArrayList<User>>(){}.getType();
            ArrayList<User> list = gson.fromJson(root.get("users"), type);
            return list != null ? list : new ArrayList<User>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<User>();
        }
    }

    private void save(ArrayList<User> list) {
        File file = new File(PATH);
        file.getParentFile().mkdirs();
        JsonObject root = new JsonObject();
        root.add("users", gson.toJsonTree(list));
        try (Writer w = new FileWriter(file)) {
            gson.toJson(root, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ── Operaciones (misma firma que antes) ──────────────────────────────

    public void create(User user) {
        ArrayList<User> list = load();
        list.add(user);
        save(list);
    }

    public ArrayList<User> getAll() {
        ArrayList<User> list = load();
        list.sort((a, b) -> Integer.compare(a.getId(), b.getId()));
        return list;
    }

    public void getAllUsers() {
        load().forEach(System.out::println);
    }

    public User findByCode(int code) {
        for (User u : load()) {
            if (u.getId() == code) return u;
        }
        return null;
    }

    public boolean deleteByCode(int code) {
        ArrayList<User> list = load();
        boolean removed = list.removeIf(u -> u.getId() == code);
        if (removed) save(list);
        return removed;
    }

    public boolean weightUpdater(double newWeight, int code) {
        ArrayList<User> list = load();
        for (User u : list) {
            if (u.getId() == code) {
                u.setWeight(newWeight);
                u.getWeightHistory().add(newWeight);
                save(list);
                return true;
            }
        }
        return false;
    }

    // ── Métodos nuevos requeridos por UserService ─────────────────────────

    public boolean updateUser(User updated) {
        ArrayList<User> list = load();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == updated.getId()) {
                list.set(i, updated);
                save(list);
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve el menor ID positivo que no esté en uso.
     * Si existen los IDs 1, 3, 4 -> devuelve 2.
     * Si existen 1, 2, 3 -> devuelve 4.
     */
    public int generateNextId() {

        ArrayList<User> list = load();

        int id = 1;

        boolean exists;

        do {

            final int current = id;

            exists = list.stream().anyMatch(u -> u.getId() == current);

            if (exists) {
                id++;
            }

        } while (exists);

        return id;
    }

}