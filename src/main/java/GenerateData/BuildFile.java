/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GenerateData;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author yaya_aye
 */
public class BuildFile {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        GenerateData newGenerate = new GenerateData();
        String jsonSchemaFile="test_schema.json";
        String jsonFile = "test.json";
        JsonNode json = new ObjectMapper().readTree(new FileReader("file/"+jsonSchemaFile));
        JsonNode root = json.get("properties");
        ArrayList<String> tmpOP = new ArrayList();
        ArrayList<String> tmpAllValue = new ArrayList();

        newGenerate.TmpObjectAndProperty(root, tmpOP);
        System.out.println("");
        System.out.println("Tmp objek and property: ");
        System.out.println(tmpOP.toString());
        
        newGenerate.TmpAllFieldValue(root, tmpAllValue);

        ArrayList<String>[] listWithProperties = new ArrayList[tmpOP.size()];
        for (int i = 0; i < tmpOP.size(); i++) {
            listWithProperties[i] = new ArrayList<>();
        }
        
        System.out.println("");
        
        System.out.println(tmpAllValue.toString());
        
        System.out.println("");
        
        for (int i = 0; i < tmpAllValue.size(); i++) {
            for (int j = 0; j < tmpOP.size(); j++) {
                if (tmpOP.get(j).equalsIgnoreCase(tmpAllValue.get(i))) {
                    listWithProperties[j].add(tmpOP.get(j));
                    listWithProperties[j].add(tmpAllValue.get(i + 3));
                }
            }
        }
        //list data with information
        System.out.println("");
        System.out.println("list with information properties: ");
        for (int i = 0; i < listWithProperties.length; i++) {
            System.out.println("    " + listWithProperties[i].toString());
        }

        System.out.println("");

        //get elemen json(class dan property )
        ArrayList<String> elementJson = new ArrayList();
        newGenerate.GetPathClass(root, elementJson);
        System.out.println("Elemen: ");
        System.out.println(elementJson.toString());

        System.out.println("");

        ArrayList<String> elementWithArray = new ArrayList();
        newGenerate.GetAllElemen(root, elementWithArray);

        System.out.println("Elemen with array: ");
        System.out.println(elementWithArray.toString());

        System.out.println("");

        //membuat elemen baru 
        String tmpObjekAfterArray = null;
        String item = null;
        ArrayList<String> elementWithArrayFix = new ArrayList();
        for (int i = 0; i < elementWithArray.size(); i++) {
            if (elementWithArray.get(i).equalsIgnoreCase("items")) {
                item = elementWithArray.get(i - 2);
                for (int j = 0; j < elementJson.size(); j++) {
                    if (item.equalsIgnoreCase(elementJson.get(j))) {
                        tmpObjekAfterArray = elementJson.get(j + 1);
                    }
                }
            }
        }

        System.out.println(item);
        if (item != null) {
            for (int i = 0; i < elementWithArray.size(); i++) {
                elementWithArrayFix.add(elementWithArray.get(i));
                if (item.equalsIgnoreCase(elementWithArray.get(i))) {
                    elementWithArrayFix.add(tmpObjekAfterArray);
                }

            }
        }

        System.out.println("elemen with Array fix");
        System.out.println(elementWithArrayFix.toString());

        System.out.println("");

        ArrayList<String> tmpClassNProperty = new ArrayList();
        //tmp class property versi terbalik
//        if (item != null) {
//            for (int i = 1; i < elementWithArrayFix.size(); i++) {
//                if (elementWithArrayFix.get(i).equalsIgnoreCase("properties") && !elementWithArrayFix.get(i - 1).equalsIgnoreCase("end")) {
//                    String superClass = elementWithArrayFix.get(i - 1);
//                    for (int j = i - 2; j >= 0; j--) {
//                        if (elementWithArrayFix.get(j).equalsIgnoreCase("start")) {
//                            break;
//                        }
//                        if (!elementWithArrayFix.get(j).equalsIgnoreCase("end")) {
//                            tmpClassNProperty.add(superClass);
//                            tmpClassNProperty.add(elementWithArrayFix.get(j));
//                        }
//
//                    }
//                }
//            }
//        }
        
