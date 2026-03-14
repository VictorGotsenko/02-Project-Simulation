package simulation.entities;

import simulation.model.GameMap;
import simulation.model.Point;
import simulation.servise.SearchServiceImpl;

import java.util.ArrayDeque;
import java.util.Deque;

import static simulation.utils.UnicodeSprites.UNICODE_PREDATOR;

public final class Predator extends Creature {
    private final String typeOfFood = "Herbivore";
    private final int attackPower = 5;

    private final SearchServiceImpl search;
    private GameMap gameMap;
    private Deque<Point> currentPathToTarget;

    public Predator(GameMap gameMap) {
        this.gameMap = gameMap;
        this.simbol = UNICODE_PREDATOR;
        this.hp = 10;
        this.speed = 4;
        this.search = new SearchServiceImpl(gameMap);
        this.currentPathToTarget = new ArrayDeque();
    }

    @Override
    public void makeMove(Point currentPoint) {
        if (!gameMap.hasCreature(this)) {
            return;
        }

        for (int i = 0; i < speed; i++) {
            if (this.hp <= 0) {
                gameMap.creatureKilled(currentPoint);
                this.gameMap.display();
                break;
            }

            if (currentPathToTarget.isEmpty()) {
                currentPathToTarget.addAll(search.findPathToTarget(currentPoint, typeOfFood));
                if (currentPathToTarget.isEmpty()) {
                    System.out.println("No herbivore! Game is over");
                    System.exit(0);
                }
            }

            // meal is near, just eat it or attack
            if (currentPathToTarget.size() == 1) {
                Point prey = currentPathToTarget.pollFirst();
                Herbivore herbivore = (Herbivore) gameMap.getEntity(prey);
                int calories = herbivore.getCalorie();
                attack(herbivore);

                // if hp<=0 eat rabbit, hp++ and go where
                if (herbivore.getHp() <= 0) {
                    gameMap.creatureStep(currentPoint, new Point(prey.x, prey.y), this);
                    currentPoint = prey;
                    this.hp += calories;   // eaten
                }
                this.gameMap.display();
                continue;
            }
            Point pointToRabbit = this.currentPathToTarget.pollFirst();
            gameMap.creatureStep(currentPoint, pointToRabbit, this);
            this.gameMap.display();
            currentPoint = pointToRabbit;

            this.hp -= 1;
        }
        currentPathToTarget.clear();
    }

    private void attack(Herbivore herbivore) {
        int hp = herbivore.getHp();
        herbivore.setHp(hp - attackPower);
        herbivore.setAttacked(true);
    }
}
