package BotMessage;

public class MainMenuMessageFactory implements BotMessageFactory {
    @Override
    public BotMessage createBotMessage() {
        return new MainMenuMessage();
    }
}
