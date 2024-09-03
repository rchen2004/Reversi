package cs3500.reversi.provider.model;

/**
 * The ModelFeatures interface outlines the functionalities to be implemented by classes
 * representing a game model. Classes implementing this interface should provide a method
 * to notify a controller about changes in the game state.
 */
public interface ModelFeatures {

  /**
   * Notifies the controller about changes in the game state.
   * Controllers implementing this interface will receive notifications when there are
   * updates in the game model.
   */
  void notifyControllerAboutChange();
}
