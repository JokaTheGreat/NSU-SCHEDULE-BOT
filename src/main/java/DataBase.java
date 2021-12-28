import java.util.HashMap;

public class DataBase {

    private final HashMap<Long, User> database;

    public DataBase() {
        database = new HashMap<>();
    }

    public Schedule getUserSchedule(Long chatId) {
        User user = database.get(chatId);
        if (user == null) {
            return null;
        }

        return user.getSchedule();
    }

    public String getGroupNumber(Long chatId) {
        User user = database.get(chatId);
        if (user == null) {
            return null;
        }

        return user.getGroupNumber();
    }

    public void addUser(Long chatId, String groupNumber, Schedule schedule) {
        database.put(chatId, new User(groupNumber, schedule));
    }
}
