package simulation.servise;

import simulation.entities.Entity;
import simulation.entities.Grass;
import simulation.entities.Herbivore;
import simulation.model.GameMap;
import simulation.model.Point;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SearchServiceImpl implements SearchService {

    GameMap gameMap;

    public SearchServiceImpl(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    @Override
    public Deque<Point> findPathToTarget(Point point, String targetType) {
        Point targetPoint = findTargetPoint(point, targetType);

        Deque<Point> result = new ArrayDeque<>();

        if (null == targetPoint) {
            // no grass or Herbivore
            return result;
        }

        Point temp = targetPoint;
        while (temp != point) {
            result.push(temp);
            temp = temp.getPreviousPoint();
        }
        return result;
    }

    private Point findTargetPoint(Point point, String targetType) {
        List<Point> closeList = new ArrayList<>();
        List<Point> openList = new ArrayList<>();
        Point stopPoint = null;

        closeList.add(point);
        openList.add(point);
        while (!openList.isEmpty()) {
            Point currentPoint = openList.removeFirst();
            closeList.add(currentPoint);
            if (null != findTargetNearby(currentPoint, targetType)) {
                stopPoint = findTargetNearby(currentPoint, targetType);
                stopPoint.setPreviousPoint(currentPoint);
                break;
            }
            for (Point emptyPoint : getEmptyPoint(currentPoint)) {
                if (!closeList.contains(emptyPoint) && !openList.contains(emptyPoint)) {
                    emptyPoint.setPreviousPoint(currentPoint);
                    openList.add(emptyPoint);
                }
            }
        }
        return stopPoint;
    }

    private Point findTargetNearby(Point point, String targetType) {
        Point result = null;
        Map<Point, Entity> neighbors = getNeighboringPoints(point);
        for (Map.Entry<Point, Entity> entry : neighbors.entrySet()) {
            if (targetType.equals("Grass")) {
                if (entry.getValue() instanceof Grass) {
                    result = entry.getKey();
                    return result;
                }
            }

            if (targetType.equals("Herbivore")) {
                if (entry.getValue() instanceof Herbivore) {
                    result = entry.getKey();
                    return result;
                }
            }
        }
        return result;
    }

    private List<Point> getEmptyPoint(Point point) {
        List<Point> result = new ArrayList<>();
        for (Map.Entry<Point, Entity> entry : getNeighboringPoints(point).entrySet()) {
            if (null == entry.getValue()) {
                Point p = entry.getKey();
                result.add(p);
            }
        }
        return result;
    }

    private Map<Point, Entity> getNeighboringPoints(Point point) {
        Map<Point, Entity> result = new HashMap<>();
        int x = point.x;
        int y = point.y;

        //  [x-1, y+1] [x, y+1] [x+1, y+1]
        //
        //  [x-1, y  ] [x, y  ] [x+1, y  ]
        //
        //  [x-1, y-1] [x, y-1] [x+1, y-1]

        for (Map.Entry<Point, Entity> entry : gameMap.getEntities().entrySet()) {
            Point tmpP = entry.getKey();
            if (tmpP.x == point.x && tmpP.y == (point.y + 1)) {
                result.put(entry.getKey(), entry.getValue());
            }
            if (tmpP.x == (point.x + 1) && tmpP.y == point.y) {
                result.put(entry.getKey(), entry.getValue());
            }
            if (tmpP.x == point.x && tmpP.y == (point.y - 1)) {
                result.put(entry.getKey(), entry.getValue());
            }
            if (tmpP.x == (point.x - 1) && tmpP.y == point.y) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }
}
