package Menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainMenu implements Menu {
    @Override
    public Collection<KeyboardRow> getMenu() {
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
}
