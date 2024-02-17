package controller.commands;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.Command;
import model.ImageModel;
import utils.Constants;

/**
 * This class implements Save command.
 */
public class Save implements Command {
  @Override
  public String checkCommand(String command, ImageModel model) {
    String[] commandParts = command.split(" ");
    int dotIndex = commandParts[1].lastIndexOf('.');
    String extension = commandParts[1].substring(dotIndex + 1);
    if (commandParts.length != 3) {
      return "save command format is not correct \n"
              + Constants.SAVE_FORMAT;
    }
    else if (!(extension.equals("jpeg") || extension.equals("png") || extension.equals("ppm")
            || extension.equals("jpg") || extension.equals("bmp"))) {
      return Constants.INCORRECT_EXTENSION;
    }
    else if (!model.getReferenceNames().contains(commandParts[2])) {
      return Constants.IMAGE_NAME_NOT_PRESENT;
    }
    return Constants.VALID_COMMAND_FORMAT;
  }

  @Override
  public String executeCommand(String command, ImageModel model) throws IOException {
    String[] commandParts = command.split(" ");
    int dotIndex = commandParts[1].lastIndexOf('.');
    String extension = commandParts[1].substring(dotIndex + 1);
    if (extension.equals("ppm")) {
      try {
        StringBuilder ppmFormat = model.saveImage(commandParts[1], commandParts[2]);
        FileOutputStream fos = new FileOutputStream(commandParts[1]);
        fos.write(new String(ppmFormat).getBytes());
        fos.close();
      }
      catch (FileNotFoundException e) {
        return Constants.INCORRECT_FILE_PATH;
      }
    }
    else {
      BufferedImage image = model.saveDFImage(commandParts[1], commandParts[2]);
      try {
        File output = new File(commandParts[1]);
        FileOutputStream fos = new FileOutputStream(commandParts[1]);
        ImageIO.write(image, extension.toUpperCase(), output);
      }
      catch (FileNotFoundException e) {
        return Constants.INCORRECT_FILE_PATH;
      }
    }
    return Constants.COMMAND_EXECUTION_SUCCESSFUL;
  }
}
