package com.uc.servingwebcontent.converter;

import com.uc.servingwebcontent.webController.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Converter {
    private HashMap<String, HashMap<String, Double>> convertMap;
    private ArrayList<String> numerator;
    private ArrayList<String> denominator;

    public Converter(HashMap<String, HashMap<String, Double>> convertMap, String from, String to) {
        this.convertMap = convertMap;
        this.numerator = createNumerator(from,to);
        this.denominator = createDenominator(from,to);
    }

    public Response convert() {
        if (numerator.size() != denominator.size())
            return new Response("Bad Request",400, "");

        double coefficient = 1;
        for (int i = 0; i < numerator.size(); i++) {
            String factor = numerator.get(i);
            for (int j = 0; j < denominator.size(); j++) {
                String divider = denominator.get(j);
                if (convertMap.containsKey(factor) && convertMap.get(factor).containsKey(divider)) {
                    coefficient /= convertMap.get(factor).get(divider);
                    denominator.remove(divider);
                    numerator.remove(factor);
                    i--;
                    break;
                }
            }
        }

        for (int i = 0; i < denominator.size(); i++) {
            String divider = denominator.get(i);
            if (convertMap.containsKey(divider)) {
                for (int j = 0; j < numerator.size(); j++) {
                    String factor = numerator.get(j);
                    if (convertMap.get(divider).containsKey(factor)) {
                        coefficient *= convertMap.get(divider).get(factor);
                        denominator.remove(divider);
                        numerator.remove(factor);
                        i--;
                        break;
                    }
                }
            } else return new Response("Bad Request",400, "");
        }
         return (denominator.size() == 0 && numerator.size() == 0) ?
                 new Response("SUCCESS",200, Double.toString(coefficient)) : new Response("Not Found",404, "");
    }

    public ArrayList<String> createNumerator(String from, String to) {
        if(to.split("/").length == 1) {
            return new ArrayList<>(Arrays.asList(to.split("\\*")));
        }

        String[] fromDenominator = from.replaceAll(" ","").split("/")[1].split("\\*");
        String[] toNumerator = to.replaceAll(" ","").split("/")[0].split("\\*");
        ArrayList<String> numerator = new ArrayList<>(Arrays.asList(fromDenominator));
        numerator.addAll(Arrays.asList(toNumerator));
        return numerator;
    }

    public ArrayList<String> createDenominator(String from, String to) {
        if(from.split("/").length == 1) {
            return new ArrayList<>(Arrays.asList(from.split("\\*")));
        }

        String[] fromNumerator = from.replaceAll(" ","").split("/")[0].split("\\*");
        String[] toDenominator = to.replaceAll(" ","").split("/")[1].split("\\*");
        ArrayList<String> denominator = new ArrayList<>(Arrays.asList(fromNumerator));
        denominator.addAll(Arrays.asList(toDenominator));
        return denominator;
    }
}
