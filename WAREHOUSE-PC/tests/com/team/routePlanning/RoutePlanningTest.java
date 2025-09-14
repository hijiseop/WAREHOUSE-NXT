package com.team.routePlanning;

import java.awt.Point;

import org.junit.Test;
import com.team.routeplanning.*;

public class RoutePlanningTest {

	// Should Fail when a path is on an obstacle
	@Test
	public void noPath() {
		Point start = new Point(0, 0);
		Point end = new Point(4, 4);

		RoutePlanner router = new RoutePlanner();
		Route myRoute = router.calculateRoute(start, end);
		assert myRoute.getCommands().length == 0;
		assert myRoute.getRouteCoords().length == 1;
		assert myRoute.getRouteCoords()[0].getX() == new Double(0);
		assert myRoute.getRouteCoords()[0].getY() == new Double(0);
	}

	@Test
	public void shortestRoute1(){
		Point start = new Point(0, 0);
		Point end = new Point(0, 7);

		RoutePlanner router = new RoutePlanner();
		Route myRoute = router.calculateRoute(start, end);
		assert myRoute.getRouteCoords()[0].getX() == new Double(0);
		assert myRoute.getRouteCoords()[0].getY() == new Double(0);
		assert myRoute.getRouteCoords()[1].getX() == new Double(0);
		assert myRoute.getRouteCoords()[1].getY() == new Double(1);;
		assert myRoute.getRouteCoords()[2].getX() == new Double(0);
		assert myRoute.getRouteCoords()[2].getY() == new Double(2);
		assert myRoute.getRouteCoords()[3].getX() == new Double(0);
		assert myRoute.getRouteCoords()[3].getY() == new Double(3);
		assert myRoute.getRouteCoords()[4].getX() == new Double(0);
		assert myRoute.getRouteCoords()[4].getY() == new Double(4);;
		assert myRoute.getRouteCoords()[5].getX() == new Double(0);
		assert myRoute.getRouteCoords()[5].getY() == new Double(5);
		assert myRoute.getRouteCoords()[6].getX() == new Double(0);
		assert myRoute.getRouteCoords()[6].getY() == new Double(6);;
		assert myRoute.getRouteCoords()[7].getX() == new Double(0);
		assert myRoute.getRouteCoords()[7].getY() == new Double(7);
	}

	@Test
	public void shortestRoute2() {
		Point start = new Point(0,0);
		Point end = new Point(11,7);

		RoutePlanner router = new RoutePlanner();
		Route myRoute = router.calculateRoute(start, end);
		assert myRoute.distance() == 18;

	}

}
