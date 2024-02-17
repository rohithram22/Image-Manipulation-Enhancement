package controller.commands;

import java.io.IOException;

import controller.Command;
import model.ImageModel;
import utils.Constants;

/**
 * This class implements Sharpen command.
 */
public class Sharpen implements Command {
  @Override
  public String checkCommand(String command, ImageModel model) {
    String[] commandParts = command.split(" ");
    if (commandParts.length != 3) {
      return "sharpen command format is not correct \n"
              + Constants.SHARPEN_FORMAT;
    }
    else if (!model.getReferenceNames().contains(commandParts[1])) {
      return Constants.IMAGE_NAME_NOT_PRESENT;
    }
    else if (model.getReferenceNames().contains(commandParts[2])) {
      return Constants.EXISTING_DESTINATION_IMAGE_NAME;
    }
    return Constants.VALID_COMMAND_FORMAT;
  }

  @Override
  public String executeCommand(String command, ImageModel model) throws IOException {
    String[] commandParts = command.split(" ");
    model.sharpenImage(commandParts[1], commandParts[2]);
    return Constants.COMMAND_EXECUTION_SUCCESSFUL;
  }
}
