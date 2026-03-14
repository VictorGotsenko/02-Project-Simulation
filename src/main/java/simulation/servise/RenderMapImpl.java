package simulation.servise;

import simulation.entities.Herbivore;
import simulation.model.GameMap;
import simulation.model.Point;

import static simulation.utils.UnicodeSprites.EMPTY_SPACES;
import static simulation.utils.UnicodeSprites.UNICODE_GREEN_SPRIG;
import static simulation.utils.UnicodeSprites.UNICODE_MOUNTAIN;
import static simulation.utils.UnicodeSprites.UNICODE_PREDATOR;
import static simulation.utils.UnicodeSprites.UNICODE_RABBIT;

public final class RenderMapImpl implements RenderMap {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED_ERROR = "\u001B[31m";

    public static final String ANSI_GRAY_BACKGROUND = "\u001B[100m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";  // maybe set 41 or 101

    private String currentBackground = ANSI_GRAY_BACKGROUND;

    private String attack = ANSI_RED_BACKGROUND;

    @Override
    public void render(GameMap map) {
        //clear console
        System.out.println("\033\143");

        String sprite;
        for (int i = 0; i < map.getX(); i++) {
            for (int j = 0; j < map.getY(); j++) {
                var entity = map.getEntity(new Point(i, j));
                sprite = entity == null ? EMPTY_SPACES : entity.getSimbol();
                // if attack change BCKGound for 1 render
                if (entity instanceof Herbivore && ((Herbivore) entity).isAttacked()) {
                    System.out.print(attack + sprite);
                    ((Herbivore) entity).setAttacked(false);
                } else {
                    // regular way
                    System.out.print(colorizeSprite(sprite));
                }

                System.out.print(ANSI_RESET);
            }
            System.out.print("\n"); //separator for EOL
        }
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String colorizeSprite(String sumbol) {
        String result = sumbol;

        switch (sumbol) {
            //ground
            case EMPTY_SPACES -> {
                result = currentBackground + result;
            }
            //grass
            case UNICODE_GREEN_SPRIG -> {
                result = currentBackground + result;
            }
            // Rock
            case UNICODE_MOUNTAIN -> {
                result = currentBackground + result + " ";
            }
            // Herbivore
            case UNICODE_RABBIT -> {
                result = currentBackground + result;
            }
            // Predator
            case UNICODE_PREDATOR -> {
                result = currentBackground + result;
            }
            default -> {
                result = ANSI_RED_BACKGROUND + ANSI_RED_ERROR + sumbol;
            }
        }
        return result;
    }
}
