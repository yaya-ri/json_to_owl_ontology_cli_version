/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author yaya_aye
 */
public class JsonPathExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
//        // Example from https://github.com/jayway/JsonPath#path-examples
//        final String json = "{\"store\": {\"book\": [{\"category\": \"reference\",\"author\": \"Nigel Rees\",\"title\": \"Sayings of the Century\",\"price\": 8.95},{\"category\": \"fiction\",\"author\": \"Evelyn Waugh\",\"title\": \"Sword of Honour\",\"price\": 12.99},{\"category\": \"fiction\",\"author\": \"Herman Melville\",\"title\": \"Moby Dick\",\"isbn\": \"0-553-21311-3\",\"price\": 8.99},{\"category\": \"fiction\",\"author\": \"J. R. R. Tolkien\",\"title\": \"The Lord of the Rings\",\"isbn\": \"0-395-19395-8\",\"price\": 22.99}],\"bicycle\": {\"color\": \"red\",\"price\": 19.95}},\"expensive\": 10}";
//        
//        FileReader file = new FileReader("file/coba.json");
//        
//        Configuration conf = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
//                .options(Option.ALWAYS_RETURN_LIST, Option.SUPPRESS_EXCEPTIONS).build();
//
//        ArrayNode node = JsonPath.using(conf).parse(json).read("$.store.book[*]"); 
//        for (Object o : node) {
//            System.out.println(o.toString());
//        }
//
//        node = JsonPath.using(conf).parse(json).read("$.store.book[0].author"); 
//        for (Object o : node) {
//            System.out.println(o.toString());
//        }
        
        String jsonString = FileUtils.readFileToString(new File("file/coba.json"), "UTF-8");
        String jsonFile = FileUtils.readFileToString(new File("file/test.json"), "UTF-8");

//        List<Map<String, Object>> match = JsonPath.parse(jsonString).read("$..filter", Filter.filter(Criteria.where("osType").eq("Linux")));
//
//        System.out.println("result>" + match);
        
        ArrayList<String> data= JsonPath.read(jsonFile, "kesehatan.penyakit[*].obat.kandungan.tumbuhan");
        System.out.println(data.toString());
    }
    
}