        if (item != null) {
            for (int i = 1; i < elementWithArrayFix.size(); i++) {
                if (elementWithArrayFix.get(i).equalsIgnoreCase("properties") && !elementWithArrayFix.get(i - 1).equalsIgnoreCase("end")) {
                    String superClass = elementWithArrayFix.get(i - 1);
                    for (int j = i - 2; j >= 0; j--) {
                        if (elementWithArrayFix.get(j).equalsIgnoreCase("start")) {
                            for (int k = j+1; k < elementWithArrayFix.size(); k++) {
                                if (elementWithArrayFix.get(k).equalsIgnoreCase("end")) {
                                    break;
                                }
                                tmpClassNProperty.add(superClass);
                                tmpClassNProperty.add(elementWithArrayFix.get(k));
                            }
                            break;
                        }
                    }
                }
            }
        }

        //mengganti item dengan nama objek array
        for (int i = 0; i < tmpClassNProperty.size(); i++) {
            if (tmpClassNProperty.get(i).equalsIgnoreCase("items")) {
                for (int j = 0; j < listWithProperties.length; j++) {
                    if (listWithProperties[j].get(1).equalsIgnoreCase("array")) {
                        tmpClassNProperty.set(i, listWithProperties[j].get(0));
                    }
                }
            }
        }

        System.out.println("");
        System.out.println("Tmp Class and property");
        System.out.println(tmpClassNProperty.toString());

        ArrayList<String> tmpObjectProperty = new ArrayList();
        ArrayList<String> tmpDatatypeProperty = new ArrayList();
        
        int point = tmpClassNProperty.size() - 1;
        for (int i = tmpClassNProperty.size() - 1; i >= 0; i = i - 2) {
            String theSub = tmpClassNProperty.get(point);
            //System.out.println(theSub);
            String theSuper = tmpClassNProperty.get(point - 1);
            // System.out.print(theSuper+", "+theSub);
            for (int j = 0; j < listWithProperties.length; j++) {
                if (theSub.equalsIgnoreCase(listWithProperties[j].get(0))) {
                    // System.out.println(listWithProperties[j].get(1));
                    if (listWithProperties[j].get(1).equalsIgnoreCase("object") || listWithProperties[j].get(1).equalsIgnoreCase("array")) {
                        tmpObjectProperty.add(theSuper);
                        tmpObjectProperty.add(theSub);
                    } else {
                        tmpDatatypeProperty.add(theSuper);
                        tmpDatatypeProperty.add(theSub);
                        tmpDatatypeProperty.add(listWithProperties[j].get(1));
                    }
                }
            }
            //System.out.println("");
            point = point - 2;
        }

        System.out.println("");
        System.out.println("tmp Object Property");
        System.out.println(tmpObjectProperty.toString());
        System.out.println("");
        System.out.println("tmp Datatype Property");
        System.out.println(tmpDatatypeProperty.toString());

        System.out.println("");
        System.out.println("");

        
        
//final list=================================================================================================================
        
        System.out.println("-------------------------------------------data siap pakai----------------------------------------------------");
        System.out.println("");
        //list class-------------------------------------------------------------------------------------------------------------
        ArrayList<String> buildListClass = new ArrayList();
        for (int i = 1; i < elementJson.size(); i++) {
            if (elementJson.get(i).equalsIgnoreCase("properties")) {
                buildListClass.add(elementJson.get(i - 1));
            }
        }
        //final list class
        System.out.println("- List Class:");
        System.out.println("    " + buildListClass.toString());

        //-----------------------------------------------------------------------------------------------------------------------

        
        System.out.println("");
        
        
        //list class to class----------------------------------------------------------------------------------------------------
        int sizeListCtoC = tmpObjectProperty.size() / 2;
        ArrayList<String>[] buildListObjectProperty = new ArrayList[sizeListCtoC];
        for (int i = 0; i < sizeListCtoC; i++) {
            buildListObjectProperty[i] = new ArrayList<>();
        }

        int pointer = 0;
        for (int i = tmpObjectProperty.size() - 1; i >= 0; i = i - 2) {
            //System.out.println(tmpObjectProperty.get(i)+", "+tmpObjectProperty.get(i+1));
            String superClass = tmpObjectProperty.get(i - 1);
            String subClass = tmpObjectProperty.get(i);
            buildListObjectProperty[pointer].add(superClass);
            buildListObjectProperty[pointer].add("punya" + subClass);
            buildListObjectProperty[pointer].add(subClass);
            pointer++;
        }

        //final Class to class
        System.out.println("- Object Property: ");
        for (int i = 0; i < buildListObjectProperty.length; i++) {
            System.out.println("    " + buildListObjectProperty[i].toString());
        }
        //-----------------------------------------------------------------------------------------------------------------------        

        
        
        
        //list class to property-------------------------------------------------------------------------------------------------
        int sizeListCtoP = tmpDatatypeProperty.size() / 3;
        ArrayList<String>[] buildListDataTypeProperty = new ArrayList[sizeListCtoP];
        for (int i = 0; i < sizeListCtoP; i++) {
            buildListDataTypeProperty[i] = new ArrayList<>();
        }

