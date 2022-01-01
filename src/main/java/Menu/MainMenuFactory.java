package Menu;

public class MainMenuFactory implements MenuFactory {
    @Override
    public Menu createMenu() {
        return new MainMenu();
    }
}
