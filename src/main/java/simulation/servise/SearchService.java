package simulation.servise;

import simulation.model.Point;

import java.util.Deque;

interface SearchService {
    Deque<Point> findPathToTarget(Point point, String targetType);
}
