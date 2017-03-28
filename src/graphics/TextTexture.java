package graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.lang.System;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Created by clement on 27/03/17.
 */
public class TextTexture extends Texture {
    private static BufferedImage fontImg;
    private static HashMap<Character, BufferedImage> charImgMap = new HashMap<>();
    private String text, color;
    private int size, maxLineWidth;

    TextTexture(String text, int size, String color, int maxLineWidth) {
        if (fontImg == null) {
            loadFontImg();
        }
    }

    private BufferedImage drawText() {
        char[] charArray = text.toCharArray();

        ArrayList<Integer> linesLength = new ArrayList<>(), linesWidth = new ArrayList<>();

        int charWidth, wordWidth = 0, lineWidth = 0, lineLength = 0, wordLength = 0;

        for (char c : charArray) {
            if (!charImgMap.containsKey(c) && c > ' ' && c <= 255) {
                charImgMap.put(c, drawChar(c));
            }

            charWidth = getCharWidth(c);

            if (charWidth + wordWidth > this.maxLineWidth) {

            }

            /*if (c == ' ' || c && lineWidth + wordWidth > this.maxLineWidth) {
                linesLength.add(lineWidth);
                lineWidth = 0;
            }*/
        }

        return null;
    }

    private int getCharWidth(char c) {
        switch (c) {
            case (char) 160:
            case ' ':
                return (int) (this.size * 0.5);

            case '\t':
                return this.size * 2;

            case '\n':
                return 0;

            default:
                BufferedImage img = charImgMap.get(c);

                if (img != null) {
                    return img.getWidth();
                }
                else {
                    return 0;
                }
        }
    }

    private BufferedImage drawChar(char c) {
        short s = (short) (c - 32);

        int x = (s % 16) * 64, y = (s / 16) * 64;

        BufferedImage ret = fontImg.getSubimage(x, y, 64, 64);

        if (this.isFullyTransparency(ret)) {
            return null;
        }

        ret = this.charImgRemoveMargin(ret);

        return ret;
    }

    private boolean isFullyTransparency(BufferedImage img) {
        DataBuffer buffer = img.getData().getDataBuffer();

        boolean ret = true;

        for (int i = 0; i < buffer.getSize() && ret; i++) {
            ret = (buffer.getElem(i) >> 24) == 0xFF;
        }

        return ret;
    }

    private BufferedImage charImgRemoveMargin(BufferedImage img) {
        DataBuffer buffer = img.getData().getDataBuffer();

        int minX = img.getWidth(), maxX = 0;

        int x, y;
        int elem, alpha;

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

        return img.getSubimage(minX, 0, (maxX - minX), 64);
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
                buffer.setElem(i, (buffer.getElem(i) & 0xFF0000) << 8);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
