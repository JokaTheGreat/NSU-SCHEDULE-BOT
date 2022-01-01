package BotMessage;

public class EditWeekDayMessageFactory implements BotMessageFactory {
    private final Long chatId;
    private final String message;

    public EditWeekDayMessageFactory(Long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    @Override
    public BotMessage createBotMessage() {
        return new EditWeekDayMessage(chatId, message);
    }
}
