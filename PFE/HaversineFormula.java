package com.reydev.tuto.androiddatabase;

public class HaversineFormula {
    private static double distance;
    private static double lat1;
    private static double lon1;
    private static double lat2;
    private static double lon2;


    /**
     *
     * @param lat1 Latitude Of THe Point 1
     * @param lon1 Longitude Of The Point 1
     * @param lat2 Latitude Of THe Point 2
     * @param lon2 Longitude Of The Point 2
     */
    public HaversineFormula(double lat1, double lon1, double lat2, double lon2) {
        this.lat1 = lat1;
        this.lon1 = lon1;
        this.lat2 = lat2;
        this.lon2 = lon2;

            double Rad = 6372.8; //Earth's Radius In kilometers

            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);

            lat1 = Math.toRadians(lat1);
            lat2 = Math.toRadians(lat2);

            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);

            double c = 2 * Math.asin(Math.sqrt(a));

            this.distance = Rad * c * 1000;

    }

    /**
     *
     * @return Distance In Kilometer.
     */
    public int getDistance() {
//        if(distance > 10 && distance < 100){
//            return distance;
//        }else if(distance < 10){
//
//        }
        return (int)distance;
    }
}
