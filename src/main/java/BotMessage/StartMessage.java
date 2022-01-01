package BotMessage;

import Menu.MainMenuFactory;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;

public class StartMessage implements BotMessage {
    @Override
    public SimpleEntry<Collection<KeyboardRow>, String> handleBotMessage() {
        String responseText = """
                Hello i'm nsu schedule test bot!
                Please write your group number!

                If you want to completely change your group - write your group number again!
                """;

        return new SimpleEntry<>((new MainMenuFactory()).createMenu().getMenu(), responseText);
    }
}
