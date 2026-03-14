package simulation.entities;

import simulation.model.Point;

public abstract class Creature extends Entity {
    protected int hp;  // health point
    protected int speed;

    public void makeMove(Point currentPoint) {
    }

    /**
     *
     * @return hp
     */
    public int getHp() {
        return hp;
    }

    /**
     *
     * @param hp
     */
    public void setHp(int hp) {
        this.hp = hp;
    }
}
