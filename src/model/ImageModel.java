package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Interface for model created, it will contain all the functions
 * that the model can execute.
 */
public interface ImageModel {

  /**
   * checks for component.
   * @param component value
   * @return String returning output
   */
  String checkComponent(String component);

  /**
   * Gets reference name from map.
   * @return reference name
   */
  List<String> getReferenceNames();

  /**
   * Checks whether the image has been loaded.
   * @return True or False
   */
  boolean checkImageLoaded();

  /**
   * Loads PPM image.
   * @param sc Scanner
   * @param referenceName name in map
   * @return True or false
   * @throws IOException throws exception if file not exists
   */
  boolean loadImage(Scanner sc, String referenceName) throws IOException;

  /**
   * Loads JPEG/PNG/BMP Image.
   * @param imageInput Input image
   * @param referenceName name in map
   * @throws IOException throws exception if file not exists
   */
  void loadImage(BufferedImage imageInput, String referenceName) throws IOException;

  /**
   * Saves PPM image.
   * @param filePath Path to be saved at
   * @param referenceName Name in map
   * @return String
   * @throws IOException throws exception if file not exists
   */
  StringBuilder saveImage(String filePath, String referenceName) throws IOException;

  /**
   * Saves JPEG/PNG/BMP image.
   * @param filePath Filepath of image to be saved at
   * @param referenceName name in map
   * @return String
   * @throws IOException throws exception if file not exists
   */
  BufferedImage saveDFImage(String filePath, String referenceName) throws IOException;

  /** Flips an image.
   * @param flipOption Horizontal or vertical
   * @param imageName Name in map
   * @param newImageName Name of new image
   * @throws IOException throws exception if file not exists
   */
  void flipImage(String flipOption, String imageName, String newImageName) throws IOException;

  /**
   * Converts an image to Greyscale image.
   * @param componentOption Sepia or Luma
   * @param imageName Name of image
   * @param newImageName Name of new image
   * @throws IOException throws exception if file not exists
   */
  void greyscaleImage(String componentOption, String imageName,
                      String newImageName) throws IOException;

  /**
   * Brightens or darkens the image.
   * @param value Int value
   * @param imageName Name of image
   * @param newImageName Name of new image
   * @throws IOException throws exception if file not exists
   */
  void brightenImage(int value, String imageName, String newImageName) throws IOException;

  /**
   * Combines red green blue image.
   * @param rComponent Red image
   * @param gComponent Green image
   * @param bComponent Blue image
   * @param newImageName Name of new combined image
   * @throws IOException throws exception if file not exists
   */
  void combineRGBImage(String rComponent, String gComponent,
                       String bComponent, String newImageName) throws IOException;

  /**
   * Dithers an image.
   * @param imageName Name of image
   * @param newImageName Name of new image
   */
  void ditherImage(String imageName, String newImageName);

  /**
   * Blurs an image.
   * @param imageName Name of image
   * @param newImageName Name if new image
   * @throws IOException throws exception if file not exists
   */
  void blurImage(String imageName, String newImageName) throws IOException;

  /**
   * Sharpens the image.
   * @param imageName Name of image
   * @param newImageName Name of new image
   * @throws IOException throws exception if file not exists
   */
  void sharpenImage(String imageName, String newImageName) throws IOException;

  /**
   * New greyscale image.
   * @param imageName Name of image
   * @param newImageName Name of new image
   * @throws IOException throws exception if file not exists
   */
  void newGreyscaleImage(String imageName, String newImageName) throws IOException;

  /**
   * Changes image to sepia.
   * @param imageName Name of image
   * @param newImageName Name of new image
   * @throws IOException throws exception if file not exists
   */
  void sepiaImage(String imageName, String newImageName) throws IOException;

  /**
   * Views an image.
   * @param referenceName Name of the image in map
   * @return Image in the form of BufferedImage
   * @throws IOException throws if file not found
   */
  BufferedImage viewImage(String referenceName) throws IOException;

  /**
   * Quits the program.
   * @param checkCommand checks the command
   * @return True or false
   */
  boolean quitCommand(String checkCommand);
}
