import controller.ImageController;
import controller.ImageControllerImpl;
import model.ImageArray;
import model.ImageModel;
import model.ImageModelImpl;
import model.ImageUtil;
import view.ImageView;
import view.ImageViewImpl;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * Class to test functionalities of IME.
 */
public class ImageControllerImplTest {

  /**
   * Testing with a wrong command.
   * @throws IOException if file not accessible.
   */
  @Test
  public void testCommandFormat() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input = "crop src/Koala.ppm Koala";
    in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input), out);
    assertEquals("Invalid Command \nAvailable commands:\n"
            + "load\nsave\nvertical-flip\n"
            + "horizontal-flip\ngreyscale\nrgb-split\nbrighten\n"
            + "rgb-combine\nrun-script", out.toString());
  }

  /**
   * Load command test - wrong command format.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void loadCommandTest1() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input = "load src/Koala.ppm";
    in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input), out);
    assertEquals("load command format is not correct \n "
            + "Format: load image-path image-name", out.toString());
  }

  /**
   * Load Command Test - same image-name.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void loadCommandTest2() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input = "load src/Koala.ppm Koala";
    String input2 = "load src/Koala.ppm Koala";
    in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageController controller = new ImageControllerImpl(model, in, out, view);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    assertEquals("Command Executed Successfully\nimage-name already used by another image",
            out.toString());
  }

  /**
   * Save Command Test - same image-name.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void saveCommandTest1() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input = "save src/Koala.ppm Koala";
    in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input), out);
    assertEquals("image-name not present", out.toString());
  }

  /**
   * Command Test - Loading, saving and accessing the same image.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void loadSaveLoadCommandTest1() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/Koala.ppm Koala";
    String input2 = "save src/Koala1.ppm Koala";
    String input3 = "load src/Koala1.ppm Koala1";
    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageController controller = new ImageControllerImpl(model, in, out, view);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);
    ImageUtil imageUtil = new ImageUtil();
    Scanner sc = new Scanner(new FileInputStream("src/Koala.ppm"));
    ImageArray image1 = imageUtil.readPPM(sc);
    Scanner sc2 = new Scanner(new FileInputStream("src/Koala1.ppm"));
    ImageArray image2 = imageUtil.readPPM(sc2);
    assertEquals(Arrays.stream(image2.getIA()).toArray(), Arrays.stream(image1.getIA()).toArray());
  }

  /**
   * Converts ppm image to jpeg and checks whether the pixels are same.
   * @throws IOException throws exception if file not found
   */
  @Test
  public void loadSaveLoadCommandTest2() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/Koala.ppm Koala";
    String input2 = "save src/Koala1.jpeg Koala";
    String input3 = "load src/Koala1.jpeg Koala1";
    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);
    ImageUtil imageUtil = new ImageUtil();
    Scanner sc = new Scanner(new FileInputStream("src/Koala.ppm"));
    ImageArray image1 = imageUtil.readPPM(sc);
    File file = new File("src/Koala1.jpeg");
    BufferedImage imageInput = ImageIO.read(file);
    ImageArray image2 = imageUtil.readJPEGPNGBMPFile(imageInput);
    assertEquals(Arrays.stream(image2.getIA()).toArray(), Arrays.stream(image1.getIA()).toArray());
  }

  /**
   * Converts ppm image to png and checks whether the pixels are same.
   * @throws IOException throws exception if file not found
   */
  @Test
  public void loadSaveLoadCommandTest3() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/Koala.ppm Koala";
    String input2 = "save src/Koala2.png Koala";
    String input3 = "load src/Koala2.png Koala2";
    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);
    ImageUtil imageUtil = new ImageUtil();
    Scanner sc = new Scanner(new FileInputStream("src/Koala.ppm"));
    ImageArray image1 = imageUtil.readPPM(sc);
    File file = new File("src/Koala2.png");
    BufferedImage imageInput = ImageIO.read(file);
    ImageArray image2 = imageUtil.readJPEGPNGBMPFile(imageInput);
    assertEquals(Arrays.stream(image2.getIA()).toArray(), Arrays.stream(image1.getIA()).toArray());
  }

  /**
   * Converts ppm image to bmp and checks whether the pixels are same.
   * @throws IOException throws exception if file not found
   */
  @Test
  public void loadSaveLoadCommandTest4() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/Koala.ppm Koala";
    String input2 = "save src/Koala3.bmp Koala";
    String input3 = "load src/Koala3.bmp Koala3";
    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageController controller = new ImageControllerImpl(model, in, out, view);

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);
    ImageUtil imageUtil = new ImageUtil();
    Scanner sc = new Scanner(new FileInputStream("src/Koala.ppm"));
    ImageArray image1 = imageUtil.readPPM(sc);
    File file = new File("src/Koala3.bmp");
    BufferedImage imageInput = ImageIO.read(file);
    ImageArray image2 = imageUtil.readJPEGPNGBMPFile(imageInput);
    assertEquals(Arrays.stream(image2.getIA()).toArray(), Arrays.stream(image1.getIA()).toArray());
  }

  /**
   * Command Test - Checks whether horizontal flip is right.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void flipCommandTest1() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();

    String input1 = "load src/fruits.ppm Fruits";
    String input2 = "horizontal-flip Fruits hFruits";
    String input3 = "save src/actualoutput/hFruits.ppm hFruits";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);

    ImageUtil imageUtil = new ImageUtil();
    Scanner sc = new Scanner(new FileInputStream("src/actualoutput/hFruits.ppm"));
    ImageArray image1 = imageUtil.readPPM(sc);
    Scanner sc1 = new Scanner(new FileInputStream("res/hfFruits.ppm"));
    ImageArray image2 = imageUtil.readPPM(sc1);
    assertEquals(Arrays.stream(image1.getIA()).toArray(), Arrays.stream(image2.getIA()).toArray());
  }

  /**
   * Command Test - Checks whether vertical flip is right.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void flipCommandTest2() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();

    String input1 = "load src/fruits.ppm Fruits";
    String input2 = "vertical-flip Fruits vFruits";
    String input3 = "save src/actualoutput/vFruits.ppm vFruits";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageController controller = new ImageControllerImpl(model, in, out, view);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);

    ImageUtil imageUtil = new ImageUtil();
    Scanner sc = new Scanner(new FileInputStream("src/actualoutput/vFruits.ppm"));
    ImageArray image1 = imageUtil.readPPM(sc);
    Scanner sc1 = new Scanner(new FileInputStream("res/vfFruits.ppm"));
    ImageArray image2 = imageUtil.readPPM(sc1);
    assertEquals(Arrays.stream(image1.getIA()).toArray(), Arrays.stream(image2.getIA()).toArray());
  }

  /**
   * Command Test - Horizontal flip, Vertical flip an image where command given is wrong.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void flipCommandTest3() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/fruits.ppm Fruits";
    String input2 = "horizontal-flip Fruits";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);


    assertEquals("Command Executed Successfullyhorizontal-flip command format is not correct \n"
            + "Format: horizontal-flip image-name dest-image-name", out.toString());
  }

  /**
   * Command Test - Horizontal flip, Vertical flip an image where command given is wrong.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void flipCommandTest4() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/Koala.ppm Koala";
    String input2 = "vertical-flip Koala";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageController controller = new ImageControllerImpl(model, in, out, view);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);

    assertEquals("Command Executed Successfullyvertical-flip command format is not correct \n"
            + "Format: vertical-flip image-name dest-image-name", out.toString());
  }

  /**
   * Command Test -Apply greyscale to an image. (Wrong component)
   * @throws IOException - if file not accessible.
   */
  @Test
  public void greyscaleCommandTest1() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/fruits.ppm Fruits";
    String input2 = "greyscale grey-component Fruits greyFruits";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);


    assertEquals("Command Executed SuccessfullyUnrecognized Component", out.toString());
  }

  /**
   * Command Test -Apply greyscale to a ppm image and save as ppm image.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void greyscaleCommandTest2() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/fruits.ppm Fruits";
    String input2 = "greyscale Fruits greyFruits";
    String input3 = "save src/actualoutput/greyFruits.ppm greyFruits";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);

    ImageUtil imageUtil = new ImageUtil();
    Scanner sc1 = new Scanner(new FileInputStream("src/actualoutput/greyFruits.ppm"));
    ImageArray image1 = imageUtil.readPPM(sc1);
    Scanner sc = new Scanner(new FileInputStream("src/PPM/greyFruits.ppm"));
    ImageArray image2 = imageUtil.readPPM(sc);

    assertEquals(Arrays.stream(image2.getIA()).toArray(), Arrays.stream(image1.getIA()).toArray());
  }

  /**
   * Command Test - Apply greyscale to a ppm image and save it as jpeg.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void greyscaleCommandTest3() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/fruits.ppm Fruits";
    String input2 = "greyscale Fruits greyFruitsjpeg";
    String input3 = "save src/actualoutput/greyFruitsjpeg.jpeg greyFruitsjpeg";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);

    ImageUtil imageUtil = new ImageUtil();

    File file = new File("src/actualoutput/greyFruitsjpeg.jpeg");
    BufferedImage imageInput = ImageIO.read(file);
    ImageArray image1 = imageUtil.readJPEGPNGBMPFile(imageInput);
    File file2 = new File("src/JPEG/greyscaleFruitsjpeg.jpeg");
    BufferedImage imageInput2 = ImageIO.read(file2);
    ImageArray image2 = imageUtil.readJPEGPNGBMPFile(imageInput2);

    assertEquals(Arrays.stream(image2.getIA()).toArray(), Arrays.stream(image1.getIA()).toArray());
  }

  /**
   * Command Test - Apply greyscale to a ppm image and save it as png.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void greyscaleCommandTest4() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/fruits.ppm Fruits";
    String input2 = "greyscale Fruits greyFruitspng";
    String input3 = "save src/actualoutput/greyFruitspng.png greyFruitspng";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageController controller = new ImageControllerImpl(model, in, out, view);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);

    ImageUtil imageUtil = new ImageUtil();

    File file = new File("src/actualoutput/greyFruitspng.png");
    BufferedImage imageInput = ImageIO.read(file);
    ImageArray image1 = imageUtil.readJPEGPNGBMPFile(imageInput);
    File file2 = new File("src/PNG/greyscaleFruitspng.png");
    BufferedImage imageInput2 = ImageIO.read(file2);
    ImageArray image2 = imageUtil.readJPEGPNGBMPFile(imageInput2);

    assertEquals(Arrays.stream(image2.getIA()).toArray(), Arrays.stream(image1.getIA()).toArray());
  }

  /**
   * Command Test - Apply greyscale to a ppm image and save it as bmp.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void greyscaleCommandTest5() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/fruits.ppm Fruits";
    String input2 = "greyscale Fruits greyFruitsbmp";
    String input3 = "save src/actualoutput/greyFruitsbmp.bmp greyFruitsbmp";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);

    ImageUtil imageUtil = new ImageUtil();

    File file = new File("src/actualoutput/greyFruitsbmp.bmp");
    BufferedImage imageInput = ImageIO.read(file);
    ImageArray image1 = imageUtil.readJPEGPNGBMPFile(imageInput);
    File file2 = new File("src/BMP/greyscaleFruitsbmp.bmp");
    BufferedImage imageInput2 = ImageIO.read(file2);
    ImageArray image2 = imageUtil.readJPEGPNGBMPFile(imageInput2);

    assertEquals(Arrays.stream(image2.getIA()).toArray(), Arrays.stream(image1.getIA()).toArray());
  }

  /**
   * Command Test - Apply greyscale to image that doesn't exist.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void greyscaleCommandTest6() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/fruit.ppm Fruit";
    String input2 = "greyscale Fruit greyFruitsbmp";
    String input3 = "save src/actualoutput/greyFruitsbmp.bmp greyFruitsbmp";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);

    ImageUtil imageUtil = new ImageUtil();

    File file = new File("src/actualoutput/greyFruitsbmp.bmp");
    BufferedImage imageInput = ImageIO.read(file);
    ImageArray image1 = imageUtil.readJPEGPNGBMPFile(imageInput);
    File file2 = new File("src/BMP/greyscaleFruitsbmp.bmp");
    BufferedImage imageInput2 = ImageIO.read(file2);
    ImageArray image2 = imageUtil.readJPEGPNGBMPFile(imageInput2);

    assertEquals("File src/fruit.ppm not found!image-name not presentimage-name not present",
            out.toString());
  }

  /**
   * Command Test -rgb-split command on an image.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void rgbSplitCommandTest1() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/Koala.ppm Koala";
    String input2 = "rgb-split Koala rKoala gKoala bKoala";
    String input3 = "save src/rKoala.ppm rKoala";
    String input4 = "save src/gKoala.ppm gKoala";
    String input5 = "save src/bKoala.ppm bKoala";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageController controller = new ImageControllerImpl(model, in, out, view);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input4), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input5), out);
    assertEquals("Command Executed Successfully", out.toString());
  }

  /**
   * Command Test -rgb-split - wrong format.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void rgbSplitCommandTest2() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/Koala.ppm Koala";
    String input2 = "rgb-split Koala rKoala gKoala";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageController controller = new ImageControllerImpl(model, in, out, view);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    assertEquals("rgb-split command format is not correct \n"
            + "Format: rgb-split image-name dest-image-name-red "
            + "dest-image-name-green dest-image-name-blue", out.toString());
  }

  /**
   * Command Test -rgb-split - image not loaded.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void rgbSplitCommandTest3() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "rgb-split Koala rKoala gKoala bKoala";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageController controller = new ImageControllerImpl(model, in, out, view);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    assertEquals("image-name not present", out.toString());
  }

  /**
   * Command Test -rgb-combine command on three images.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void rgbCombineCommandTest1() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/Koala.ppm Koala";
    String input2 = "rgb-split Koala rKoala gKoala bKoala";
    String input3 = "rgb-combine combinedKoala rKoala gKoala bKoala";
    String input4 = "save src/combinedKoala.ppm combinedKoala";


    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input4), out);

    assertEquals("Command Executed Successfully", out.toString());
  }

  /**
   * Command Test -rgb-combine - images not present.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void rgbCombineCommandTest2() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/Koala.ppm Koala";
    String input2 = "rgb-split Koala rKoala gKoala bKoala";
    String input3 = "rgb-combine combinedKoala rKoala kkKoala bKoala";



    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageController controller = new ImageControllerImpl(model, in, out, view);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);


    assertEquals("image-name not present", out.toString());
  }

  /**
   * Command Test - brighten - images .
   * @throws IOException - if file not accessible.
   */
  @Test
  public void brightenCommandTest1() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/Koala.ppm Koala";
    String input2 = "brighten 15 Koala brightKoala";
    String input3 = "save src/brightKoala.ppm brightKoala";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageController controller = new ImageControllerImpl(model, in, out, view);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);


    assertEquals("Command Executed Successfully", out.toString());
  }

  /**
   * Command Test - darken the image.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void brightenCommandTest2() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/Koala.ppm Koala";
    String input2 = "brighten -15 Koala darkKoala";
    String input3 = "save src/darkKoala.ppm darkKoala";


    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);


    assertEquals("Command Executed Successfully", out.toString());
  }

  /**
   * Command Test - brighten - second part of command not integer.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void brightenCommandTest3() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/Koala.ppm Koala";
    String input2 = "brighten five Koala darkKoala";


    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageController controller = new ImageControllerImpl(model, in, out, view);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);


    assertEquals("The second part of the command should be a number", out.toString());
  }

  /**
   * Command Test - brighten - images (value more than 255).
   * @throws IOException - if file not accessible.
   */
  @Test
  public void brightenCommandTest4() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/Koala.ppm Koala";
    String input2 = "brighten 600 Koala brightKoala";
    String input3 = "save src/brightKoala.ppm brightKoala";


    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageController controller = new ImageControllerImpl(model, in, out, view);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);


    assertEquals("Command Executed Successfully", out.toString());
  }

  /**
   * Command Test - darken the image - < -255.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void brightenCommandTest5() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/Koala.ppm Koala";
    String input2 = "brighten -260 Koala darkKoala";
    String input3 = "save src/darkKoala.ppm darkKoala";


    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageController controller = new ImageControllerImpl(model, in, out, view);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);

    assertEquals("Command Executed Successfully", out.toString());
  }

  /**
   * Command Test - run-script where the script is right.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void runScriptCommandTest() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "run-script src/script.txt";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);

    assertEquals("Command Executed Successfully", out.toString());
  }

  /**
   * Command Test - run-script where the script is wrong.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void runScriptCommandTest2() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "run-script src/script2.txt";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);

    assertEquals("Command Executed Successfully", out.toString());
  }

  /**
   * Command Test - file not found.
   * @throws IOException - if file not accessible.
   */
  @Test(expected = NoSuchElementException.class)
  public void fileNotFoundCommandTest() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/kk.ppm kk";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageController controller = new ImageControllerImpl(model, in, out, view);
    controller.goAhead();

  }

  /**
   * Command Test - Apply sepia to a ppm image and save it as ppm.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void sepiaCommandTest1() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/fruits.ppm Fruits";
    String input2 = "sepia Fruits sepiaFruitsppm";
    String input3 = "save src/actualoutput/sepiaFruitsppm.ppm sepiaFruitsppm";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageController controller = new ImageControllerImpl(model, in, out, view);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);

    ImageUtil imageUtil = new ImageUtil();
    Scanner sc = new Scanner(new FileInputStream("src/actualoutput/sepiaFruitsppm.ppm"));
    ImageArray image1 = imageUtil.readPPM(sc);

    Scanner sc2 = new Scanner(new FileInputStream("src/PPM/greyFruits.ppm"));
    ImageArray image2 = imageUtil.readPPM(sc2);

    assertEquals(Arrays.stream(image2.getIA()).toArray(), Arrays.stream(image1.getIA()).toArray());
  }

  /**
   * Command Test - Apply sepia to a ppm image and save it as jpeg.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void sepiaCommandTest2() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/fruits.ppm Fruits";
    String input2 = "sepia Fruits sepiaFruitsjpeg";
    String input3 = "save src/actualoutput/sepiaFruitsjpeg.jpeg sepiaFruitsjpeg";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageController controller = new ImageControllerImpl(model, in, out, view);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);

    ImageUtil imageUtil = new ImageUtil();

    File file = new File("src/actualoutput/sepiaFruitsjpeg.jpeg");
    BufferedImage imageInput = ImageIO.read(file);
    ImageArray image1 = imageUtil.readJPEGPNGBMPFile(imageInput);
    File file2 = new File("src/JPEG/sepiaFruitsjpeg.jpeg");
    BufferedImage imageInput2 = ImageIO.read(file2);
    ImageArray image2 = imageUtil.readJPEGPNGBMPFile(imageInput2);

    assertEquals(Arrays.stream(image2.getIA()).toArray(), Arrays.stream(image1.getIA()).toArray());
  }

  /**
   * Command Test - Wrong sepia command.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void sepiaCommandTest5() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/fruits.ppm Fruits";
    String input2 = "sepi Fruits sepiaFruitsbmp";
    String input3 = "save src/actualoutput/sepiaFruitsbmp.bmp sepiaFruitsbmp";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);

    ImageUtil imageUtil = new ImageUtil();

    File file = new File("src/actualoutput/sepiaFruitsbmp.bmp");
    BufferedImage imageInput = ImageIO.read(file);
    ImageArray image1 = imageUtil.readJPEGPNGBMPFile(imageInput);
    File file2 = new File("src/BMP/sepiaFruitsbmp.bmp");
    BufferedImage imageInput2 = ImageIO.read(file2);
    ImageArray image2 = imageUtil.readJPEGPNGBMPFile(imageInput2);

    assertEquals("Command Executed SuccessfullyInvalid Command \n" +
            "Available commands:\n" +
            "load\n" +
            "save\n" +
            "vertical-flip\n" +
            "horizontal-flip\n" +
            "greyscale\n" +
            "rgb-split\n" +
            "brighten\n" +
            "rgb-combine\n" +
            "run-script\n" +
            "dither\n" +
            "blur\n" +
            "sharpen\n" +
            "sepiaimage-name not present", out.toString());
  }

  /**
   * Command Test - Apply blur to a ppm image and save it as ppm.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void blurCommandTest1() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/fruits.ppm Fruits";
    String input2 = "sepia Fruits blurFruitsppm";
    String input3 = "save src/actualoutput/blurFruitsppm.ppm blurFruitsppm";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);

    ImageUtil imageUtil = new ImageUtil();
    Scanner sc = new Scanner(new FileInputStream("src/actualoutput/blurFruitsppm.ppm"));
    ImageArray image1 = imageUtil.readPPM(sc);

    Scanner sc2 = new Scanner(new FileInputStream("src/PPM/greyFruits.ppm"));
    ImageArray image2 = imageUtil.readPPM(sc2);

    assertEquals(Arrays.stream(image2.getIA()).toArray(), Arrays.stream(image1.getIA()).toArray());
  }

  /**
   * Command Test - Apply blur to a ppm image and save it as png.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void blurCommandTest2() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/fruits.ppm Fruits";
    String input2 = "sepia Fruits blurFruitspng";
    String input3 = "save src/actualoutput/blurFruitspng.png blurFruitspng";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);

    ImageUtil imageUtil = new ImageUtil();

    File file = new File("src/actualoutput/blurFruitspng.png");
    BufferedImage imageInput = ImageIO.read(file);
    ImageArray image1 = imageUtil.readJPEGPNGBMPFile(imageInput);

    File file2 = new File("src/PNG/blurFruitspng.png");
    BufferedImage imageInput2 = ImageIO.read(file2);
    ImageArray image2 = imageUtil.readJPEGPNGBMPFile(imageInput2);

    assertEquals(Arrays.stream(image2.getIA()).toArray(), Arrays.stream(image1.getIA()).toArray());
  }

  /**
   * Command Test - Apply sharpen to a ppm image and save it as ppm.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void sharpenCommandTest1() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/fruits.ppm Fruits";
    String input2 = "sepia Fruits sharpenFruitsppm";
    String input3 = "save src/actualoutput/sharpenFruitsppm.ppm sharpenFruitsppm";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);

    ImageUtil imageUtil = new ImageUtil();
    Scanner sc = new Scanner(new FileInputStream("src/actualoutput/sharpenFruitsppm.ppm"));
    ImageArray image1 = imageUtil.readPPM(sc);

    Scanner sc2 = new Scanner(new FileInputStream("src/PPM/sharpenFruits.ppm"));
    ImageArray image2 = imageUtil.readPPM(sc2);

    assertEquals(Arrays.stream(image2.getIA()).toArray(), Arrays.stream(image1.getIA()).toArray());
  }

  /**
   * Command Test - Apply sharpen to a ppm image and save it as bmp.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void sharpenCommandTest2() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/fruits.ppm Fruits";
    String input2 = "sharpen Fruits sharpenFruitsbmp";
    String input3 = "save src/actualoutput/sharpenFruitsbmp.bmp sharpenFruitsbmp";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);

    ImageUtil imageUtil = new ImageUtil();

    Scanner sc = new Scanner(new FileInputStream("src/actualoutput/sharpenFruitsbmp.bmp"));
    ImageArray image1 = imageUtil.readPPM(sc);

    File file2 = new File("src/BMP/sharpenFruitsbmp.bmp");
    BufferedImage imageInput2 = ImageIO.read(file2);
    ImageArray image2 = imageUtil.readJPEGPNGBMPFile(imageInput2);

    assertEquals(Arrays.stream(image2.getIA()).toArray(), Arrays.stream(image1.getIA()).toArray());
  }

  /**
   * Command Test - Apply dither to a ppm image and save it as ppm.
   * @throws IOException - if file not accessible.
   */
  @Test
  public void ditherCommandTest1() throws IOException {
    InputStream in = null;
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    String input1 = "load src/fruits.ppm Fruits";
    String input2 = "dither Fruits ditherFruitsppm";
    String input3 = "save src/actualoutput/ditherFruitsppm.ppm ditherFruitsppm";

    in = new ByteArrayInputStream(input1.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input1), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input2), out);
    view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(input3), out);

    ImageUtil imageUtil = new ImageUtil();

    Scanner sc = new Scanner(new FileInputStream("src/actualoutput/ditherFruitsppm.ppm"));
    ImageArray image1 = imageUtil.readPPM(sc);

    Scanner sc2 = new Scanner(new FileInputStream("src/PPM/ditherFruits.ppm"));
    ImageArray image2 = imageUtil.readPPM(sc2);

    assertEquals(Arrays.stream(image2.getIA()).toArray(), Arrays.stream(image1.getIA()).toArray());
  }
}