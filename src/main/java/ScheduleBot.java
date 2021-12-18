import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class ScheduleBot extends TelegramLongPollingBot {
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
        if (update.hasMessage()) {
            try {
                handleMessage(update.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void handleMessage(Message message) throws Exception {
        if (message.hasText()) {
            if (message.getText().contains("/start")) {
                execute(
                        SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text("Hello i'm nsu schedule test bot! \nI'm very young \nPlease don't bully me!!")
                                .build()
                );
            }
            else {
                StringBuffer stringBuffer = new StringBuffer("All subjects for 19206:\n");
                Document document = Jsoup.connect("https://table.nsu.ru/group/19206/").get();
                Elements subjects = document.getElementsByAttributeValue("class", "subject");
                subjects.forEach((subject) -> {
                    stringBuffer.append(subject.text()).append("\n");
                });
                execute(
                        SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text(stringBuffer.toString())
                                .build()
                );
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ScheduleBot bot = new ScheduleBot(new DefaultBotOptions());
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }
}
