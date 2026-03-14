package simulation.entities;

import static simulation.utils.UnicodeSprites.UNICODE_GREEN_SPRIG;

public class Grass extends Entity {
    private final int calorie = 3;

    public Grass() {
        this.simbol = UNICODE_GREEN_SPRIG;
    }

    /**
     * @return int
     */
    public int getCalorie() {
        return calorie;
    }
}
