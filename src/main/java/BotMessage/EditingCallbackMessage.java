package BotMessage;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.List;

import TGBotMain.*;
import Menu.*;

public class EditingCallbackMessage implements BotMessage {
    private final Long chatId;
    private final String message;

    public EditingCallbackMessage(Long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    private int getLessonIdFromMessage(String message) {
        String lessonId = message.substring(message.lastIndexOf("[") + 1, message.lastIndexOf(" "));
        return Integer.parseInt(lessonId);
    }

    private String getDayCodeFromMessage(String message) {
        return message.substring(message.lastIndexOf(" ") + 1, message.lastIndexOf("]"));
    }

    @Override
    public SimpleEntry<Collection<KeyboardRow>, String> handleBotMessage() throws IOException, ClassNotFoundException {
        DataBase dataBase = DataBase.getDataBase();
        String groupNumber = dataBase.getGroupNumber(chatId);

        if (groupNumber != null) {
            int lessonIndex = getLessonIdFromMessage(message);
            String dayCode = getDayCodeFromMessage(message);
            dataBase.getUserSchedule(chatId).getListSchedule(dayCode).remove(lessonIndex);

            List<Lesson> lessons = dataBase.getUserSchedule(chatId).getListSchedule(dayCode);
            return new SimpleEntry<>((new RemovingMenuFactory(lessons, dayCode)).createMenu().getMenu(), "Lesson deleted successfully!");
        }

        return new SimpleEntry<>((new MainMenuFactory()).createMenu().getMenu(), "You didn't choose your group.\nPlease do it!");
    }
}
