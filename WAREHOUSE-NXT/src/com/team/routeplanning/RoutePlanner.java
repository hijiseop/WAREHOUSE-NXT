package com.team.routeplanning;
/*    


    Copyright (C) 2012 http://software-talk.org/ (developer@software-talk.org)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

/**
 * A simple example for the usage of this package.
 * 
 * @see AStarFactory
 * @see AStarNode
 */
public class RoutePlanner {

    private static Map<AStarNode> myMap = new Map<AStarNode>(12, 8, new AStarFactory());
	
    public Route calculateRoute(Point _start, Point _end) {
		Route returnRoute;
        Point[] returnPath;
        int returnRouteLength;
        Integer[] returnCommands;
        List<AStarNode> path = myMap.findPath((int)_start.getX(), (int)_start.getY(),(int) _end.getX(),(int) _end.getY());
        returnPath = new Point[path.size()+1];
        returnPath[0] = _start;
        for(int i = 1; i < path.size() +1; i++){
        	returnPath[i] = new Point(path.get(i-1).getxPosition(),path.get(i-1).getyPosition());
        }
        Double previousX = (double) 0;
        Double currentX = (double) 0;
        Double previousY = null;
        Double currentY = null;
        
        returnRouteLength = path.size();
        String[] commands= new String [returnRouteLength + 1];
//        Heading.setHeading(0);
        
        for(int i = 0 ; i < (returnRouteLength + 1) ; i++) {
  
            Point p = returnPath[i];
            if(!(previousX == null) && !(previousY == null)) {
                currentX = p.getX();
                currentY = p.getY();

                Double xDisp = currentX - previousX;
                Double yDisp = currentY - previousY;
                if(Heading.getHeading() == 0) {
                    if(xDisp == 1) {
                        commands[i] = "FORWARD";
                    }else if(yDisp == 1) {
                        commands[i] = "LEFT";
                        Heading.setHeading(90);
                    }else if(yDisp == -1) {
                        commands[i] = "RIGHT";
                        Heading.setHeading(270);
                    }if(xDisp == -1) {
                        commands[i] = "BACK";
                        Heading.setHeading(180);
                    }

                }else if(Heading.getHeading() == 90) {
                    if(xDisp == 1) {
                        commands[i] = "RIGHT";
                        Heading.setHeading(0);
                    }else if(yDisp == 1) {
                        commands[i] = "FORWARD";
                        Heading.setHeading(90);
                    }else if(yDisp == -1) {
                        commands[i] = "BACK";
                        Heading.setHeading(270);
                    }if(xDisp == -1) {
                        commands[i] = "LEFT";
                        Heading.setHeading(180);
                    }

                }else if(Heading.getHeading() == 180) {
                    if(xDisp == 1) {
                        commands[i] = "BACK";

                        Heading.setHeading(0);
                    }else if(yDisp == 1) {
                        commands[i] = "RIGHT";
                        Heading.setHeading(90);
                    }else if(yDisp == -1) {
                        commands[i] = "LEFT";
                        Heading.setHeading(270);
                    }if(xDisp == -1) {
                        commands[i] = "FORWARD";
                        Heading.setHeading(180);
                    }

                }else if(Heading.getHeading() == 270) {
                    if(xDisp == 1) {
                        commands[i] = "LEFT";
                        Heading.setHeading(0);
                    }else if(yDisp == 1) {
                        commands[i] = "BACK";
                        Heading.setHeading(90);
                    }else if(yDisp == -1) {
                        commands[i] = "FORWARD";
                        Heading.setHeading(270);
                    }if(xDisp == -1) {
                        commands[i] = "RIGHT";
                        Heading.setHeading(180);
                    }

                }

            }
            //set the previous X and Y
            previousX = p.getX();
            previousY = p.getY();
        }
        //System.out.println(Arrays.toString(commands));
      //Defines the array which will store the integers which the robot understands as a route
        returnCommands = new Integer[commands.length -1];
//        logger.debug("Initialised final array object");
        //Converts words into integers
        for(int i = 1; i < commands.length ; i++) {
        
        	
            switch (commands[i]) {
                case "FORWARD": returnCommands[i-1] = 0;
                    break;
                case "LEFT":  returnCommands[i-1] = 1;
                    break;
                case "RIGHT":  returnCommands[i-1] = -1;
                    break;
                case "BACK":  returnCommands[i-1] = null;
                    break;
                case "null":  returnCommands[i-1] = null;
                    break;
                default: returnCommands[i-1] = null;
                    break;
            }
        }

        // Create the Route object to be returned by the method.
        returnRoute = new Route(returnRouteLength, returnCommands, returnPath);
//        logger.debug("Created new Route object to return and calculate route method complete");
        return returnRoute;
	}
	
    public static void main(String[] args) {
        
    	RoutePlanner r = new RoutePlanner();
    	Route myRoute = r.calculateRoute(new Point(0,0), new Point(11,7));
    	System.out.println(myRoute.distance());
    	System.out.println("MY COMMANDS" + Arrays.toString(myRoute.getCommands()));
    	System.out.println(Arrays.toString(myRoute.getRouteCoords()));
    }


}
