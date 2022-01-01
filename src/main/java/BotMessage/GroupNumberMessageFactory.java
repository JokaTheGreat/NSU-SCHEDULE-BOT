package BotMessage;

public class GroupNumberMessageFactory implements BotMessageFactory {
    private final Long chatId;
    private final String message;

    public GroupNumberMessageFactory(Long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    @Override
    public BotMessage createBotMessage() {
        return new GroupNumberMessage(chatId, message);
    }
}
