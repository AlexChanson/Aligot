package graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.lang.System;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by clement on 27/03/17.
 */
public class TextTexture extends Texture {
    private static BufferedImage fontImg;
    private static HashMap<Character, BufferedImage> charImgMap = new HashMap<>();
    private static char[] blankChar = { 32, 127, 129, 141, 143, 144, 157, 160 };
    private String text, color;
    private int size, maxLineWidth;

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }

    TextTexture(String text, int size, String color, int maxLineWidth) {
        if (fontImg == null) {
            loadFontImg();
        }

        this.generateAllChar(text);
    }

    private void generateAllChar(String text) {
        char[] charArr = text.toCharArray();

        for (char c : charArr) {
            if (c > ' ' && c <= 255 && !charImgMap.containsKey(c) && !Arrays.asList(blankChar).contains(c)) {
                this.generateChar(c);
            }
        }
    }

    private void generateChar(char c) {
        short s = (short) (c - 32);
        int x = (s % 16) * 64, y = (s / 16) * 64;

        charImgMap.put(c, this.charImgRemoveMargin(fontImg.getSubimage(x, y, 64, 64)));
    }

    private BufferedImage charImgRemoveMargin(BufferedImage img) {
        DataBuffer buffer = img.getData().getDataBuffer();

        int minX = img.getWidth() - 1, maxX = 0;

        int x, y, elem, alpha;

        for (y = 0; y < img.getHeight(); y++) {
            for (x = 0; x < img.getWidth(); x++) {
                elem = buffer.getElem(img.getWidth() * y + x);
                alpha = elem >> 24;

                if (x < minX && alpha != 0xFF) {
                    minX = x;
                }

                if (x > maxX && alpha != 0xFF) {
                    maxX = x;
                }
            }
        }

        return img.getSubimage(minX, 0, (maxX + 1 - minX), 64);
    }

    private void removeCharBackground(Graphics g) {
    }

    private static void loadFontImg() {
        try {
            String path = System.getProperty("user.dir") + "/ressources/font/verdana.tga";

            fontImg = new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_ARGB);

            File f = new File(path);
            fontImg.getGraphics().drawImage(ImageIO.read(f), 0, 0, null);

            DataBuffer buffer = fontImg.getData().getDataBuffer();

            for (int i = 0; i < buffer.getSize(); i++) {
                buffer.setElem(i, ((buffer.getElem(i) & 0xFF0000) << 8) + 0xFFFFFF);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
