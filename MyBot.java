package bots;
import elf_kingdom.*;


/**
 * This is an example for a bot.
 */
public class MyBot implements SkillzBot {
    public static int turns = 0;
    /**
     * Makes the bot run a single turn.
     *
     * @param game - the current game state.
     */
    @Override
    public void doTurn(Game game) {
        // Give orders to my elves.
        Elves.handleElves(game);
        // Give orders to my portals.
        Portals.handlePortals(game);
        turns++;
    }
}
