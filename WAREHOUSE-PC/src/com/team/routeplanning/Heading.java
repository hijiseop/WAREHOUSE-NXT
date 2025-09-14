package com.team.routeplanning;
public class Heading {

    private static int heading;
    private static final int POSITIVE_X = 0;
    private static final int POSITIVE_Y = 90;
    private static final int NEGATIVE_X = 180;
    private static final int NEGATIVE_Y = 270;


    public Heading() {
        Heading.heading = 0;
    }

    public static int getHeading() {
        return heading;
    }

    public static void setHeading(int newDirection) {
        switch (newDirection) {
            case 0:  heading = POSITIVE_X;
                break;
            case 90: heading = POSITIVE_Y;
                break;
            case 180: heading = NEGATIVE_X;
                break;
            case 270: heading = NEGATIVE_Y;
                break;


            default: heading = POSITIVE_X;
                break;
        }
    }

}