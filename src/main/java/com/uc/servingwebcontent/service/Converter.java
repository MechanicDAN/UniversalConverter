package com.uc.servingwebcontent.service;

import com.uc.servingwebcontent.parser.CvsParser;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@NoArgsConstructor
public class Converter {
    @Autowired
    private CvsParser parser;
    private ArrayList<String> numerator;
    private ArrayList<String> denominator;

    public ResponseEntity<String> convert(String from, String to) {
        try {
             numerator = createNumerator(from, to);
             denominator = createDenominator(from, to);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!isCorrectRequest())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        double coefficient = 1;

        coefficient = calculateFactorToDivider(coefficient);
        coefficient = calculateDividerToFactor(coefficient);

         return (denominator.size() == 0 && numerator.size() == 0) ?
                 new ResponseEntity<>(Double.toString(coefficient), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private ArrayList<String> createNumerator(String from, String to) throws Exception {
        if(!to.contains("/")) {
            return new ArrayList<>(Arrays.asList(to.replaceAll(" " , "").split("\\*")));
        }

        String[] fromDenominator = from.replaceAll(" ","").split("/")[1].split("\\*");
        String[] toNumerator = to.replaceAll(" ","").split("/")[0].split("\\*");
        ArrayList<String> numerator = new ArrayList<>(Arrays.asList(fromDenominator));
        numerator.addAll(Arrays.asList(toNumerator));
        return numerator;
    }

    private ArrayList<String> createDenominator(String from, String to) throws Exception {
        if(!to.contains("/")) {
            return new ArrayList<>(Arrays.asList(from.replaceAll(" " , "").split("\\*")));
        }

        String[] fromNumerator = from.replaceAll(" ","").split("/")[0].split("\\*");
        String[] toDenominator = to.replaceAll(" ","").split("/")[1].split("\\*");
        ArrayList<String> denominator = new ArrayList<>(Arrays.asList(fromNumerator));
        denominator.addAll(Arrays.asList(toDenominator));
        return denominator;
    }

    private boolean isCorrectRequest() {
        if (numerator.size() != denominator.size())
            return false;

        return !containUnknown(numerator) && !containUnknown(denominator);
    }

    private boolean containUnknown(ArrayList<String> arr) {
        for(String unit : arr) {
            if (!parser.unitsSet.contains(unit))
                return true;

        }
        return false;
    }

    private double calculateFactorToDivider(double coefficient) {
        for (int i = 0; i < numerator.size(); i++) {
            String factor = numerator.get(i);
            for (int j = 0; j < denominator.size(); j++) {
                String divider = denominator.get(j);
                if(factor.equals(divider)) {
                    denominator.remove(divider);
                    numerator.remove(factor);
                    i--;
                    break;
                }
                if (parser.convertMap.containsKey(factor) && parser.convertMap.get(factor).containsKey(divider)) {
                    coefficient /= parser.convertMap.get(factor).get(divider);
                    denominator.remove(divider);
                    numerator.remove(factor);
                    i--;
                    break;
                }
            }
        }
        return coefficient;
    }

    private double calculateDividerToFactor(double coefficient) {
        for (int i = 0; i < denominator.size(); i++) {
            String divider = denominator.get(i);
            if (parser.convertMap.containsKey(divider)) {
                for (int j = 0; j < numerator.size(); j++) {
                    String factor = numerator.get(j);
                    if (parser.convertMap.get(divider).containsKey(factor)) {
                        coefficient *= parser.convertMap.get(divider).get(factor);
                        denominator.remove(divider);
                        numerator.remove(factor);
                        i--;
                        break;
                    }
                }
            }
        }
        return coefficient;
    }
}
