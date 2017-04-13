package graphics;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Colorizer {

    public static BufferedImage colorize(BufferedImage original, Color tint) {
        BufferedImage img = new BufferedImage(original.getWidth(), original.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);

        for (int x = 0; x < original.getWidth(); ++x) {
            for (int y = 0; y < original.getHeight(); ++y) {
                Color pixelVal = new Color(original.getRGB(x, y), true);
                int grayValue = pixelVal.getRed();
                int newRed = (tint.getRed() * grayValue) / 255;
                int newGreen = (tint.getGreen() * grayValue) / 255;
                int newBlue = (tint.getBlue() * grayValue) / 255;
                img.setRGB(x, y, new Color(newRed, newGreen, newBlue, pixelVal.getAlpha()).getRGB());
            }
        }
        return img;
    }

}
