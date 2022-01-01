package BotMessage;

public class WeekDayMessageFactory implements BotMessageFactory {
    private final Long chatId;
    private final String message;

    public WeekDayMessageFactory(Long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    @Override
    public BotMessage createBotMessage() {
        return new WeekDayMessage(chatId, message);
    }
}
