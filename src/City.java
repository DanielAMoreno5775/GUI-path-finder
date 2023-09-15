/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.lang.Comparable;

public class City extends Government implements Comparable<City>, DistanceCalculation<City>{
    //define the data fields
    private int zipcode;
    private String state;
    private double latitude;
    private double longitude;
    private int timezone;          //relative to GMT
    private boolean yesDaylight;   //true if following daylight savings time
    private String standardTimeAbbreviation;
    
    //static constants for calculations
    private final static double EPSILON = 0.00001;
    
    //constructor definition
    public City (int zip, String cName, String st, double lat, double lon, int zone, boolean daylight) {
        super(cName);   //calls the Government constructor
        state = st;
        zipcode = zip;
        latitude = lat;
        longitude = lon;
        timezone = zone;
        yesDaylight = daylight;
        
        //convert timezone and daylight savings time information into an abbreviated string
        if (yesDaylight) {
            if (timezone == -4) {
                standardTimeAbbreviation = "ADT";
            } else if (timezone == -5) {
                standardTimeAbbreviation = "EDT";
            } else if (timezone == -6) {
                standardTimeAbbreviation = "CDT";
            } else if (timezone == -7) {
                standardTimeAbbreviation = "MDT";
            } else if (timezone == -8) {
                standardTimeAbbreviation = "PDT";
            } else if (timezone == -9) {
                standardTimeAbbreviation = "AKDT";
            } else if (timezone == -10) {
                standardTimeAbbreviation = "HDT";
            }
        }
        else {
            if (timezone == -4) {
                standardTimeAbbreviation = "AST";
            } else if (timezone == -5) {
                standardTimeAbbreviation = "EST";
            } else if (timezone == -6) {
                standardTimeAbbreviation = "CST";
            } else if (timezone == -7) {
                standardTimeAbbreviation = "MST";
            } else if (timezone == -8) {
                standardTimeAbbreviation = "PST";
            } else if (timezone == -9) {
                standardTimeAbbreviation = "AKST";
            } else if (timezone == -10) {
                standardTimeAbbreviation = "HST";
            }
        }
    }
    
    public String getStateName (){
        return state;
    }
    
    public int getZipcode(){
        return zipcode;
    }
    
    @Override
    public String toString(){
        return String.format("%s, %s",govName, state);
    }
    
    @Override
    public int compareTo (City otherCity) {
        /*double result = this.compareDistance(otherCity);
        
        //EPSILON handles margin of error due to binary representation of small decimal values and the distance calculation
        if (Math.abs(result) <= EPSILON) {
            return 0;
        } else if (result < 0) {
            return -1;
        } else {
            return 1;
        }*/
        //compare city names
        int result = this.govName.compareToIgnoreCase(otherCity.govName);
        //compare state names if city names match
        if (result == 0)    {
            result = this.state.compareToIgnoreCase(otherCity.state);
        }
        
        return result;
    }

    @Override
    public double compareDistance(City otherPlace) {
        double theta = otherPlace.longitude - this.longitude;
        double dist = Math.sin(Math.toRadians(otherPlace.latitude)) * Math.sin(Math.toRadians(this.latitude)) + (Math.cos(Math.toRadians(otherPlace.latitude)) * Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(theta)));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515;
        
        //EPSILON handles margin of error due to binary representation of small decimal values and the distance calculation
        if (Math.abs(dist) <= EPSILON) {
            return 0;
        }
        
        return dist;
    }
}