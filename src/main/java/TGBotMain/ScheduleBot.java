package TGBotMain;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Collection;
import BotMessage.*;

public class ScheduleBot extends TelegramLongPollingBot {
    private static final String TOKEN = "5076105079:AAEpt9sWk4vuWG5-HUy7LAGbZnSAzYt1Kb0";
    private static final String USERNAME = "@nsuScheduleTestBot";

    public ScheduleBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            try {
                handleMessage(update.getMessage());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendResponse(Message message, String text, Collection<KeyboardRow> menu) throws TelegramApiException {
        if (menu == null) {
            execute(
                    SendMessage.builder()
                            .chatId(message.getChatId().toString())
                            .text(text)
                            .build()
            );
        }
        else {
            execute(
                    SendMessage.builder()
                            .chatId(message.getChatId().toString())
                            .text(text)
                            .replyMarkup(ReplyKeyboardMarkup.builder().keyboard(menu).resizeKeyboard(true).build())
                            .build()
            );
        }
    }

    private boolean isWeekDay(String userMessage) {
        String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        return Arrays.stream(weekDays).anyMatch(userMessage::contains);
    }

    private boolean isEditingCallback(String userMessage) {
        return userMessage.contains("[") && userMessage.contains("]");
    }

    public void handleMessage(Message message) throws TelegramApiException, IOException, ClassNotFoundException {
        if (message.hasText()) {
            String userMessage = message.getText();
            Long chatId = message.getChatId();
            BotMessageFactory botMessageFactory;

            if (userMessage.contains("/start")) {
                botMessageFactory = new StartMessageFactory();
            }
            else if (userMessage.contains("Main Menu")) {
                botMessageFactory = new MainMenuMessageFactory();
            }
            else if (userMessage.equals("Edit")) {
                botMessageFactory = new EditMessageFactory(chatId);
            }
            else if (userMessage.contains("Edit")) {
                botMessageFactory = new EditWeekDayMessageFactory(chatId, userMessage);
            }
            else if (isWeekDay(userMessage)) {
                botMessageFactory = new WeekDayMessageFactory(chatId, userMessage);
            }
            else if (isEditingCallback(userMessage)) {
                botMessageFactory = new EditingCallbackMessageFactory(chatId, userMessage);
            }
            else {
                botMessageFactory = new GroupNumberMessageFactory(chatId, userMessage);
            }

            BotMessage botMessage = botMessageFactory.createBotMessage();
            SimpleEntry<Collection<KeyboardRow>, String> result = botMessage.handleBotMessage();
            sendResponse(message, result.getValue(), result.getKey());
        }
    }

    public static void main(String[] args) throws Exception {
        ScheduleBot bot = new ScheduleBot(new DefaultBotOptions());
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        telegramBotsApi.registerBot(bot);
    }
}