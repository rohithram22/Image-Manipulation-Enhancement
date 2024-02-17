package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import controller.Features;

/**
 * View of Graphical User Interface.
 */
public interface GUIView {

  /**
   * Gets the filepath for loading or saving.
   * @param option Open or Save
   * @return String filepath
   */
  String getFilePath(String option);

  /**
   * Sets the image in the GUI.
   * @param image Loaded image.
   * @throws IOException throws if file not found
   */
  void setImage(BufferedImage image) throws IOException;

  /**
   * Gets the brighten value through the slider.
   * @return Brighten value
   */
  Integer inputBrighten();

  /**
   * Gets three files for executing RGB combine function.
   * @return List of three filepath
   */
  List<String> rgbFileRead();

  /**
   * Displays all three images and asks the user whether the images are to be saved.
   * @param image Split images
   * @param imageType Red, green, blue
   * @return True or False
   */
  boolean rgbSplitPopUp(BufferedImage image, String imageType);

  /**
   * Error thrown when the user tries to save without loading any image.
   */
  void noImageErrorMessage();

  /**
   * Warning displayed when the user tries to load a new image
   * when already an image is in the GUI.
   */
  void newLoadSaveWarning();

  /**
   * Error thrown when file extension is invalid.
   */
  void invalidExtensionError();

  /**
   * Pop-up asking whether the user wants to quit the program.
   * @return True or False
   */
  boolean quitGUI();

  /**
   * Adds features to the GUI.
   * @param features New feature
   * @throws IOException throws if file not found
   */
  void addFeatures(Features features) throws IOException;
}
