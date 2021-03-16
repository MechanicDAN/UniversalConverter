package com.uc.servingwebcontent.component;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Component("cvsParser")
public class CvsParser {
    private String path = "";
    public HashMap<String, HashMap<String, Double>> convertMap = new HashMap<String, HashMap<String, Double>>();

    public CvsParser() {
    }

    public void setPath(String text) {
        this.path = text;
    }

    public String getPath() {
        return this.path;
    }

    public void parse() throws Exception {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
            String[] values = null;
           while ((values = csvReader.readNext()) != null) {
               if (!convertMap.containsKey(values[0])) {
                   convertMap.put(values[0], new HashMap<String, Double>());
               }
               convertMap.get(values[0]).put(values[1], Double.valueOf(values[2]));
           }
        }
        expandConvertMap();
    }

    public void expandConvertMap() {
        for(String key: convertMap.keySet()) {
            expandConvertMapIter(key,convertMap.get(key),1);
        }
    }

    public void expandConvertMapIter(String from, HashMap<String, Double> to, double factor) {
        for(String key: to.keySet()) {
            convertMap.get(from).put(key, factor*to.get(key));
            if(convertMap.containsKey(key))
                expandConvertMapIter(from,convertMap.get(key),factor*to.get(key));
        }
    }
}