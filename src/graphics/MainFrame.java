package graphics;

public class MainFrame {
    public static void main(String[] args) {
        Window window = new Window();
        window.createWindow("Space Shooter");
        window.draw("alphys.png",-0.5f,0.5f);
    }
}