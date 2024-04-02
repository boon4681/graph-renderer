package engine.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Image {
    public static final BufferedImage loadImage(File file) {
        try {
            return ImageIO.read(file);
        } catch (Exception e) {
            return null;
        }
    }
}
