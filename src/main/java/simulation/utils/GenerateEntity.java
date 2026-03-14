package simulation.utils;

import simulation.entities.Entity;
import simulation.entities.Grass;
import simulation.entities.Herbivore;
import simulation.entities.Predator;
import simulation.entities.Rock;
import simulation.model.GameMap;

import java.util.Random;

public final class GenerateEntity {

    public Entity getRandEntity(GameMap gameMap) {
        int r = new Random().nextInt(gameMap.getX());

        switch (r) {
            case 0 -> {
                return new Rock();
            }
            case 1 -> {
                return new Grass();
            }
            case 3 -> {
                return new Herbivore(gameMap);
            }
            case 8 -> {
                return new Predator(gameMap);
            }
            default -> {
                return null;
            }
        }
    }
}
