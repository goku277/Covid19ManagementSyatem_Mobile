package com.example.covid_19managementsystem.Utility;

import java.util.*;
public class Details {
  /*  public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        String data="Id: 67468f6a-6e41-4958-a0df-cd29006e936b Age: 25 Status: -ve Name: Aman Id: 7d1e23d8-b15f-4f9b-acb2-1563ea9aaa51 Age: 24 Status: -ve Name: Suchorita Id: a5efda83-dee8-48cf-aad1-c1ef0b013777 Age: 25 Status: -ve Name: Rawat Id: db2348d2-70d4-4ba0-8aea-d60f4e617e87 Age: 25 Status: -ve Name: Charles Id: 67468f6a-6e41-4958-a0df-cd29006e936b Age: 25 Status: -ve Name: Aman Id: 7d1e23d8-b15f-4f9b-acb2-1563ea9aaa51 Age: 24 Status: -ve Name: Suchorita Id: a5efda83-dee8-48cf-aad1-c1ef0b013777 Age: 25 Status: -ve Name: Rawat Id: db2348d2-70d4-4ba0-8aea-d60f4e617e87 Age: 25 Status: -ve Name: Charles";

        Map<String, Set<String>> mapIdToDetails= init(data);

        System.out.println("From main() mapIdToDetails: " + mapIdToDetails);
    }   */

    public Map<String, Set<String>> init(String data) {
        String split[]= data.split("Id:");
        Set<String> Id= new LinkedHashSet<>();
        for (String s: split) {
            s= s.trim();
            if (!s.trim().isEmpty()) {
                System.out.println("split: "+ s);
                if (s.contains("Age:")) {
                    Id.add(s.substring(s.indexOf(s.charAt(0)), s.indexOf("Age:")).trim());
                }
            }
        }
        Map<String, Set<String>> mapIdToDetails= new LinkedHashMap<>();
        Set<String> details= null;

        for (String s: Id) {
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
                        }
                        if (s1.contains("Name:")) {
                            name= s1.substring(s1.indexOf("Name:")).trim();
                        }
                        details.add(age);
                        details.add(status);
                        details.add(name);

                        mapIdToDetails.put(s, details);
                    }
                }
            }
        }

        //   System.out.println("mapIdToDetails: " + mapIdToDetails);

        return mapIdToDetails;
    }
}