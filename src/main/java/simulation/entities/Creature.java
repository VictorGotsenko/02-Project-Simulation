package simulation.entities;

import simulation.model.Point;

public abstract class Creature extends Entity {
    protected int hp;  // health point
    protected int speed;

    /**
     *
     * @param currentPoint
     * @return is food
     */
    public String makeMove(Point currentPoint) {
        return "";
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
