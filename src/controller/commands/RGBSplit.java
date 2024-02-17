package controller.commands;

import java.io.IOException;

import controller.Command;
import model.ImageModel;
import utils.Constants;

/**
 * This class implements RGB Split command.
 */
public class RGBSplit implements Command {

  @Override
  public String checkCommand(String command, ImageModel model) {
    String[] commandParts = command.split(" ");
    if (commandParts.length != 5) {
      return "rgb-split command format is not correct \n"
              + Constants.RGB_SPLIT_FORMAT;
    }
    else if (!model.getReferenceNames().contains(commandParts[1])) {
      return Constants.IMAGE_NAME_NOT_PRESENT;
    }
    else if (model.getReferenceNames().contains(commandParts[2])
            || model.getReferenceNames().contains(commandParts[3])
            || model.getReferenceNames().contains(commandParts[4])) {
      return Constants.EXISTING_DESTINATION_IMAGE_NAME;
    }
    return Constants.VALID_COMMAND_FORMAT;
  }

  @Override
  public String executeCommand(String command, ImageModel model) throws IOException {
    String[] commandParts = command.split(" ");
    model.greyscaleImage("Red", commandParts[1], commandParts[2]);
    model.greyscaleImage("Green", commandParts[1], commandParts[3]);
    model.greyscaleImage("Blue", commandParts[1], commandParts[4]);
    return Constants.COMMAND_EXECUTION_SUCCESSFUL;
  }
}
