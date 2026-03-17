package simulation;

import simulation.entities.Creature;
import simulation.entities.Entity;
import simulation.entities.Grass;
import simulation.entities.Herbivore;
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
            while (!Thread.currentThread().isInterrupted()) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("p")) {
                    isPaused = !isPaused;
                    System.out.println(isPaused ? "--- ПАУЗА ---" : "--- ПРОДОЛЖАЕМ ---");
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("inputThread - stop");
        });
        inputThread.setDaemon(true);
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
//        if (isPaused) {
//            return;
//        }

        if (getCreatures("").isEmpty()) {
            System.out.println("No creature or grass. Game is over!");
            isRunning = false;
            return;
        }

        for (Map.Entry<Point, Entity> entry : getCreatures("").entrySet()) {
            if (isPaused) {
                return;
            }
            String error = ((Creature) entry.getValue()).makeMove(entry.getKey());
            if (error.equals("no_herbivore") && getCreatures("Herbivore").isEmpty()) {
                isRunning = false;
                return;
            }
            if (error.equals("no_grass") && getCreatures("Grass").isEmpty()) {
                isRunning = false;
                return;
            }
        }
    }

    private Map<Point, Entity> getCreatures(String creature) {

        switch (creature) {
            case "Herbivore" -> {
                return gameMap.getEntities().entrySet().stream()
                        .filter(pointEntityEntry -> (pointEntityEntry.getValue() != null
                                && pointEntityEntry.getValue() instanceof Herbivore))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (a, b) -> a,
                                HashMap::new));
            }

            case "Grass" -> {
                return gameMap.getEntities().entrySet().stream()
                        .filter(pointEntityEntry -> (pointEntityEntry.getValue() != null
                                && pointEntityEntry.getValue() instanceof Grass))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (a, b) -> a,
                                HashMap::new));
            }

            default -> {
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
    }
}
