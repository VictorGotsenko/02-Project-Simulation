package simulation.entities;

import simulation.model.GameMap;
import simulation.model.Point;
import simulation.servise.SearchServiceImpl;

import java.util.ArrayDeque;
import java.util.Deque;

import static simulation.utils.UnicodeSprites.UNICODE_RABBIT;

public final class Herbivore extends Creature {

    private final String typeOfFood = "Grass";
    private final SearchServiceImpl search;
    private GameMap gameMap;
    private Deque<Point> currentPathToTarget;
    private boolean isAttacked = false;
    private final int calorie = 4;


    public Herbivore(GameMap gameMap) {
        this.gameMap = gameMap;
        this.simbol = UNICODE_RABBIT;
        this.hp = 10;
        this.speed = 3;
        this.search = new SearchServiceImpl(gameMap);
        this.currentPathToTarget = new ArrayDeque();
    }

    @Override
    public String makeMove(Point currentPoint) {
        if (!gameMap.hasCreature(this)) {
            return "no_Herbivore";
        }

        // herbove can move (how many point traverse for one step)
        for (int i = 0; i < this.speed; i++) {

            if (this.hp <= 0) {
                gameMap.creatureKilled(currentPoint);
                this.gameMap.display();
                break;
            }

            if (currentPathToTarget.isEmpty()) {
                // go to find path grass
                currentPathToTarget.addAll(search.findPathToTarget(currentPoint, typeOfFood));
                if (currentPathToTarget.isEmpty()) {
                    System.out.println("No grass!");
                    return "no_grass";

                }
            }

            // meal is near, just eat it
            if (currentPathToTarget.size() == 1) {
                Point food = currentPathToTarget.pollFirst();
                Grass grass = (Grass) gameMap.getEntity(food);
                int calories = grass.getCalorie();
                this.hp += calories;   // eaten
                gameMap.creatureStep(currentPoint, new Point(food.x, food.y), this);
                currentPoint = food;
                this.gameMap.display();
                continue;
            }

            Point pointToGrass = this.currentPathToTarget.pollFirst();
            gameMap.creatureStep(currentPoint, pointToGrass, this);
            this.gameMap.display();
            currentPoint = pointToGrass;

            this.hp -= 1;
        }
        currentPathToTarget.clear();
        return "ok";
    }

    public boolean isAttacked() {
        return isAttacked;
    }

    public void setAttacked(boolean attacked) {
        isAttacked = attacked;
    }

    public int getCalorie() {
        return calorie;
    }
}
