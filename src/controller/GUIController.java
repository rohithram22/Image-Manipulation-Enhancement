package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import controller.commands.Blur;
import controller.commands.Brighten;
import controller.commands.Dither;
import controller.commands.Greyscale;
import controller.commands.HorizontalFlip;
import controller.commands.Load;
import controller.commands.RGBCombine;
import controller.commands.RGBSplit;
import controller.commands.Save;
import controller.commands.Sepia;
import controller.commands.Sharpen;
import controller.commands.VerticalFlip;
import java.util.Objects;
import model.ImageModel;
import view.GUIView;
import utils.Constants;

/**
 * Controller for the GUI that extends features and ImageController interface.
 */
public class GUIController implements Features, ImageController {

  private ImageModel model;
  private GUIView view;
  private String loadedImage;

  /**
   * Constructor that sets the model and view.
   * @param model Model created
   * @param view View created
   * @throws IOException throws if file not found
   */
  public GUIController(ImageModel model, GUIView view) throws IOException {
    this.model = model;
    this.view = view;
    this.loadedImage = "image";
  }

  /**
   * Helper function to add features in the View class.
   * @throws IOException throws if file not found
   */
  private void setViewFeatures() throws IOException {
    this.view.addFeatures(this);
  }

  /**
   * Helper function to extracting required file path.
   * @param filePath Input filepath
   * @return Extracted filepath
   */
  private String filterImagePath(String filePath) {
    if (filePath == null || filePath.isBlank()) {
      return "";
    }
    String[] currentDirectoryPath = System.getProperty("user.dir").split("/");
    String currentDirectory = currentDirectoryPath[currentDirectoryPath.length - 1];
    return filePath.substring(filePath.lastIndexOf(currentDirectory)
            + currentDirectory.length() + 1);
  }

  /**
   * Sets the image in the GIU.
   * @throws IOException throws if file not found
   */
  private void setLoadedImage() throws IOException {
    BufferedImage image = model.viewImage(loadedImage);
    view.setImage(image);
  }

  @Override
  public void goAhead() throws IOException {
    setViewFeatures();
  }

  @Override
  public void loadImage() throws IOException {
    if (model.checkImageLoaded()) {
      view.newLoadSaveWarning();
    }
    String filePath = view.getFilePath("Open File");
    if (Objects.equals(filePath, "")) {
      return;
    }
    else {
      filePath = filterImagePath(filePath);
    }
    if (filePath.isBlank()) {
      return;
    }
    int dotIndex = filePath.lastIndexOf('.');
    String extension = filePath.substring(dotIndex + 1);
    if (!(extension.equals("ppm") || extension.equals("png") || extension.equals("bmp")
            || extension.equals("jpeg") || extension.equals("jpg"))) {
      view.invalidExtensionError();
      return;
    }
    String command = "load " + filePath + " " + loadedImage;
    Command cmd = new Load();
    String status = cmd.executeCommand(command, model);
    if (status.equals(Constants.COMMAND_EXECUTION_SUCCESSFUL)) {
      setLoadedImage();
    }
  }

  @Override
  public void saveImage() throws IOException {
    if (!model.checkImageLoaded()) {
      view.noImageErrorMessage();
      return;
    }
    String filePath = filterImagePath(view.getFilePath("Save File"));
    String command = "save " + filePath + " " + loadedImage;
    Command cmd = new Save();
    cmd.executeCommand(command, model);
  }

  @Override
  public void blurImage() throws IOException {
    if (!model.checkImageLoaded()) {
      view.noImageErrorMessage();
      return;
    }
    Command cmd = new Blur();
    String command = "blur " + loadedImage + " " + loadedImage;
    cmd.executeCommand(command, model);
    setLoadedImage();
  }

  @Override
  public void verticalFlip() throws IOException {
    if (!model.checkImageLoaded()) {
      view.noImageErrorMessage();
      return;
    }
    Command cmd = new VerticalFlip();
    String command = "vertical-flip " + loadedImage + " " + loadedImage;
    cmd.executeCommand(command, model);
    setLoadedImage();
  }

  @Override
  public void greyscaleImage() throws IOException {
    if (!model.checkImageLoaded()) {
      view.noImageErrorMessage();
      return;
    }
    Command cmd = new Greyscale();
    String command = "greyscale " + loadedImage + " " + loadedImage;
    cmd.executeCommand(command, model);
    setLoadedImage();
  }

