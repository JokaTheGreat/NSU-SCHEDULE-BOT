package BotMessage;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;

import Menu.MainMenuFactory;

public class MainMenuMessage implements BotMessage {
    @Override
    public SimpleEntry<Collection<KeyboardRow>, String> handleBotMessage() {
        return new SimpleEntry<>((new MainMenuFactory()).createMenu().getMenu(), "Choose day");
    }
}
