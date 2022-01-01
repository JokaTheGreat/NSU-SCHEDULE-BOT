package BotMessage;

public class EditingCallbackMessageFactory implements BotMessageFactory {
    private final Long chatId;
    private final String message;

    public EditingCallbackMessageFactory(Long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    @Override
    public BotMessage createBotMessage() {
        return new EditingCallbackMessage(chatId, message);
    }
}
