package com.example.currentplacedetailsonmap;

import android.location.Location;

/**
 * Created by alwin on 1/20/18.
 */

public class PlaceDetails {

    public double getDegrees(double lat1, double long1, double lat2, double long2, double headX) {

        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(long2-long1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1)*Math.sin(lat2) -
                Math.sin(lat1)*Math.cos(lat2)*Math.cos(dLon);
        double brng = Math.toDegrees(Math.atan2(y, x));

        // fix negative degrees
        if(brng<0) {
            brng=360-Math.abs(brng);
        }
        System.out.println("Direction: "+(brng - headX));
        return brng - headX;
    }

    public static double getDistance(double lat1, double lat2, double lon1,
                                  double lon2) {
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);


//        Location location1=new Location("locationA");
//            location1.setLatitude(lat1);
//            location1.setLongitude(lon1);
//Location location2=new Location("locationA");
//            location2.setLatitude(lat2);
//            location2.setLongitude(lon2);
//double distance=location1.distanceTo(location2);
//return distance;
    }

    public String getDirectionPlace(double lat1, double long1, double lat2, double long2){
        double angle;
        angle = getDegrees(lat1,long1,lat2,long2,0);
        if(((337.0<angle) && (angle <= 360.0)) || ((0.0<=angle)&&(angle<=23.0)))
        {
         return "North";
        }else if(((23.0<angle)&&(angle<=68.0))){
            return "North East";
        }else if(((68.0<angle)&&(angle<=113.0))){
            return "East";

        }else if(((113.0<angle)&&(angle<=158.0))){
            return "South East";

        }else if(((158.0<angle)&&(angle<=203.0))){
            return "South";

        }else if(((203.0<angle)&&(angle<=248.0))){
            return "South West";

        }else if(((248.0<angle)&&(angle<=293.0))){
            return "West";

        }else if(((293.0<angle)&&(angle<=337.0))){
            return "North West";

        }else {
            return "Sorry, Unable To Find The Location";
        }
    }

}
