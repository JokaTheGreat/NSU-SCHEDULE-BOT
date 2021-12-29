import org.checkerframework.checker.units.qual.K;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Initializations {
    public static Collection<KeyboardRow> initMainMenu() {
        List<KeyboardRow> daysList = new ArrayList<>();
        KeyboardRow firstRow = new KeyboardRow();
        KeyboardRow secondRow = new KeyboardRow();

        firstRow.add("Monday");
        firstRow.add("Tuesday");
        firstRow.add("Wednesday");

        secondRow.add("Thursday");
        secondRow.add("Friday");
        secondRow.add("Saturday");
        secondRow.add("Edit");

        daysList.add(firstRow);
        daysList.add(secondRow);

        return daysList;
    }

    public static Collection<KeyboardRow> initEditingMenu() {
        List<KeyboardRow> daysListDel = new ArrayList<>();
        KeyboardRow firstRow = new KeyboardRow();
        KeyboardRow secondRow = new KeyboardRow();

        firstRow.add("Edit Monday Lessons");
        firstRow.add("Edit Tuesday Lessons");
        firstRow.add("Edit Wednesday Lessons");
        firstRow.add("Edit Thursday Lessons");

        secondRow.add("Edit Friday Lessons");
        secondRow.add("Edit Saturday Lessons");
        secondRow.add("Main Menu");

        daysListDel.add(firstRow);
        daysListDel.add(secondRow);

        return daysListDel;
    }

    public static Collection<KeyboardRow> initLessonDeletingMenu(String day, List<Lesson> lessons) {
        List<KeyboardRow> lessonsToDelete = new ArrayList<>();
        KeyboardRow firstRow = new KeyboardRow();
        KeyboardRow secondRow = new KeyboardRow();

        if (lessons == null) {
            firstRow.add("Main Menu");
            lessonsToDelete.add(firstRow);
            return lessonsToDelete;
        }

        int firstRowNumber = lessons.size() / 2 + 1;
        int secondRowNumber = lessons.size();
        for (int i = 0; i < firstRowNumber; i++) {
            String subject = lessons.get(i).getSubject();
            if (subject.length() > 50) {
                subject = subject.substring(0, 45);
            }
            firstRow.add(subject + " [" + i + " " + day + "]");
        }
        for (int i = firstRowNumber; i < secondRowNumber; i++) {
            String subject = lessons.get(i).getSubject();
            if (subject.length() > 50) {
                subject = subject.substring(0, 45);
            }
            secondRow.add(subject + " [" + i + " " + day + "]");
        }
        secondRow.add("Main Menu");

        lessonsToDelete.add(firstRow);
        lessonsToDelete.add(secondRow);

        return lessonsToDelete;
    }
}
