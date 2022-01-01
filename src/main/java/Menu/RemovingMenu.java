package Menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import TGBotMain.Lesson;

public class RemovingMenu implements Menu {
    private static final int MAX_MESSAGE_LENGTH = 50;
    private final List<Lesson> lessons;
    private final String day;

    public RemovingMenu(List<Lesson> lessons, String day) {
        this.lessons = lessons;
        this.day = day;
    }

    @Override
    public Collection<KeyboardRow> getMenu() {
        List<KeyboardRow> lessonsToDelete = new ArrayList<>();
        KeyboardRow firstRow = new KeyboardRow();
        KeyboardRow secondRow = new KeyboardRow();

        if (lessons.isEmpty()) {
            firstRow.add("Main Menu");
            lessonsToDelete.add(firstRow);
            return lessonsToDelete;
        }

        int firstRowNumber = lessons.size() / 2 + 1;
        int secondRowNumber = lessons.size();
        for (int i = 0; i < firstRowNumber; i++) {
            String subject = lessons.get(i).getSubject();
            if (subject.length() > MAX_MESSAGE_LENGTH) {
                subject = subject.substring(0, MAX_MESSAGE_LENGTH) + "...";
            }
            firstRow.add(subject + " [" + i + " " + day + "]");
        }
        for (int i = firstRowNumber; i < secondRowNumber; i++) {
            String subject = lessons.get(i).getSubject();
            if (subject.length() > MAX_MESSAGE_LENGTH) {
                subject = subject.substring(0, MAX_MESSAGE_LENGTH) + "...";
            }
            secondRow.add(subject + " [" + i + " " + day + "]");
        }
        secondRow.add("Main Menu");

        lessonsToDelete.add(firstRow);
        lessonsToDelete.add(secondRow);

        return lessonsToDelete;
    }
}
