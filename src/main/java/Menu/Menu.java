package Menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collection;

public interface Menu {
    Collection<KeyboardRow> getMenu();
}
