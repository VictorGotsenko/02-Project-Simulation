package simulation.model;

import simulation.entities.Creature;
import simulation.entities.Entity;
import simulation.entities.Herbivore;
import simulation.entities.Predator;
import simulation.servise.RenderMapImpl;
import simulation.utils.GenerateEntity;

import java.util.HashMap;

public final class GameMap {

    private final int width;
    private final int height;
    private HashMap<Point, Entity> entities;
    private RenderMapImpl renderMap;


    private int maxCountRabbit;
    private int maxCountPredator;

    // Init Game field
    public GameMap(int x, int y, RenderMapImpl renderMap) {
        this.width = x;
        this.height = y;
        this.renderMap = renderMap;
        this.entities = new HashMap<>();
        this.maxCountRabbit = (int) (x * 45 / 100.0);  // in percent 70
        this.maxCountPredator = (int) (x * 15 / 100.0);  // in percent

        GenerateEntity generateEntity = new GenerateEntity();

        // Gen Rock, Grass, Creatures
        Entity entity;
        int rabbit = 0;
        int predator = 0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                entity = generateEntity.getRandEntity(this);
                if (entity instanceof Herbivore) {
                    if (rabbit < maxCountRabbit) {
                        entities.put(new Point(i, j), entity);
                        ++rabbit;
                        continue;
                    } else {
                        continue;
                    }
                }
                if (entity instanceof Predator) {
                    if (predator < maxCountPredator) {
                        entities.put(new Point(i, j), entity);
                        ++predator;
                        continue;
                    } else {
                        continue;
                    }
                }
                entities.put(new Point(i, j), entity);
            }
        }
    }

    public void display() {
        renderMap.render(this);
    }

    public int getX() {
        return width;
    }

    public int getY() {
        return height;
    }

    public HashMap<Point, Entity> getEntities() {
        return entities;
    }

    public Entity getEntity(Point point) {
        return entities.get(point);
    }

    public boolean hasCreature(Creature creature) {
        if (entities.containsValue(creature)) {
            return true;
        }
        return false;
    }

    public void creatureKilled(Point point) {
        entities.put(point, null);
    }

    public void creatureStep(Point start, Point finish, Creature creature) {
        entities.put(finish, creature);
        entities.put(start, null);
    }
}