        int pointer1 = 0;
        for (int i = tmpDatatypeProperty.size() - 1; i >= 0; i = i - 3) {
            String superClass = tmpDatatypeProperty.get(i - 2);
            String propertyClass = tmpDatatypeProperty.get(i - 1);
            String dataType = tmpDatatypeProperty.get(i);
            if (dataType.equalsIgnoreCase("number")) {
                buildListDataTypeProperty[pointer1].add(superClass);
                buildListDataTypeProperty[pointer1].add(propertyClass);
                buildListDataTypeProperty[pointer1].add("xsd:integer");
            } else {
                buildListDataTypeProperty[pointer1].add(superClass);
                buildListDataTypeProperty[pointer1].add(propertyClass);
                buildListDataTypeProperty[pointer1].add("xsd:" + dataType);
            }
            pointer1++;

        }

        System.out.println("");

        //final class to Property
        System.out.println("- DataType Property: ");
        for (int i = 0; i < buildListDataTypeProperty.length; i++) {
            System.out.println("    " + buildListDataTypeProperty[i].toString());
        }
        //-----------------------------------------------------------------------------------------------------------------------

        
        
        System.out.println("");
        
        

        //list Path property-----------------------------------------------------------------------------------------------------
        int sizeTmpListPathProperty = buildListDataTypeProperty.length;
        ArrayList<String>[] tmpListPathProperty = new ArrayList[sizeTmpListPathProperty];
        for (int i = 0; i < sizeTmpListPathProperty; i++) {
            tmpListPathProperty[i] = new ArrayList<>();
        }
        
        for (int i = 0; i < buildListDataTypeProperty.length; i++) {
            String propertyName = buildListDataTypeProperty[i].get(1);
            String parrent = buildListDataTypeProperty[i].get(0);
            tmpListPathProperty[i].add(propertyName);
            for (int j = buildListObjectProperty.length - 1; j >= 0; j--) {
                if (parrent.equalsIgnoreCase(buildListObjectProperty[j].get(2))) {
                    tmpListPathProperty[i].add(buildListObjectProperty[j].get(2));
                    for (int k = j; k >= 0; k--) {
                        if (k > 0) {
                            if (!buildListObjectProperty[k - 1].get(2).equalsIgnoreCase(buildListObjectProperty[k].get(0))) {
                                tmpListPathProperty[i].add(buildListObjectProperty[k].get(0));
                                break;
                            }
                            tmpListPathProperty[i].add(buildListObjectProperty[k - 1].get(2));
                        }

                    }
                }
            }
        }
//        //tmp path
        for (int i = 0; i < tmpListPathProperty.length; i++) {
            int last = tmpListPathProperty[i].size() - 1;
            if (!tmpListPathProperty[i].get(last).equalsIgnoreCase(buildListClass.get(0))) {
                tmpListPathProperty[i].add(buildListClass.get(0));

            }
        }

        System.out.println("tmp path property: ");
        for (int i = 0; i < tmpListPathProperty.length; i++) {
            System.out.println("    " + tmpListPathProperty[i].toString());
        }

        int sizeListPathProperty = buildListDataTypeProperty.length;
        ArrayList<String>[] listPathProperty = new ArrayList[sizeListPathProperty];
        for (int i = 0; i < sizeListPathProperty; i++) {
            listPathProperty[i] = new ArrayList<>();
        }
        for (int i = 0; i < tmpListPathProperty.length; i++) {
            listPathProperty[i].add("$.");
            for (int j = tmpListPathProperty[i].size() - 1; j >= 0; j--) {
                String field = tmpListPathProperty[i].get(j);
                for (int k = 0; k < listWithProperties.length; k++) {
                    if (field.equalsIgnoreCase(listWithProperties[k].get(0))) {
                        listPathProperty[i].add(field);
                        if (listWithProperties[k].get(1).equalsIgnoreCase("array")) {
                            listPathProperty[i].add("[*]");
                        } else if (listWithProperties[k].get(1).equalsIgnoreCase("object")) {
                            listPathProperty[i].add(".");
                        }

                    }
                }
            }
        }

        //final path
        ArrayList<String>[] buildListPathProperty = new ArrayList[sizeListPathProperty];
        for (int i = 0; i < listPathProperty.length; i++) {
            buildListPathProperty[i] = new ArrayList<>();
        }

