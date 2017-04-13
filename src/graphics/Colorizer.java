package graphics;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Colorizer {
    public static BufferedImage colorize(BufferedImage original, Color tint) {

        BufferedImage img = new BufferedImage(original.getWidth(), original.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);

        int redVal=tint.getRed();
        int greenVal=tint.getGreen();
        int blueVal=tint.getBlue();

        for (int x=0;x<original.getWidth();x++) for (int y=0;y<original.getHeight();y++) {
            Color pixelVal = new Color(original.getRGB(x, y), true);
            int grayValue=pixelVal.getRed();
            int alpha=pixelVal.getAlpha();
            int newRed= (redVal*(grayValue))/255;
            int newGreen= (greenVal*grayValue)/255;
            int newBlue= (blueVal*grayValue)/255;
            img.setRGB(x, y, new Color(newRed,newGreen,newBlue,alpha).getRGB());
        }
        return img;}
}
