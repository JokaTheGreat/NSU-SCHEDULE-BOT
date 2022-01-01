package BotMessage;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;

import TGBotMain.DataBase;
import Menu.EditingMenuFactory;

public class EditMessage implements BotMessage {
    private final Long chatId;

    public EditMessage(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public SimpleEntry<Collection<KeyboardRow>, String> handleBotMessage() throws IOException, ClassNotFoundException {
        String groupNumber = DataBase.getDataBase().getGroupNumber(chatId);

        if (groupNumber != null) {
            return new SimpleEntry<>((new EditingMenuFactory()).createMenu().getMenu(), "Choose day, which you want to correct");
        }

        return new SimpleEntry<>(null, "You didn't choose your group.\nPlease do it!");
    }
}