        for (int i = 0; i < listPathProperty.length; i++) {
            String a = "";
            for (int j = 0; j < listPathProperty[i].size(); j++) {
                a = a + listPathProperty[i].get(j);
            }
            buildListPathProperty[i].add(a);
        }

        System.out.println("");

        System.out.println("- Path class property: ");
        for (int i = 0; i < buildListPathProperty.length; i++) {
            System.out.println("    " + buildListPathProperty[i].get(0));
        }
        
        ArrayList<String>[] valueProperty = new ArrayList[sizeListPathProperty];
        for (int i = 0; i < valueProperty.length; i++) {
            valueProperty[i] = new ArrayList<>();
        }
        
        for (int i = 0; i < buildListPathProperty.length; i++) {
            newGenerate.GetValueByPath(jsonFile, buildListPathProperty[i].get(0), valueProperty,i);
        }
        
        System.out.println("");
        
        System.out.println("value per property: ");
        for (int i = 0; i <valueProperty.length ; i++) {
            valueProperty[i].add(tmpListPathProperty[i].get(0));
            System.out.println("    "+valueProperty[i].toString());
        }
        //-----------------------------------------------------------------------------------------------------------------------

        System.out.println("");
        
        //List Individual--------------------------------------------------------------------------------------------------------
                
        ArrayList<String> tmpIndividual = new ArrayList<>();
        
        int counter;
        for (int i = 0; i < buildListDataTypeProperty.length; i++) {
            String clasIndividual = buildListDataTypeProperty[i].get(0);
            for (int j = i; j < buildListDataTypeProperty.length; j++) {
                if ((j<buildListDataTypeProperty.length-1) && !buildListDataTypeProperty[j].get(0).equalsIgnoreCase(buildListDataTypeProperty[j+1].get(0))) {
                    //System.out.println("statemen: "+buildListDataTypeProperty[i].get(0) +" : "+ buildListDataTypeProperty[i].get(1));
                    tmpIndividual.add(buildListDataTypeProperty[i].get(1));
                    tmpIndividual.add(buildListDataTypeProperty[i].get(0));
                    i=j;
                    break;
                }else if(j==buildListDataTypeProperty.length-1){
                    //System.out.println("statemen: " + buildListDataTypeProperty[i].get(1) +" : "+ buildListDataTypeProperty[i].get(1));
                    tmpIndividual.add(buildListDataTypeProperty[i].get(1));
                    tmpIndividual.add(buildListDataTypeProperty[i].get(0));
                    i = j;
                    break;
                }
               
            }
            
        }
        
//        System.out.println(String.valueOf(valueProperty[0].get(0)));
        int sizeListIndividual=tmpIndividual.size()/2*(valueProperty[1].size()-1);
        System.out.println("size: "+sizeListIndividual);
        ArrayList<String>[] buildListIndividual = new ArrayList[sizeListIndividual];
        for (int i = 0; i < buildListIndividual.length; i++) {
            buildListIndividual[i] = new ArrayList<>();
        }
        
        int pointerListIndividu=0;
//        for (int i = 0; i < valueProperty.length; i++) {
//            for (int j = 0; j < tmpIndividual.size()-1; j=j+2) {
//                if(tmpIndividual.get(j).equalsIgnoreCase(valueProperty[i].get(valueProperty[i].size()-1))){
//                    //System.out.println(valueProperty[i].get(valueProperty[i].size()-1));
////                    String namaList =valueProperty[i].get(0);
//                    //System.out.println(tmpIndividual.get(j+1));
//                    //System.out.println(i);
//                   String listName = String.valueOf(valueProperty[i].get(0));
//                   buildListIndividual[pointerListIndividu].add(listName);
//                   buildListIndividual[pointerListIndividu].add(tmpIndividual.get(j + 1));
//                   pointerListIndividu++;
//                }
//            }
//        }
        
        for (int i = 0; i < valueProperty.length; i++) {
            for (int j = 0; j < tmpIndividual.size() - 1; j = j + 2) {
                if (tmpIndividual.get(j).equalsIgnoreCase(valueProperty[i].get(valueProperty[i].size() - 1))) {
                    //System.out.println(valueProperty[i].get(valueProperty[i].size()-1));
//                    String namaList =valueProperty[i].get(0);
                    //System.out.println(tmpIndividual.get(j+1));
                    //System.out.println(i);
                    for (int k = 0; k < valueProperty[1].size()-1; k++) {
                        String listName = String.valueOf(valueProperty[i].get(k));
                        buildListIndividual[pointerListIndividu].add(listName);
                        buildListIndividual[pointerListIndividu].add(tmpIndividual.get(j + 1));
                        pointerListIndividu++;
                    }
                    
                }
            }
        }
        
        
        System.out.println("- List Individu name");
        for (int i = 0; i < buildListIndividual.length; i++) {
            System.out.println("    "+buildListIndividual[i].toString());
        }
        
