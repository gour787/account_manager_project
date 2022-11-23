package com.example.group19project;

import android.widget.Toast;

import java.util.Objects;

public class Validity {

    public boolean validTime(String time){
        try{
            String[] check = time.split(" ");
            String[] hour_min = check[0].split(":");
            int hour = Integer.parseInt(hour_min[0]);
            int min = Integer.parseInt(hour_min[1]);
            if(Objects.equals(check[1], "AM") || Objects.equals(check[1], "PM")) {
                if(hour>=1 && hour<=12){
                    if(min>=0 && min<=59){
                        return true;
                    }
                }
            }
            else{
                return false;
            }
        }
        catch (Exception e){
            return false;
        }
        return false;
    }

    public boolean validDay(String day){
        if(Objects.equals(day, "Monday") || Objects.equals(day, "Tuesday") || Objects.equals(day, "Wednesday") || Objects.equals(day, "Thursday") || Objects.equals(day, "Friday") || Objects.equals(day, "Saturday")
                || Objects.equals(day, "Sunday")){
            return true;

        }
        return false;
    }

    public static boolean validCapacity(String cap){
        try{
            int new_cap = Integer.parseInt(cap);
            if(new_cap>200){

                return false;
            }
            else{

                return true;
            }
        } catch (Exception e) {

            return false;
        }
    }

}
