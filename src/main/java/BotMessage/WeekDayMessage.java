package BotMessage;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;

import TGBotMain.DataBase;

public class WeekDayMessage implements BotMessage {
    private final Long chatId;
    private final String message;

    public WeekDayMessage(Long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    private String getCodeByDay(String day) {
        return day.substring(0, 2).toUpperCase();
    }

    @Override
    public SimpleEntry<Collection<KeyboardRow>, String> handleBotMessage() throws IOException, ClassNotFoundException {
        DataBase dataBase = DataBase.getDataBase();
        String groupNumber = dataBase.getGroupNumber(chatId);

        if (groupNumber == null) {
            return new SimpleEntry<>(null, "You didn't choose your group.\nPlease do it!");
        }
        else {
            String dayCode = getCodeByDay(message);
            String schedule = dataBase.getUserSchedule(chatId).getStringSchedule(dayCode);

            if (schedule.isEmpty()) {
                return new SimpleEntry<>(null, "You haven't lessons today.\nLucky!!");
            }
            else {
                return new SimpleEntry<>(null, schedule);
            }
        }
    }
}
