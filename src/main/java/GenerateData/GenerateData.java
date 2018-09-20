/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GenerateData;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.JsonPath;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author yaya_aye
 */
public class GenerateData {
    public void TmpObjectAndProperty(JsonNode node, ArrayList<String> listClass) {
        ArrayList<String> list = listClass;
//        int i = index;

        //System.out.println("test: "+node.fieldNames().next());      
        Iterator<String> fieldNames = node.fieldNames();
        //System.out.println("nama: "+fieldNames.next());
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode fieldValue = node.get(fieldName);
            if (fieldValue.isObject() && !fieldName.equalsIgnoreCase("required")) {
                if (!fieldName.equalsIgnoreCase("properties") && !fieldName.equalsIgnoreCase("items")) {
                    list.add(fieldName);
                }
                TmpObjectAndProperty(fieldValue, listClass);
            }
        }
    }

    public void TmpAllFieldValue(JsonNode node, ArrayList<String> listClass) {
        ArrayList<String> list = listClass;
        Iterator<String> fieldNames = node.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode fieldValue = node.get(fieldName);
            if (!fieldName.equalsIgnoreCase("items")) {
                //list.add(fieldName);
                //System.out.println("    "+fieldName + " : " +fieldValue.asText());
                list.add(fieldName);
                list.add(fieldValue.asText());
            }
            TmpAllFieldValue(fieldValue, listClass);
        }
    }

    public void GetPathClass(JsonNode node, ArrayList<String> listClass) {
        ArrayList<String> list = listClass;
        Iterator<String> fieldNames = node.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode fieldValue = node.get(fieldName);
            if (fieldValue.isObject() && !fieldName.equalsIgnoreCase("required")) {
                if (!fieldName.equalsIgnoreCase("items")) {
                    list.add(fieldName);
                }
                GetPathClass(fieldValue, listClass);
            }
        }
    }
//    

    public void GetAllElemen(JsonNode node, ArrayList<String> listClass) {
        ArrayList<String> list = listClass;
        Iterator<String> fieldNames = node.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode fieldValue = node.get(fieldName);
            if (fieldValue.isObject()) {
                GetArray(fieldValue, listClass);
                //if (!fieldName.equalsIgnoreCase("items")) {
                list.add(fieldName);
                //}
                GetAllElemen(fieldValue, listClass);
            }
        }
    }

    public void GetArray(JsonNode node, ArrayList<String> listClass) {
        ArrayList<String> list = listClass;
        Iterator<String> fieldNames = node.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode fieldValue = node.get(fieldName);
            if (fieldValue.isArray()) {

                list.add("start");
                //System.out.println(fieldName);
                for (final JsonNode objNode : fieldValue) {
                    //System.out.println(objNode.asText());
                    list.add(objNode.asText());
                }
                list.add("end");
                //System.out.println("");
            }
        }
    }
    
    public void GetValueByPath(String fileName, String path, ArrayList<String>[] list,int index ) throws FileNotFoundException, IOException{
        String jsonFile = FileUtils.readFileToString(new File("file/"+fileName), "UTF-8");
        list[index] = JsonPath.read(jsonFile, path);
        //System.out.println(list[index].toString());
    }
}
