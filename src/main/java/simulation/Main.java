package simulation;

import simulation.model.GameMap;
import simulation.servise.RenderMapImpl;

public class Main {
    public static void main(String[] args) {

        Simulation simulation = new Simulation(new GameMap(15, 15, new RenderMapImpl()));
        simulation.startSimulation();
    }
}
