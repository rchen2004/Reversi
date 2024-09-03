package cs3500.reversi.provider.player;

import java.util.Optional;

import cs3500.reversi.model.AxialCoordinate;
import cs3500.reversi.model.FallibleReversiStrategy;
import cs3500.reversi.model.Player;
import cs3500.reversi.model.ReadOnlyReversi;
import cs3500.reversi.model.Reversi;
import cs3500.reversi.provider.model.AdapterRoReversiModel;
import cs3500.reversi.provider.model.Coordinate;

/**
 * The adapter class for the providers ReversiStrategy class.
 */
public class AdapterStrategy implements FallibleReversiStrategy {
  private final ReversiStrategy strat;

  private final AdapterRoReversiModel RorModel;

  /**
   * Constructs an adapter for the providers ReversiStrategy class.
   * @param strat The strategy to be adapted.
   * @param rorModel The model to be adapted.
   */
  public AdapterStrategy(ReversiStrategy strat, Reversi rorModel) {
    this.strat = strat;
    this.RorModel = new AdapterRoReversiModel(rorModel);
  }

  /**
   *  Chooses a move for the given player in the given model.
   * @param model  The ReadOnlyReversi model representing the current state of the game.
   * @param player The player for whom the strategy is choosing a move.
   * @return An optional containing the chosen move, or empty if no move is possible.
   */
  @Override
  public Optional<AxialCoordinate> chooseMove(ReadOnlyReversi model, Player player) {
    Optional<Coordinate> coords = strat.chooseMove(RorModel);
    return Optional.of(new AxialCoordinate(coords.get().getQ(), coords.get().getR()));
  }
}
