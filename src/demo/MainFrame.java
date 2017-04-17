package demo;

import core.GraphicsEngine;
import graphics.Window;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.GUIComponent;

import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class MainFrame {
    public static void main(String[] args) {
        Window.init("Space Shooter", false);
        GraphicsEngine e = new GraphicsEngine();
        e.registerGUIComponents(GUI.GetStart().getComponents().toArray(new GUIComponent[GUI.GetStart().getComponents().size()]));
        GUI guicomponent = new GUI();
        Button button = new Button ("gui_options_button_titles.png", "MDR",500, 500, Window.getTexture("gui_options_button_titles.png").getWidth(), Window.getTexture("gui_options_button_titles.png").getHeight());
        guicomponent.addComponent(button);
        Button button2 = new Button("", "MDR", 500, 500, 100,100);
        button2.setZ(5);
        Button button3 = new Button("", "MDR", 500, 500, 100,100);
        button3.setZ(8);
        Button button4 = new Button("", "MDR", 500, 500, 100,100);
        button4.setZ(10);
        guicomponent.addComponent(button2);
        guicomponent.addComponent(button4);
        guicomponent.addComponent(button3);
        guicomponent.affiche();
        button.addListener(() -> System.out.println("Hello Button !"));
        Window.registerButtonListener(button.getListeners());

        for (int i = 0; Window.shouldClose(); ++i) {
            Window.loopStart();
            e.drawGui();
            Window.loopEnd();
        }
        glfwTerminate();
    }
}