        //-----------------------------------------------------------------------------------------------------------------------
        
        System.out.println("");
        
        
        //list Statement
        int sizeListStatementDP = valueProperty.length*((valueProperty[0].size()-1));
        System.out.println(sizeListStatementDP);
        ArrayList<String>[] buildListStatementDP= new ArrayList[sizeListStatementDP];
        for (int i = 0; i < buildListStatementDP.length; i++) {
            buildListStatementDP[i] = new ArrayList<>();
        }
        
        
        
        int pointerListStatement=0;
        for (int i = 0; i < buildListIndividual.length; i++) {
            String individuName = buildListIndividual[i].get(0);
            String className = buildListIndividual[i].get(1);
//            System.out.println(individuName);
            for (int j = 0; j < buildListDataTypeProperty.length; j++) {
                String classNameFromProperty = buildListDataTypeProperty[j].get(0);
                if(className.equalsIgnoreCase(classNameFromProperty)){
                    String propertyFromClassToProperty = buildListDataTypeProperty[j].get(1);
                    int pointerLiteral=0;
                    for (int k = 0; k < valueProperty.length; k++) {
//                        System.out.println(pointerLiteral);
                        String propertyFromValue = valueProperty[k].get(valueProperty[k].size()-1);
                        //for (int l = 0; l < valueProperty[k].size()-1; l++) {
                            for (int l = 0; l < valueProperty[k].size(); l++) {
                                if(individuName.equalsIgnoreCase(String.valueOf(valueProperty[k].get(l)))){
                                    pointerLiteral = l;
                                }
                            }
                         
                            if (propertyFromClassToProperty.equalsIgnoreCase(propertyFromValue)) {
                                buildListStatementDP[pointerListStatement].add(individuName);
                                buildListStatementDP[pointerListStatement].add("punya" + propertyFromValue);
                                buildListStatementDP[pointerListStatement].add(String.valueOf(valueProperty[k].get(pointerLiteral)));
                                //System.out.println(individuName + ", " + "punya"+propertyFromValue + ", " + String.valueOf(valueProperty[k].get(pointerLiteral))); 
                                pointerListStatement++;
                            }
                           
                        //}
                    }
                }
            }
            
        }
        
        System.out.println("- List Statemen: ");
        for (int i = 0; i < buildListStatementDP.length; i++) {
            System.out.println("    "+buildListStatementDP[i].toString());
        }
        
        //-----------------------------------------------------------------------------------------------------------------------

        System.out.println("");
        
        //list Statement Object Property
        
//        System.out.println(sizeListStatement);
        ArrayList<String>[] buildListStatementOP= new ArrayList[buildListIndividual.length - (valueProperty[0].size()-1)];
        for (int i = 0; i < buildListStatementOP.length; i++) {
            buildListStatementOP[i] = new ArrayList<>();
        }
        
        int pointerListStatementOP = valueProperty[0].size()-1;
        for (int i = 0; i < buildListStatementOP.length; i++) {
            String namaIndividu = buildListIndividual[i].get(0);
            String namaKelas = buildListIndividual[pointerListStatementOP].get(1);
            String literal = buildListIndividual[pointerListStatementOP].get(0);
            buildListStatementOP[i].add(namaIndividu);
            buildListStatementOP[i].add("punya"+namaKelas);
            buildListStatementOP[i].add(literal);
            pointerListStatementOP++;
        }
        
        System.out.println("List Statement Object property: ");
        for (int i = 0; i < buildListStatementOP.length; i++) {
            System.out.println("    "+buildListStatementOP[i].toString());
        }
        
        
        //-----------------------------------------------------------------------------------------------------------------------
        
        //Input list fo OWL ontology
        
        BuildOntology CreateOntoogy = new BuildOntology();
        CreateOntoogy.BuildOWLOntology(buildListClass, buildListObjectProperty, buildListDataTypeProperty, buildListIndividual, buildListStatementDP, buildListStatementOP);
        
        //-----------------------------------------------------------------------------------------------------------------------
    }
    
}
