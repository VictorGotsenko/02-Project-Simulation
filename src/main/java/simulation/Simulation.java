package simulation;

import simulation.entities.Creature;
import simulation.entities.Entity;
import simulation.model.GameMap;
import simulation.model.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class Simulation {
    GameMap gameMap;
    private boolean isRunning = true;
    public boolean isPaused = false;

    Scanner scanner = new Scanner(System.in);

    public Simulation(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void startSimulation() {
        gameMap.display();
        System.out.println();
        this.run();
    }

    public void run() {
        System.out.println("***         Welcome game simulation        ***");
        System.out.println("*                                            *");
        System.out.println("*  Pause/Continue  please press P + ENTER    *");
        System.out.println("*  to Start press ENTER                      *");
        System.out.println();

        scanner.nextLine();
        // Thread is reading user inputs for switch Pause/Continue
        Thread inputThread = new Thread(() -> {
            while (isRunning) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("p")) {
                    isPaused = !isPaused;
                    System.out.println(isPaused ? "--- ПАУЗА ---" : "--- ПРОДОЛЖАЕМ ---");
                }
            }
        });
        inputThread.start();

        while (isRunning) {
            if (!isPaused) {
                // Gameplay
                this.nextTurn();
            } else {
                // Gameplay is paused, nothing do
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        inputThread.interrupt();
    }

    public void nextTurn() {
        if (getCreatures().isEmpty()) {
            System.out.println("No creatures! Game is over");
            System.exit(0);
        }
        for (Map.Entry<Point, Entity> entry : getCreatures().entrySet()) {
            if (isPaused) {
                return;
            }
            ((Creature) entry.getValue()).makeMove(entry.getKey());
        }
    }

    private Map<Point, Entity> getCreatures() {
        return gameMap.getEntities().entrySet().stream()
                .filter(pointEntityEntry -> (pointEntityEntry.getValue() != null
                        && pointEntityEntry.getValue() instanceof Creature))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        HashMap::new));
    }
}
