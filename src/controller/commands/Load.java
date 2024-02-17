package controller.commands;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.imageio.ImageIO;

import controller.Command;
import model.ImageModel;
import utils.Constants;

/**
 * This class implements Load command.
 */
public class Load implements Command {
  @Override
  public String checkCommand(String command, ImageModel model) {

    String[] commandParts = command.split(" ");
    int dotIndex = commandParts[1].lastIndexOf('.');
    String extension = commandParts[1].substring(dotIndex + 1);
    if (commandParts.length != 3) {
      return "load command format is not correct \n "
              + Constants.LOAD_FORMAT;
    }
    else if (!(extension.equals("jpeg") || extension.equals("png") || extension.equals("ppm")
            || extension.equals("jpg") || extension.equals("bmp") )) {
      return Constants.INCORRECT_EXTENSION;
    }
    else if (model.getReferenceNames().contains(commandParts[2])) {
      return "image-name already used by another image";
    }
    return Constants.VALID_COMMAND_FORMAT;
  }

  @Override
  public String executeCommand(String command, ImageModel model) throws IOException {
    String[] commandParts =  command.split(" ");
    Scanner sc = null;
    BufferedImage imageInput = null;
    Path path = Paths.get(commandParts[1]);
    String filename = path.getFileName().toString();
    int dotIndex = filename.lastIndexOf('.');
    String extension = filename.substring(dotIndex + 1);

    try {
      if (extension.equals("ppm")) {
        sc = new Scanner(new FileInputStream(commandParts[1]));
      }
      else if (extension.equals("jpeg") || extension.equals("png") ||
              extension.equals("jpg") || extension.equals("bmp")) {
        File file = new File(commandParts[1]);
        imageInput = ImageIO.read(file);
      }

    }
    catch (FileNotFoundException e) {
      return "File " + commandParts[1] + " not found!";

    }
    if (extension.equals("ppm")) {
      boolean opSuccessful = model.loadImage(sc, commandParts[2]);
      if (!opSuccessful) {
        return Constants.INVALID_PPM_FORMAT;
      }
    }
    else {
      model.loadImage(imageInput, commandParts[2]);
    }

    return Constants.COMMAND_EXECUTION_SUCCESSFUL;
  }

}
