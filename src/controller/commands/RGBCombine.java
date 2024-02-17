package controller.commands;

import java.io.IOException;

import controller.Command;
import model.ImageModel;
import utils.Constants;

/**
 * This class implements RGB combine command.
 */
public class RGBCombine implements Command {
  @Override
  public String checkCommand(String command, ImageModel model) {

    String[] commandParts = command.split(" ");
    if (commandParts.length != 5) {
      return "rgb-combine command format is not correct \n"
              + Constants.RGB_COMBINE_FORMAT;
    }
    else if (model.getReferenceNames().contains(commandParts[1])) {
      return Constants.EXISTING_DESTINATION_IMAGE_NAME;
    }
    else if ((!model.getReferenceNames().contains(commandParts[2]))
            || (!model.getReferenceNames().contains(commandParts[3]))
            || (!model.getReferenceNames().contains(commandParts[4]))) {
      return Constants.IMAGE_NAME_NOT_PRESENT;
    }
    return Constants.VALID_COMMAND_FORMAT;
  }

  @Override
  public String executeCommand(String command, ImageModel model) throws IOException {
    String[] commandParts = command.split(" ");
    model.combineRGBImage(commandParts[2], commandParts[3], commandParts[4], commandParts[1]);
    return Constants.COMMAND_EXECUTION_SUCCESSFUL;
  }
}
