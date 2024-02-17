package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


/**
 * This class contains utility methods to read a PPM image from file
 * and simply print its contents. Feel free to change this method
 * as required.
 */
public class ImageUtil {

  /**
   * This function creates a PPM file.
   * @param token P3 token is being passed
   * @param width width of the image
   * @param height height of the image
   * @param maxValue max pixel value of the image
   * @param imageArray Image to be used
   * @param filePath Path of the file
   * @throws IOException Input output exception
   */
  public StringBuilder createPPMFile(String token, int width, int height, int maxValue,
                            ImageArray imageArray, String filePath) throws IOException {
    StringBuilder ppmFormat = new StringBuilder();
    List<Integer>[][] image = imageArray.getIA();
    ppmFormat.append(token + '\n' + width + " " + height + '\n' + maxValue + '\n');

    for (int i = 0;i < height;i++) {
      for (int j = 0;j < width;j++) {
        for (int k : image[i][j]) {
          ppmFormat.append(k);
          ppmFormat.append('\n');
        }
      }
      ppmFormat.append('\n');
    }
    return ppmFormat;
  }

  /**
   * This function creates a JPEG, PNG, BMP image.
   * @param imageArray Input array from hashmap
   * @return BufferedImage
   * @throws IOException throws file if not found
   */
  public BufferedImage createImageFile(ImageArray imageArray) throws IOException {

    List<Integer>[][] pixelOfImage = imageArray.getIA();
    int height = pixelOfImage.length;
    int width = pixelOfImage[0].length;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        List<Integer> pixel = pixelOfImage[y][x];
        int red = pixel.get(0);
        int green = pixel.get(1);
        int blue = pixel.get(2);
        int rgb = (red << 16) | (green << 8) | blue;
        image.setRGB(x, y, rgb);
      }
    }
    return image;
  }

  /**
   * This function flips the image - horizontally or vertically.
   * @param option Horizontal or Vertical
   * @param width width of the image
   * @param height height of the image
   * @param imageArray Final 2D array with all pixels
   * @return Flipped image
   * @throws IOException Input Output exception
   */
  public ImageArray flipping(String option, int width, int height,
                                           ImageArray imageArray) throws IOException {
    List<Integer>[][] outputArrayFlip = new List[height][width];
    List<Integer>[][] pixelOfImage = imageArray.getIA();
    System.arraycopy(pixelOfImage,0,outputArrayFlip,0,height);

    if (option.equals("Horizontal")) {
      for (int i = 0;i < height;i++) {
        for (int j = 0; j < width / 2; j++) {
          List<Integer> temp = outputArrayFlip[i][j];
          outputArrayFlip[i][j] = outputArrayFlip[i][width - j - 1];
          outputArrayFlip[i][width - j - 1] = temp;
        }
      }
    }

    if (option.equals("Vertical")) {
      for (int i = 0;i < height / 2;i++) {
        for (int j = 0; j < width; j++) {
          List<Integer> temp = outputArrayFlip[i][j];
          outputArrayFlip[i][j] = outputArrayFlip[height - i - 1][j];
          outputArrayFlip[height - i - 1][j] = temp;
        }
      }
    }
    return new ImageArrayImpl(outputArrayFlip);
  }

  /**
   * Converts RGB image to their respective greyscale components.
   * @param option Greyscale option to be converted to
   * @param width width of the image
   * @param height height of the image
   * @param imageArray Final 2D array with all pixels
   * @return Greyscale image array
   * @throws IOException Input Output Exception
   */
  public ImageArray greyscale(String option, int width, int height,
                                            ImageArray imageArray) throws IOException {
    List<Integer>[][] outputArray = new List[height][width];
    List<Integer>[][] pixelOfImage = imageArray.getIA();

    for (int i = 0;i < height;i++) {
      for (int j = 0;j < width;j++) {
        List<Integer> val = new ArrayList<>();
        if (option.equals("Red")) {
          int temp1 = pixelOfImage[i][j].get(0);
          val.add(temp1);
          val.add(temp1);
          val.add(temp1);
        }
        else if (option.equals("Green")) {
          int temp2 = pixelOfImage[i][j].get(1);
          val.add(temp2);
          val.add(temp2);
          val.add(temp2);
        }
        else if (option.equals("Blue")) {
          int temp3 = pixelOfImage[i][j].get(2);
          val.add(temp3);
          val.add(temp3);
          val.add(temp3);

        }
        else if (option.equals("Value")) {
          List<Integer> temp = pixelOfImage[i][j];
          int max = Collections.max(temp);
          val.add(max);
          val.add(max);
          val.add(max);
        }
        else if (option.equals("Intensity")) {
          List<Integer> temp = pixelOfImage[i][j];
          int average = (temp.get(0) + temp.get(1) + temp.get(2)) / 3;
          val.add(average);
          val.add(average);
          val.add(average);
        }
        else if (option.equals("Luma")) {
          List<Integer> temp = pixelOfImage[i][j];
          double weightedSum = 0.2126 * temp.get(0) + 0.7152 * temp.get(1) + 0.0722 * temp.get(2);
          val.add((int) weightedSum);
          val.add((int) weightedSum);
          val.add((int) weightedSum);
        }
        outputArray[i][j] = val;
      }
    }
    return new ImageArrayImpl(outputArray);
  }

  /**
   * This function increases or decreases the contrast of the image.
   * @param value Value to be increased or decreased by
   * @param width width of the image
   * @param height height of the image
   * @param imageArray Final 2D array with all pixels
   * @return Output Array
   * @throws IOException Input output exception
   */
  public ImageArray brightenDarken(int value, int width, int height,
                                   ImageArray imageArray) throws IOException {
    List<Integer>[][] brighterDarker = new List[height][width];
    List<Integer>[][] pixelOfImage = imageArray.getIA();
    System.arraycopy(pixelOfImage,0,brighterDarker,0,height);

    for (int i = 0;i < height;i++) {
      for (int j = 0;j < width;j++) {
        if (value > 0) {
          int firstElement = Math.min(brighterDarker[i][j].get(0) + value, 255);
          brighterDarker[i][j].set(0, firstElement);
          int secondElement = Math.min(brighterDarker[i][j].get(1) + value, 255);
          brighterDarker[i][j].set(1, secondElement);
          int thirdElement = Math.min(brighterDarker[i][j].get(2) + value, 255);
          brighterDarker[i][j].set(2, thirdElement);
        }
        else if (value < 0) {
          int firstElement = Math.max(brighterDarker[i][j].get(0) + value, 0);
          brighterDarker[i][j].set(0, firstElement);
          int secondElement = Math.max(brighterDarker[i][j].get(1) + value, 0);
          brighterDarker[i][j].set(1, secondElement);
          int thirdElement = Math.max(brighterDarker[i][j].get(2) + value, 0);
          brighterDarker[i][j].set(2, thirdElement);
        }
      }
    }
    return new ImageArrayImpl(brighterDarker);
  }

  /**
   * This function extracts an RGB image from the greyscale arrays.
   * @param rImage greyscale of red component
   * @param gImage greyscale of green component
   * @param bImage greyscale of blue component
   * @param width width of the image
   * @param height height of the image
   * @return RGB image
   * @throws IOException Input output exception
   */
  public ImageArray combineComponents(ImageArray rImage, ImageArray gImage, ImageArray bImage,
                                      int width, int height) throws IOException {
    List<Integer>[][] combinedImage = new List[height][width];
    List<Integer>[][] r = rImage.getIA();
    List<Integer>[][] g = gImage.getIA();
    List<Integer>[][] b = bImage.getIA();

    for (int i = 0;i < height;i++) {
      for (int j = 0; j < width; j++) {
        List<Integer> rgbValues = new ArrayList<>();
        int firstElement = r[i][j].get(0);
        rgbValues.add(firstElement);
        int secondElement = g[i][j].get(1);
        rgbValues.add(secondElement);
        int thirdElement = b[i][j].get(2);
        rgbValues.add(thirdElement);
        combinedImage[i][j] = rgbValues;
      }
    }
    return new ImageArrayImpl(combinedImage);
  }

  /**
   * Read an image file in the PPM format and print the colors.
   * @param sc contains the contents of the file.
   */
  public ImageArray readPPM(Scanner sc) throws IOException {

    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.length() != 0 && s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    String token; 

    token = sc.next();
    if (!token.equals("P3")) {
      //System.out.println("Invalid PPM file: plain RAW file should begin with P3");
      return null;
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    List<Integer>[][] image = new List[height][width];
    for (int i = 0;i < height;i++) {
      for (int j = 0;j < width;j++) {
        List<Integer> rgb = new LinkedList<>();
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        rgb.add(r);
        rgb.add(g);
        rgb.add(b);
        image[i][j] = rgb;
      }
    }
    return new ImageArrayImpl(image);
  }

  /**
   * Function to get a dithered image.
   * @param imageArray Input Image
   * @param width Width of the image
   * @param height Height of the image
   * @return Dithered image 2D array
   */
  public ImageArray dithering(ImageArray imageArray, int width, int height) {

    List<Integer>[][] ditheredImage = new List[height][width];
    List<Integer>[][] pixelOfImage =  imageArray.getIA();
    System.arraycopy(pixelOfImage,0,ditheredImage,0,height);
    int error;
    int newPixel;
    for (int i = 0; i < height;i++) {
      for (int j = 0;j < width;j++) {
        int oldPixel = pixelOfImage[i][j].get(0);
        if (oldPixel < 128) {
          newPixel = 0;
          ditheredImage[i][j].set(0, 0);
          ditheredImage[i][j].set(1, 0);
          ditheredImage[i][j].set(2, 0);
        }

        else {
          newPixel = 255;
          ditheredImage[i][j].set(0, 255);
          ditheredImage[i][j].set(1, 255);
          ditheredImage[i][j].set(2, 255);
        }
        error = oldPixel - newPixel;

        if (j < width - 1) {
          pixelOfImage[i][j + 1].set(0,pixelOfImage[i][j + 1].get(0) + error * 7 / 16);
          pixelOfImage[i][j + 1].set(1,pixelOfImage[i][j + 1].get(1) + error * 7 / 16);
          pixelOfImage[i][j + 1].set(2,pixelOfImage[i][j + 1].get(2) + error * 7 / 16);
        }

        if (j > 0 && i < height - 1) {
          pixelOfImage[i + 1][j - 1].set(0,pixelOfImage[i + 1][j - 1].get(0) + error * 3 / 16);
          pixelOfImage[i + 1][j - 1].set(1,pixelOfImage[i + 1][j - 1].get(1) + error * 3 / 16);
          pixelOfImage[i + 1][j - 1].set(2,pixelOfImage[i + 1][j - 1].get(2) + error * 3 / 16);
        }

        if (i < height - 1) {
          pixelOfImage[i + 1][j].set(0,pixelOfImage[i + 1][j].get(0) + error * 5 / 16);
          pixelOfImage[i + 1][j].set(1,pixelOfImage[i + 1][j].get(1) + error * 5 / 16);
          pixelOfImage[i + 1][j].set(2,pixelOfImage[i + 1][j].get(2) + error * 5 / 16);
        }

        if (j < width - 1 && i < height - 1) {
          pixelOfImage[i + 1][j + 1].set(0,pixelOfImage[i + 1][j + 1].get(0) + error / 16);
          pixelOfImage[i + 1][j + 1].set(1,pixelOfImage[i + 1][j + 1].get(1) + error / 16);
          pixelOfImage[i + 1][j + 1].set(2,pixelOfImage[i + 1][j + 1].get(2) + error / 16);
        }
      }
    }
    return new ImageArrayImpl(ditheredImage);
  }

  /**
   * Colour transformation of image.
   * @param filter Filter to be used
   * @param imageArray Image array
   * @param width width of image
   * @param height height of image
   * @return 2D image array
   */
  public ImageArray colorTransformation(double[][] filter, ImageArray imageArray,
                                               int width, int height) {
    List<Integer>[][] outputImage = new List[height][width];
    List<Integer>[][] pixelOfImage = imageArray.getIA();
    System.arraycopy(pixelOfImage,0,outputImage,0,height);

    for (int i = 0;i < height;i++) {
      for (int j = 0;j < width;j++) {
        double redPixel = filter[0][0] * outputImage[i][j].get(0) +
                filter[0][1] * outputImage[i][j].get(1) +
                filter[0][2] * outputImage[i][j].get(2);
        redPixel = Math.min(255, redPixel);

        double greenPixel = filter[1][0] * outputImage[i][j].get(0) +
                filter[1][1] * outputImage[i][j].get(1) +
                filter[1][2] * outputImage[i][j].get(2);
        greenPixel = Math.min(255, greenPixel);

        double bluePixel = filter[2][0] * outputImage[i][j].get(0) +
                filter[2][1] * outputImage[i][j].get(1) +
                filter[2][2] * outputImage[i][j].get(2);
        bluePixel = Math.min(255, bluePixel);

        outputImage[i][j].set(0, (int) Math.round(redPixel));
        outputImage[i][j].set(1, (int) Math.round(greenPixel));
        outputImage[i][j].set(2, (int) Math.round(bluePixel));
      }
    }
    return new ImageArrayImpl(outputImage);
  }

  /**
   * This function is used to apply filter over an image.
   * @param imageArray Input image
   * @param filter Filter to be convolved with
   * @param width width of the image
   * @param height height of the image
   * @return 2D array of convolution image
   */
  public ImageArray filterImage(ImageArray imageArray, double[][] filter, int width, int height) {

    List<Integer>[][] filterImage = new List[height][width];
    List<Integer>[][] image = imageArray.getIA();
    int mid = filter.length / 2;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        double r = 0.0;
        double g = 0.0;
        double b = 0.0;
        for (int m = -mid; m <= mid; m++) {
          for (int n = -mid; n <= mid; n++) {
            int pixelX = i + m;
            int pixelY = j + n;
            if (pixelX >= 0 && pixelX < height && pixelY >= 0 && pixelY < width) {
              double weights = filter[m + mid][n + mid];
              r += image[pixelX][pixelY].get(0) * weights;
              g += image[pixelX][pixelY].get(1) * weights;
              b += image[pixelX][pixelY].get(2) * weights;
            }
          }
        }
        int newRed = Math.min(Math.max((int) r, 0), 255);
        int newGreen = Math.min(Math.max((int) g, 0), 255);
        int newBlue = Math.min(Math.max((int) b, 0), 255);

        List<Integer> filterPixel = new ArrayList<>();
        filterPixel.add(0, Math.round(newRed));
        filterPixel.add(1, Math.round(newGreen));
        filterPixel.add(2, Math.round(newBlue));
        filterImage[i][j] = filterPixel;
      }
    }
    return new ImageArrayImpl(filterImage);
  }

  /**
   * Takes input in png or jpeg and converts it to 2D image array.
   * @param imageInput Buffered Image Input
   * @return 2D image array
   * @throws IOException Input Output exception if file not found
   */
  public ImageArray readJPEGPNGBMPFile(BufferedImage imageInput) throws IOException {

    int width = imageInput.getWidth();
    int height = imageInput.getHeight();

    List<Integer>[][] image = new List[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int pixel = imageInput.getRGB(j, i);
        List<Integer> rgb = new LinkedList<>();
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = pixel & 0xff;
        rgb.add(red);
        rgb.add(green);
        rgb.add(blue);
        image[i][j] = rgb;
      }
    }
    return new ImageArrayImpl(image);
  }
}

