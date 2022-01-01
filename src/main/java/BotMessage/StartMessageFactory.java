package BotMessage;

public class StartMessageFactory implements BotMessageFactory {
    @Override
    public BotMessage createBotMessage() {
        return new StartMessage();
    }
}
