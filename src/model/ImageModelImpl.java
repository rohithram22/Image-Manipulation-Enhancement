package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import utils.Constants;

/**
 * This class is an implementation of the Model created.
 */
public class ImageModelImpl implements ImageModel {

  private final Map<String, ImageArray> fileReferences  =  new HashMap<String, ImageArray>();

  private Integer getMaxValue(ImageArray imageArray) {
    List<Integer>[][] image = imageArray.getIA();
    int maxValue = Collections.max(image[0][0]);
    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[0].length; j++) {
        maxValue = Collections.max(image[i][j]);
        if (maxValue == 255) {
          return maxValue;
        }
      }
    }
    return maxValue;
  }

  @Override
  public String checkComponent(String component) {

    if (component.equals("red-component")) {
      return "Red";
    }
    else if (component.equals("blue-component")) {
      return "Blue";
    }
    else if (component.equals("green-component")) {
      return "Green";
    }
    else if (component.equals("value-component")) {
      return "Value";
    }
    else if (component.equals("intensity-component")) {
      return "Intensity";
    }
    else if (component.equals("luma-component")) {
      return "Luma";
    }
    else {
      return "Unrecognized Component";
    }
  }

  @Override
  public List<String> getReferenceNames() {
    List<String> keys = new ArrayList<>();
    keys.addAll(fileReferences.keySet());
    return keys;
  }

  @Override
  public boolean checkImageLoaded() {
    return fileReferences.containsKey("image");
  }

  @Override
  public boolean loadImage(Scanner sc, String referenceName) throws IOException {
    ImageUtil img = new ImageUtil();
    ImageArray image = img.readPPM(sc);
    if (image != null) {
      fileReferences.put(referenceName, image);
      return true;
    }
    return false;
  }

  @Override
  public void loadImage(BufferedImage imageInput, String referenceName) throws IOException {
    ImageUtil img = new ImageUtil();
    ImageArray newImage = img.readJPEGPNGBMPFile(imageInput);
    fileReferences.put(referenceName, newImage);
  }

  @Override
  public StringBuilder saveImage(String filePath, String referenceName) throws IOException {
    ImageUtil img = new ImageUtil();
    ImageArray image = fileReferences.get(referenceName);

    return img.createPPMFile("P3", image.getIA()[0].length, image.getIA().length,
            getMaxValue(image), image, filePath);
  }

  @Override
  public BufferedImage saveDFImage(String filePath, String referenceName) throws IOException {
    ImageUtil img = new ImageUtil();
    ImageArray imageArray = fileReferences.get(referenceName);
    return img.createImageFile(imageArray);
  }

  @Override
  public void flipImage(String flipOption, String imageName,
                        String newImageName) throws IOException {
    ImageUtil img =  new ImageUtil();
    ImageArray image = fileReferences.get(imageName);
    ImageArray newImage = img.flipping(flipOption, image.getIA()[0].length, image.getIA().length,
            image);
    fileReferences.put(newImageName, newImage);
  }

  @Override
  public void greyscaleImage(String componentOption, String imageName,
                             String newImageName) throws IOException {
    ImageUtil img = new ImageUtil();
    ImageArray image = fileReferences.get(imageName);
    ImageArray newImage = img.greyscale(componentOption, image.getIA()[0].length,
            image.getIA().length, image);
    fileReferences.put(newImageName, newImage);
  }

  @Override
  public void brightenImage(int value, String imageName,
                            String newImageName) throws IOException {
    ImageUtil img = new ImageUtil();
    ImageArray image = fileReferences.get(imageName);
    ImageArray newImage = img.brightenDarken(value, image.getIA()[0].length,
            image.getIA().length, image);
    fileReferences.put(newImageName, newImage);
  }

  @Override
  public void combineRGBImage(String rComponent, String gComponent,
                              String bComponent, String newImageName) throws IOException {
    ImageUtil img = new ImageUtil();
    ImageArray rImage = fileReferences.get(rComponent);
    ImageArray gImage = fileReferences.get(gComponent);
    ImageArray bImage = fileReferences.get(bComponent);
    ImageArray newImage = img.combineComponents(rImage, gImage, bImage, rImage.getIA()[0].length,
            rImage.getIA().length);
    fileReferences.put(newImageName, newImage);
  }

  @Override
  public void ditherImage(String imageName, String newImageName) {
    ImageUtil img = new ImageUtil();
    ImageArray image = fileReferences.get(imageName);
    ImageArray newImage = img.dithering(image, image.getIA()[0].length, image.getIA().length);
    fileReferences.put(newImageName, newImage);
  }

  @Override
  public void newGreyscaleImage(String imageName, String newImageName) throws IOException {
    ImageUtil img = new ImageUtil();
    ImageArray imageArray = fileReferences.get(imageName);
    ImageArray newImage = img.colorTransformation(Constants.GREYSCALE_FILTER, imageArray,
            imageArray.getIA()[0].length, imageArray.getIA().length);
    fileReferences.put(newImageName, newImage);
  }

  @Override
  public void sepiaImage(String imageName, String newImageName) throws IOException {
    ImageUtil img = new ImageUtil();
    ImageArray imageArray = fileReferences.get(imageName);
    ImageArray newImage = img.colorTransformation(Constants.SEPIA_FILTER, imageArray,
            imageArray.getIA()[0].length, imageArray.getIA().length);
    fileReferences.put(newImageName, newImage);
  }

  @Override
  public BufferedImage viewImage(String referenceName) throws IOException {
    ImageUtil img = new ImageUtil();
    return img.createImageFile(fileReferences.get(referenceName));
  }

  @Override
  public void blurImage(String imageName, String newImageName) throws IOException {
    ImageUtil img = new ImageUtil();
    ImageArray imageArray = fileReferences.get(imageName);
    ImageArray newImage = img.filterImage(imageArray, Constants.BLUR_FILTER,
            imageArray.getIA()[0].length, imageArray.getIA().length);
    fileReferences.put(newImageName, newImage);
  }

  @Override
  public void sharpenImage(String imageName, String newImageName) throws IOException {
    ImageUtil img = new ImageUtil();
    ImageArray imageArray = fileReferences.get(imageName);
    ImageArray newImage = img.filterImage(imageArray, Constants.SHARPEN_FILTER,
            imageArray.getIA()[0].length, imageArray.getIA().length);
    fileReferences.put(newImageName, newImage);
  }

  @Override
  public boolean quitCommand(String checkCommand) {
    return checkCommand.equals("quit");
  }
}
