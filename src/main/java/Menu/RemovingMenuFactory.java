package Menu;

import java.util.List;

import TGBotMain.Lesson;

public class RemovingMenuFactory implements MenuFactory {
    private final List<Lesson> lessons;
    private final String day;

    public RemovingMenuFactory(List<Lesson> lessons, String day) {
        this.lessons = lessons;
        this.day = day;
    }

    @Override
    public Menu createMenu() {
        return new RemovingMenu(lessons, day);
    }
}
