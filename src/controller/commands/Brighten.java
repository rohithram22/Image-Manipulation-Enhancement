package controller.commands;

import java.io.IOException;

import controller.Command;
import model.ImageModel;
import utils.Constants;

/**
 * This class implements Brighten command.
 */
public class Brighten implements Command {
  @Override
  public String checkCommand(String command, ImageModel model) {

    String[] commandParts = command.split(" ");
    if (commandParts.length != 4) {
      return "brighten command format is not correct \n"
              + Constants.BRIGHTEN_FORMAT;
    }
    try {
      Integer.parseInt(commandParts[1]);
    }
    catch (NumberFormatException nfe) {
      return "The second part of the command should be a number";
    }
    if (!model.getReferenceNames().contains(commandParts[2])) {
      return Constants.IMAGE_NAME_NOT_PRESENT;
    }
    else if (model.getReferenceNames().contains(commandParts[3])) {
      return Constants.EXISTING_DESTINATION_IMAGE_NAME;
    }
    return Constants.VALID_COMMAND_FORMAT;
  }

  @Override
  public String executeCommand(String command, ImageModel model) throws IOException {
    String[] commandParts = command.split(" ");
    model.brightenImage(Integer.parseInt(commandParts[1]), commandParts[2], commandParts[3]);
    return Constants.COMMAND_EXECUTION_SUCCESSFUL;
  }
}
