import com.google.common.collect.Multimap;

import java.util.HashMap;

public class DataBase {
    private final HashMap<Long, Multimap<String, Lesson>> database;

    public DataBase() {
        database = new HashMap<>();
    }

    public Multimap<String, Lesson> getUserSchedule(Long chatId) {
        return database.get(chatId);
    }

    public void addUserSchedule(Long chatId, Multimap<String, Lesson> schedule) {
        database.put(chatId, schedule);
    }
}
