package demo;

import graphics.Window;
import graphics.gui.Button;
import graphics.gui.GUI;

import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class MainFrame {
    public static void main(String[] args) {
        Window.init("Space Shooter", false);
        GUI guicomponent = new GUI();
        Button button = new Button ("gui_options_button_titles.png", "MDR",500, 500, Window.getTexture("gui_options_button_titles.png").getWidth(), Window.getTexture("gui_options_button_titles.png").getHeight());
        guicomponent.addComponent(button);
        guicomponent.affiche();
        button.addListener(() -> System.out.println("Hello Button !"));
        Window.registerButtonListener(button.getListeners());

        for (int i = 0; Window.shouldClose(); ++i) {
            Window.loopStart();

            Window.drawLine(100, 100, 300, 300, 180, 255, 128, 0);
            Window.drawRectangle(100, 200, 500 + (int) (Math.cos(i / 100.0) * 200), 540, 200, 0, 0, 0);
            Window.drawCircle(1000, 200, 100,0, i % 256, 64);
            //Window.drawSprite("rubics_cube.jpg", 720, 270, 0, (float) (1.0 / (i % 250)));
            Window.drawSprite("rubics_cube.jpg", 720, 270, 128, 128, 0, 1f, 64, 64, 64, 64);
            for (int j = 0; j < 10; j++) {
                Window.drawSprite("carre.png", 22 + j * 4, 22 + j * 4, 20, 20, 0, 1, 50, 50, 20, 20);
            }
            Window.drawSprite("dammier.png", 720, 270, 160, 160, 0, 1f, 160, 160, 320, 320);
            Window.drawRectangle(20, 20, 0, 0, 255, 0, 0, 0);
            Window.drawText("test 1\ntest 2 ut ornare urna ullamcorper nec. Donec in massa suscipit, ullamcorper orci eu, facilisis ante. Fusce eleifend eget neque ac blandit", 0, 0, 16, 300, Window.TEXT_ALIGN_CENTER, 0, 255, 255, false);
            button.draw();

            Window.loopEnd();
        }
        glfwTerminate();
    }
}