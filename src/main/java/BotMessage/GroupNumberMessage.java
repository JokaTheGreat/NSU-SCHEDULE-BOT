package BotMessage;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.regex.Pattern;

import TGBotMain.*;
import Menu.*;

public class GroupNumberMessage implements BotMessage {
    private static final String GROUP_NUMBER_FORMAT = "[1-2][0-9]{4}(\\.[1-4])?";

    private final Long chatId;
    private final String message;

    public GroupNumberMessage(Long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    private boolean isGroupNumberValid(String groupNumber) {
        return Pattern.compile(GROUP_NUMBER_FORMAT).matcher(groupNumber).matches();
    }

    @Override
    public SimpleEntry<Collection<KeyboardRow>, String> handleBotMessage() throws IOException, ClassNotFoundException {
        Menu menu = (new MainMenuFactory()).createMenu();

        if (!isGroupNumberValid(message)) {
            return new SimpleEntry<>(menu.getMenu(), "Invalid group number.\nPlease try again!");
        }
        else {
            DataBase dataBase = DataBase.getDataBase();
            String oldGroupNumber = dataBase.getGroupNumber(chatId);

            if (oldGroupNumber != null && oldGroupNumber.equals(message)) {
                return new SimpleEntry<>(menu.getMenu(), "Group number is already set!");
            }
            else {
                Schedule schedule = ScheduleParser.parseGroupSchedule(message);

                if (schedule == null) {
                    return new SimpleEntry<>(menu.getMenu(), "Invalid group number.\nPlease try again!");
                }
                else {
                    dataBase.addUser(chatId, message, schedule);
                    return new SimpleEntry<>(menu.getMenu(), "Group number correct!\nNow you can choose the day!");
                }
            }
        }
    }
}
