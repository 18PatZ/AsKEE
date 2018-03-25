package lah;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ConversionUtil {

    //private static List<String> chars = Arrays.asList(" ", "`", "-", ".", "'", "_", ":", ",", "\"", "=", "^", ";", "+", "!", "*", "?", "/", "c", "L", "\\", "z", "r", "s", "7", "T", "i", "v", "J", "t", "C", "{", "3", "F", ")", "I", "l", "(", "x", "Z", "f", "Y", "5", "S", "2", "e", "a", "j", "o", "1", "4", "[", "n", "u", "y", "E", "]", "P", "6", "V", "9", "k", "X", "p", "K", "w", "G", "h", "q", "A", "U", "b", "O", "d", "8", "#", "H", "R", "D", "B", "0", "$", "m", "g", "M", "W", "&", "Q", "%", "N", "@");
    //private static List<String> chars = Arrays.asList(" ", ".", "-", ":", "=", "+", "x", "#", "%", "@");
    private static List<String> chars = Arrays.asList("@", "%", "#", "x", "+", "=", ":", "-", ".", " ");
    //private static List<String> chars = Arrays.asList(" ", ".", "-", ":", "=", "@");

    public static String convert(boolean color, double size, double gScale, String file, String outPath){

        try {
            return convert(color, size, gScale, ImageIO.read(new File(file)));
        } catch (IOException e) {
        }

        return null;

    }

    public static String convert(boolean color, double size, double gScale, BufferedImage image){

        double range = 255.0 / chars.size();

        double w = 2 * size;
        double h = 3 * size;

        double width = w * (image.getWidth() / w);
        double height = h * (image.getHeight() / h);

        //String ss = "";
        StringBuilder b1 = new StringBuilder();

        for(double j = 0; j < height; j += h){

            //String s = "";
            StringBuilder b2 = new StringBuilder();

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
                // HIGHER GRAY IS BRIGHTER!!!!
                gray /= gScale;

                int[] a = getRGB(image.getRGB((int) i, (int)j));
                b2.append ((!color ? "" : ("<span style='color:rgb("+a[0]+","+a[1]+","+a[2]+");'>")) + chars.get(Math.min((int)(gray / range), chars.size() - 1)) + (!color ? "" : "</span>"));

            }

            b1.append("\n" + b2.toString());
            //ss += (ss.equals("") ? "" : "\n") + s;

        }

        System.out.println("  IMAGE CONVERSION COMPLETE");

        return b1.toString();

    }

    public static List<String> convertGif(boolean color, double size, double gScale, int loopMax, int inc, String file){

        System.out.println("um hey");

        List<BufferedImage> images = convertGifBI(loopMax, inc, file);
        System.out.println(1);
        List<String> text = new ArrayList<>();
        System.out.println(2);

        images.forEach(i -> text.add(convert(color, size, gScale, i)));
        System.out.println(3);

        System.out.println("CONVERSION COMPLETE");

        return text;

    }

    public static List<BufferedImage> convertGifBI(int loopMax, int inc, String file){

        List<BufferedImage> images = new ArrayList<>();

        try {
            String[] imageatt = new String[]{
                    "imageLeftPosition",
                    "imageTopPosition",
                    "imageWidth",
                    "imageHeight"
            };

            ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
            ImageInputStream ciis = ImageIO.createImageInputStream(new File(file));
            reader.setInput(ciis, false);

            int noi = reader.getNumImages(true);
            BufferedImage master = null;

            for (int i = 0; i < (loopMax == -1 ? noi : Math.min(noi, loopMax)); i += inc) {
                BufferedImage image = reader.read(i);
                IIOMetadata metadata = reader.getImageMetadata(i);

                Node tree = metadata.getAsTree("javax_imageio_gif_image_1.0");
                NodeList children = tree.getChildNodes();

                for (int j = 0; j < children.getLength(); j++) {
                    Node nodeItem = children.item(j);

                    if(nodeItem.getNodeName().equals("ImageDescriptor")){
                        Map<String, Integer> imageAttr = new HashMap<>();

                        for (int k = 0; k < imageatt.length; k++) {
                            NamedNodeMap attr = nodeItem.getAttributes();
                            Node attnode = attr.getNamedItem(imageatt[k]);
                            imageAttr.put(imageatt[k], Integer.valueOf(attnode.getNodeValue()));
                        }
                        if(i == 0)
                            master = new BufferedImage(imageAttr.get("imageWidth"), imageAttr.get("imageHeight"), BufferedImage.TYPE_INT_ARGB);
                        master.getGraphics().drawImage(image, imageAttr.get("imageLeftPosition"), imageAttr.get("imageTopPosition"), null);
                    }
                }

                BufferedImage bI = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_INT_ARGB);
                bI.getGraphics().drawImage(master, 0, 0, null);
                images.add(bI);

                //ImageIO.write(bI, "png", new File( "output/gif/" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return images;
    }

    private static int[] getRGB(int rgb){
        return new int[]{(rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF};
    }

    private static int getBinary(int... rgb){
        return (rgb[0] << 16) + (rgb[1] << 8) + rgb[2];
    }

}
