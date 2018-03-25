package lah;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConversionUtil {

    //private static List<String> chars = Arrays.asList(" ", "`", "-", ".", "'", "_", ":", ",", "\"", "=", "^", ";", "+", "!", "*", "?", "/", "c", "L", "\\", "z", "r", "s", "7", "T", "i", "v", "J", "t", "C", "{", "3", "F", ")", "I", "l", "(", "x", "Z", "f", "Y", "5", "S", "2", "e", "a", "j", "o", "1", "4", "[", "n", "u", "y", "E", "]", "P", "6", "V", "9", "k", "X", "p", "K", "w", "G", "h", "q", "A", "U", "b", "O", "d", "8", "#", "H", "R", "D", "B", "0", "$", "m", "g", "M", "W", "&", "Q", "%", "N", "@");
    private static List<String> chars = Arrays.asList(" ", ".", "-", ":", "=", "+", "x", "#", "%", "@");
    //private static List<String> chars = Arrays.asList(" ", ".", "-", ":", "=", "@");

    public static List<List<String>> convert(int size, String file, String outPath){

        double range = 255.0 / chars.size();

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(file));
        } catch (IOException e) {
        }

        double w = 2 * size;
        double h = 3 * size;

        double width = w * (image.getWidth() / w);
        double height = h * (image.getHeight() / h);

        List<List<String>> list = new ArrayList<>();

        for(double j = 0; j < height; j += h){

            List<String> subList = new ArrayList<>();

            for(double i = 0; i < width; i += w){
                //int[] rgb = getRGB(image.getRGB(i, j));

                int average = 0;
                for(double ii = 0; ii < w; ii++) {
                    for (double jj = 0; jj < h; jj++) {
                        if(i + ii >= image.getWidth() || j + jj >= image.getHeight())
                            break;
                        average += image.getRGB((int)(i + ii), (int)(j + jj));
                    }
                }
                average /= (w * h);

                int[] rgb = getRGB(average);

                int gray = (int)(rgb[0] * 0.2989 + rgb[1] * 0.5870 + rgb[2] * 0.1140);

                int[] a = getRGB(image.getRGB((int) i, (int)j));
                subList.add("<span style='color:rgb("+a[0]+","+a[1]+","+a[2]+");'>" + chars.get((int)(gray / range)) + "</span>");
                //subList.add(chars.get((int)(gray / range)));

                /*for(double ii = 0; ii < w; ii++) {
                    for (double jj = 0; jj < h; jj++) {
                        if(i + ii >= image.getWidth() || j + jj >= image.getHeight())
                            break;
                        image.setRGB((int)(i + ii), (int)(j + jj), getBinary(gray, gray, gray));
                    }
                }*/
            }

            list.add(subList);
        }

        try {
            ImageIO.write(image, "png", new File(outPath));
        } catch (IOException e) {
        }

        return list;

    }

    private static int[] getRGB(int rgb){
        return new int[]{(rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF};
    }

    private static int getBinary(int... rgb){
        return (rgb[0] << 16) + (rgb[1] << 8) + rgb[2];
    }

}
