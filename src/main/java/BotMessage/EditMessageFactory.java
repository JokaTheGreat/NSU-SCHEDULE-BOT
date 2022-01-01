package BotMessage;

public class EditMessageFactory implements BotMessageFactory {
    private final Long chatId;

    public EditMessageFactory(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public BotMessage createBotMessage() {
        return new EditMessage(chatId);
    }
}
