import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ReplaceColors {

  public static void main(String args[]) throws Exception {

    //Reading the input_pallet1.png -> store colors in Arraylist<String>
    File file = new File("input_pallet1.png");
    BufferedImage img = ImageIO.read(file);

    ArrayList < String > pallet1Colors = new ArrayList < String > ();

    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {

        int pixel = img.getRGB(x, y);
        Color color = new Color(pixel, true);

        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        String str = red + ":" + green + ":" + blue + "";

        if (!pallet1Colors.contains(str))
          pallet1Colors.add(str);
      }
    }

    //same with input_pallet2.png 
    file = new File("input_pallet2.png");
    img = ImageIO.read(file);

    ArrayList < String > pallet2Colors = new ArrayList < String > ();

    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {

        int pixel = img.getRGB(x, y);
        Color color = new Color(pixel, true);

        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        String str = red + ":" + green + ":" + blue + "";

        if (!pallet2Colors.contains(str))
          pallet2Colors.add(str);
      }
    }

    /******** T I M E  T O  S W A P **************/

    file = new File("input_toChange.png");
    img = ImageIO.read(file);

    //create buffered image object
    BufferedImage img2 = null;
    img2 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {

        //get color from input_toChange.png
        int pixel = img.getRGB(x, y);
        Color color = new Color(pixel, true);

        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        String str = red + ":" + green + ":" + blue + "";

        if (pallet1Colors.contains(str)
              /* && !str.equals("255:255:255")
                  && !str.equals("0:0:0") */ ){

          int indexOf = pallet1Colors.indexOf(str);

          int hashIndex = indexOf % pallet2Colors.size();
          str = pallet2Colors.get(hashIndex);

          int r = getRedFromStr(str);

          int g = getGreenFromStr(str);

          int b = getBlueFromStr(str);

          int p = (255 << 24) | (r << 16) | (g << 8) | b;

          img2.setRGB(x, y, p);

        }
      }
    }

    //write newImage
    File f = null;
    try {
      f = new File("output_newImage.png");
      ImageIO.write(img2, "png", f);

      System.out.println("Image Generated");
    } catch (IOException e) {
      System.out.println("Error: " + e);
    }
  }

  public static int getRedFromStr(String str) {
    int index = 0;

    int r = Integer.parseInt(str.substring(0, str.indexOf(":", 0)));

    return r;

  }
  public static int getGreenFromStr(String str) {

    int index = 0;

    int r = Integer.parseInt(str.substring(0, str.indexOf(":", 0)));
    index = str.indexOf(":", 0);
    int g = Integer.parseInt(str.substring(index + 1, str.indexOf(":", index + 1)));

    return g;

  }
  public static int getBlueFromStr(String str) {

    int index = 0;

    int r = Integer.parseInt(str.substring(0, str.indexOf(":", 0)));
    index = str.indexOf(":", 0);
    int g = Integer.parseInt(str.substring(index + 1, str.indexOf(":", index + 1)));
    index = str.indexOf(":", index + 1);
    int b = Integer.parseInt(str.substring(index + 1));

    return b;

  }

}