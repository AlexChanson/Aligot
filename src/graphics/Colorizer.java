package graphics;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Colorizer {

    public static BufferedImage colorize(BufferedImage original, Color tint, int seuil) {
        BufferedImage img = new BufferedImage(original.getWidth(), original.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);

        for (int x = 0; x < original.getWidth(); ++x) {
            for (int y = 0; y < original.getHeight(); ++y) {
                Color pixelVal = new Color(original.getRGB(x, y), true);
                int grayValue = (pixelVal.getBlue()+pixelVal.getGreen()+pixelVal.getRed())/3;
                int newRed;
                int newGreen;
                int newBlue;
                if(grayValue > seuil){
                    newRed = grayValue;
                    newGreen = grayValue;
                    newBlue = grayValue;
                }else{
                    newRed = (tint.getRed()/255) * grayValue;
                    newGreen = (tint.getGreen()/255) * grayValue;
                    newBlue = (tint.getBlue()/255) * grayValue;
                }

                img.setRGB(x, y, new Color(newRed, newGreen, newBlue, pixelVal.getAlpha()).getRGB());
            }
        }
        return img;
    }

    public static BufferedImage colorize(BufferedImage original, Color tint){
        return colorize(original, tint, 200);
    }

}
