package cs3500.reversi.model;

/**
 * Represents an observer in the observer pattern.
 */
public interface Observer {

  /**
   * Notifies the observer of a change in the model.
   */
  void notice();

}