  @Override
  public void sepia() throws IOException {
    if (!model.checkImageLoaded()) {
      view.noImageErrorMessage();
      return;
    }
    Command cmd = new Sepia();
    String command = "sepia " + loadedImage + " " + loadedImage;
    cmd.executeCommand(command, model);
    setLoadedImage();
  }

  @Override
  public void dither() throws IOException {
    if (!model.checkImageLoaded()) {
      view.noImageErrorMessage();
      return;
    }
    Command cmd = new Dither();
    String command = "dither " + loadedImage + " " + loadedImage;
    cmd.executeCommand(command, model);
    setLoadedImage();
  }

  @Override
  public void sharpen() throws IOException {
    if (!model.checkImageLoaded()) {
      view.noImageErrorMessage();
      return;
    }
    Command cmd = new Sharpen();
    String command = "sharpen " + loadedImage + " " + loadedImage;
    cmd.executeCommand(command, model);
    setLoadedImage();
  }

  @Override
  public void horizontalFlip() throws IOException {
    if (!model.checkImageLoaded()) {
      view.noImageErrorMessage();
      return;
    }
    Command cmd = new HorizontalFlip();
    String command = "horizontal-flip " + loadedImage + " " + loadedImage;
    cmd.executeCommand(command, model);
    setLoadedImage();
  }

  @Override
  public void brightenImage() throws IOException {
    if (!model.checkImageLoaded()) {
      view.noImageErrorMessage();
      return;
    }
    Integer brightenValue = view.inputBrighten();
    if (brightenValue == 0) {
      return;
    }
    String command = "brighten " + brightenValue + " " + loadedImage + " " + loadedImage;
    Command cmd = new Brighten();
    cmd.executeCommand(command, model);
    setLoadedImage();
  }

  @Override
  public void rgbCombine() throws IOException {
    List<String> rgbPaths = view.rgbFileRead();
    if (rgbPaths.size() != 3) {
      return;
    }
    String redImagePath = filterImagePath(rgbPaths.get(0));
    String greenImagePath = filterImagePath(rgbPaths.get(1));
    String blueImagePath = filterImagePath(rgbPaths.get(2));
    Command cmd = new Load();
    String command = "load " + redImagePath + " rImage";
    cmd.executeCommand(command, model);
    command = "load " + greenImagePath + " gImage";
    cmd.executeCommand(command, model);
    command = "load " + blueImagePath + " bImage";
    cmd.executeCommand(command, model);
    Command cmd2 = new RGBCombine();
    command = "rgb-combine " + loadedImage + " rImage gImage bImage";
    cmd2.executeCommand(command, model);
    setLoadedImage();
  }

  @Override
  public void rgbSplit() throws IOException {
    if (!model.checkImageLoaded()) {
      view.noImageErrorMessage();
      return;
    }
    String command = "rgb-split " + loadedImage + " redImage greenImage blueImage";
    Command cmd = new RGBSplit();
    cmd.executeCommand(command, model);
    BufferedImage redImage = model.viewImage("redImage");
    if (view.rgbSplitPopUp(redImage, "Red Greyscale")) {
      String filePath = filterImagePath(view.getFilePath("Save File"));
      String command1 = "save " + filePath + " redImage";
      Command cmd1 = new Save();
      cmd1.executeCommand(command1, model);
    }
    BufferedImage greenImage = model.viewImage("greenImage");
    if (view.rgbSplitPopUp(greenImage, "Green Greyscale")) {
      String filePath = filterImagePath(view.getFilePath("Save File"));
      String command2 = "save " + filePath + " greenImage";
      Command cmd2 = new Save();
      cmd2.executeCommand(command2, model);
    }
    BufferedImage blueImage = model.viewImage("blueImage");
    if (view.rgbSplitPopUp(blueImage, "Blue Greyscale")) {
      String filePath = filterImagePath(view.getFilePath("Save File"));
      String command3 = "save " + filePath + " blueImage";
      Command cmd3 = new Save();
      cmd3.executeCommand(command3, model);
    }
  }

  @Override
  public void quit() throws IOException {
    if (view.quitGUI()) {
      System.exit(0);
    }
  }
}
