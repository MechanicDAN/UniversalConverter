package com.uc.servingwebcontent.parser;

import com.opencsv.CSVReader;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;

@Component
@NoArgsConstructor
public class CvsParser {
    public String path = "";
    public HashMap<String, HashMap<String, Double>> convertMap = new HashMap<>();
    public HashSet<String> unitsSet = new HashSet<>();

    public void parse() throws IOException {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
           String[] values;
           while ((values = csvReader.readNext()) != null) {
               if (!convertMap.containsKey(values[0])) {
                   convertMap.put(values[0], new HashMap<String, Double>());
               }
               convertMap.get(values[0]).put(values[1], Double.valueOf(values[2]));
           }
        }
        expandConvertMap();
    }

    private void expandConvertMap() {
        for(String key: convertMap.keySet()) {
            unitsSet.add(key);
            expandConvertMapIter(key,convertMap.get(key),1);
        }
    }

    private void expandConvertMapIter(String from, HashMap<String, Double> to, double factor) {
        for(String key: to.keySet()) {
            unitsSet.add(key);
            convertMap.get(from).put(key, factor*to.get(key));
            if(convertMap.containsKey(key))
                expandConvertMapIter(from,convertMap.get(key),factor*to.get(key));
        }
    }
}