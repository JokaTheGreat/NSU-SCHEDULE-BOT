package Menu;

public class EditingMenuFactory implements MenuFactory {
    @Override
    public Menu createMenu() {
        return new EditingMenu();
    }
}
