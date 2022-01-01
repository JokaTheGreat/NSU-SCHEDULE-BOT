package Menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EditingMenu implements Menu {
    @Override
    public Collection<KeyboardRow> getMenu() {
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
}
