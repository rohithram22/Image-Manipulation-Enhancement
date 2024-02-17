package controller;

import java.io.IOException;

import model.ImageModel;

/**
 * This is an interface for the command.
 */
public interface Command {

  /**
   * Checks whether format of command is right.
   * @param command Command
   * @param model Model created
   * @return String whether the command is right or wrong
   */
  String checkCommand(String command, ImageModel model);

  /**
   * Executes the command.
   * @param command command given
   * @param model model created
   * @return String when command is executed
   * @throws IOException throws error when file is not found
   */
  String executeCommand(String command, ImageModel model) throws IOException;

}
