package cs3500.reversi.provider.view.textual;

import java.io.IOException;

/**
 * This interface represents a textual view for rendering game-related information.
 */
public interface TextualView {

  /**
   * Renders the game-related information in textual format.
   *
   * @throws IOException if there's an error while rendering the content.
   */
  void render() throws IOException;
}

