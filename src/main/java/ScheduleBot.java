import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ScheduleBot extends TelegramLongPollingBot {

    private Multimap<String, Lesson> schedule = null;
    private static List<List<InlineKeyboardButton>> daysList = null;
    private static final String GROUP_NUMBER_FORMAT = "[1-2]{1}[0-9]{4}(\\.[1-4])?";

    private static final String TOKEN = "";
    private static final String USERNAME = "";

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
        if (update.hasCallbackQuery()) {
            try {
                handleCallback(update.getCallbackQuery());
            }
            catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else if (update.hasMessage()) {
            try {
                handleMessage(update.getMessage());
            }
            catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
    }

    private void handleCallback(CallbackQuery callbackQuery) throws TelegramApiException {
        Message message = callbackQuery.getMessage();
        String day = callbackQuery.getData();

        String response = Schedule.getFullSchedule(schedule, day);
        execute(
                SendMessage.builder()
                        .chatId(message.getChatId().toString())
                        .text(response)
                        .build()
        );
    }

    private void sendTryAgainResponse(Message message) throws TelegramApiException {
        execute(
                SendMessage.builder()
                        .chatId(message.getChatId().toString())
                        .text("Incorrect group number, please try again!\n")
                        .build()
        );
    }

    private boolean isGroupNumberValid(String groupNumber) {
        return Pattern.compile(GROUP_NUMBER_FORMAT).matcher(groupNumber).matches();
    }

    public void handleMessage(Message message) throws TelegramApiException {
        if (message.hasText()) {
            if (message.getText().contains("/start")) {
                execute(
                        SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text("Hello i'm nsu schedule test bot! \nPlease, write your group number!")
                                .build()
                );
            }
            else {
                String groupNumber = message.getText();
                if (isGroupNumberValid(groupNumber)) {
                    schedule = ScheduleParser.parseGroupSchedule(groupNumber);
                    if (schedule != null) {
                        execute(
                                SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text("Choose day")
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(daysList).build())
                                .build()
                        );
                    }
                    else {
                        sendTryAgainResponse(message);
                    }
                }
                else {
                    sendTryAgainResponse(message);
                }
            }
        }
    }

    private static void initDaysList() {
        daysList = new ArrayList<>();
        daysList.add(Arrays.asList(
                InlineKeyboardButton.builder().text("Monday").callbackData("MO").build(),
                InlineKeyboardButton.builder().text("Tuesday").callbackData("TU").build()
        ));
        daysList.add(Arrays.asList(
                InlineKeyboardButton.builder().text("Wednesday").callbackData("WE").build(),
                InlineKeyboardButton.builder().text("Thursday").callbackData("TH").build()
        ));
        daysList.add(Arrays.asList(
                InlineKeyboardButton.builder().text("Friday").callbackData("FR").build(),
                InlineKeyboardButton.builder().text("Saturday").callbackData("SA").build()
        ));
    }

    public static void main(String[] args) throws Exception {
        initDaysList();

        ScheduleBot bot = new ScheduleBot(new DefaultBotOptions());
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }
}
