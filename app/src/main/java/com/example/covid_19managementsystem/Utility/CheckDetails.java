package com.example.covid_19managementsystem.Utility;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class CheckDetails {
    public Map<String, Set<String>> init(String data) {
        String split[]= data.split("Id:");
        Set<String> Id= new LinkedHashSet<>();
        for (String s: split) {
            s= s.trim();
            if (!s.trim().isEmpty()) {
                //    System.out.println(s);
                if (s.contains("Age:")) {
                    Id.add(s.substring(s.indexOf(s.charAt(0)), s.indexOf("Age:")).trim());
                }
            }
        }
        Map<String, Set<String>> mapIdToDetails= new LinkedHashMap<>();
        Set<String> details= null;

        for (String s: Id) {
            boolean isDischarge= false;
            details= new LinkedHashSet<>();
            String age="", status="", name="";
            for (String s1: split) {
                if (!s1.trim().isEmpty()) {
                    if (s1.contains(s)) {
                        if (s1.contains("Age:") && s1.contains("Status:")) {
                            age = s1.substring(s1.indexOf("Age:"), s1.indexOf("Status:")).trim();
                        }
                        if (s1.contains("Status:") && s1.contains("Name:")) {
                            status= s1.substring(s1.indexOf("Status:"), s1.indexOf("Name:")).trim();
                            if (status.contains("Discharge")) {
                                isDischarge= true;
                            }
                        }
                        if (s1.contains("Name:")) {
                            name= s1.substring(s1.indexOf("Name:")).trim();
                        }
                        details.add(age);
                        details.add(status);
                        details.add(name);
                        if (isDischarge) {
                            mapIdToDetails.put(s, details);
                        }
                    }
                }
            }
        }

        //   System.out.println("mapIdToDetails: " + mapIdToDetails);

        return mapIdToDetails;
    }
}
