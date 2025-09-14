package com.team.routeplanning;
import java.awt.Point;


public class Route {

    private int routeDistance;
    private Point[] routeCoords;
    private Integer[] commands;

    Route(int distance, Integer[] commands, Point[] pathCoords) {
        this.routeDistance = distance;
        this.commands = commands;
        this.routeCoords = pathCoords;
    }

    public int distance() {
        return routeDistance;
    }

    public Point[] getRouteCoords() {
        return routeCoords;
    }

    public Integer[] getCommands() {
        return commands;
    }

}
