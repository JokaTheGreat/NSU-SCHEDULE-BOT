package BotMessage;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.List;

import TGBotMain.*;
import Menu.*;

public class EditWeekDayMessage implements BotMessage {
    private final Long chatId;
    private final String message;

    public EditWeekDayMessage(Long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    private String getCodeByDay(String day) {
        return day.substring(0, 2).toUpperCase();
    }

    private String getDayFromMessage(String editString) {
        return editString.substring(editString.indexOf("Edit") + "Edit".length() + 1, editString.lastIndexOf(" "));
    }

    @Override
    public SimpleEntry<Collection<KeyboardRow>, String> handleBotMessage() throws IOException, ClassNotFoundException {
        DataBase dataBase = DataBase.getDataBase();
        String groupNumber = dataBase.getGroupNumber(chatId);

        if (groupNumber != null) {
            String dayCode = getCodeByDay(getDayFromMessage(message));
            List<Lesson> lessons = dataBase.getUserSchedule(chatId).getListSchedule(dayCode);

            return new SimpleEntry<>((new RemovingMenuFactory(lessons, dayCode)).createMenu().getMenu(), "Choose lesson");
        }

        return new SimpleEntry<>((new MainMenuFactory()).createMenu().getMenu(), "You didn't choose your group.\nPlease do it!");
    }
}
