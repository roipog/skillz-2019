package bots;

import elf_kingdom.Game;
import elf_kingdom.Portal;

/**
 * class that controls the portals
 */
class Portals {

	/**
	 * Gives orders to my portals.
	 *
	 * @param game - the current game state
	 */
	static void handlePortals(Game game) {

		for (int i = 0; i < game.getMyPortals().length; i++) {

			if (Functions.enemyInRange(game, 3000)) {
				if (game.getMyPortals()[i].distance(game.getMyCastle()) < 3000 && game.getMyManaFountains().length > 0
						|| game.getMyMana() > 180) {
					game.getMyPortals()[i].summonIceTroll();
				}
			}
			if (Functions.enemyInRange(game, 3000) == false && game.getMyMana() > 160) {
				game.getMyPortals()[i].summonLavaGiant();
			}
		}
	}
}