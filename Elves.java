package bots;

import elf_kingdom.Elf;
import elf_kingdom.Game;
import elf_kingdom.Location;

/**
 * class that controls the elves
 */
class Elves {

	/**
	 * Gives orders to my elves.
	 *
	 * @param game - the current game state
	 */
	static void handleElves(Game game) {

		Elf[] elves = game.getMyLivingElves();
		// 0 mana or enemyNotClose and 3< numfMana

		if (!Functions.enemyInRange(game, 3000) && game.getMyManaFountains().length != game.getEnemyManaFountains().length
				&& game.getMyManaFountains().length < 4) {
			Functions.ManapreparationMode(game);
		}

		if (!Functions.enemyInRange(game, 3000)
				&& (game.getMyManaFountains().length == game.getEnemyManaFountains().length
						|| game.getMyManaFountains().length > 4)
				&& Functions.howMany(game.getMyCastle().location, game.getMyPortals(), 10000) <= 2) {
			Functions.PortalPreparation(game);
		}

		if (Functions.enemyInRange(game, 3000)) {
			Functions.defensiveMode(game, 3000);
		}
	}
}
