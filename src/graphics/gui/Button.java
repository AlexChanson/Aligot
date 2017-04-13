package graphics.gui;

/**
 * Created by Christopher on 10/04/2017.
 */
public class Button extends GUIComponent {
    private Texture texture;

    public Button (int x, int y, Texture texture, int width, int height){
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render() throws Exception {

    }

}
