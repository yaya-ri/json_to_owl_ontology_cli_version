/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GenerateData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.util.PrintUtil;
import org.apache.jena.vocabulary.XSD;

/**
 *
 * @author yaya_aye
 */

public class BuildOntology {
    
    public void BuildOWLOntology(ArrayList<String> listClass, ArrayList<String>[] listObjectProperty, ArrayList<String>[] listDataTypeProperty) throws FileNotFoundException {

//        org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);

        String mergeURI = "http://www.example.com/merge.owl#";
        PrintUtil.registerPrefix("merge", mergeURI);

        OntModel mo = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        mo.setNsPrefix("", mergeURI);
        mo.setNsPrefix("base", mergeURI);

//        Create Class
        System.out.println(listClass.size());
        System.out.println(listClass.size());
        for (int i = 0; i < listClass.size(); i++) {
            mo.createClass(mergeURI + listClass.get(i));
        }

//        Create Object Property
        for (int i = 0; i < listObjectProperty.length; i++) {
            ObjectProperty oop = mo.createObjectProperty(mergeURI + listObjectProperty[i].get(1));
            oop.addDomain(mo.getResource(mergeURI + listObjectProperty[i].get(0)));
            oop.addRange(mo.getResource(mergeURI + listObjectProperty[i].get(2)));
        }

        //Create Data type property
        for (int i = 0; i < listDataTypeProperty.length; i++) {
            DatatypeProperty dt = mo.createDatatypeProperty(mergeURI + listDataTypeProperty[i].get(1));
            dt.addDomain(mo.getResource(mergeURI + listDataTypeProperty[i].get(0)));
            dt.addRange(XSD.xstring);
        }

//        for (int i = 0; i < listDataTypeProperty.length; i++) {
//            OntClass newclass = mo.getOntClass(mergeURI + listDataTypeProperty[i].get(0));
//            mo.createIndividual(mergeURI + listDataTypeProperty[i].get(2), newclass);
//        }
//
//        for (int i = 0; i < listDataTypeProperty.length; i++) {
//            Resource ex = mo.getResource(mergeURI + listDataTypeProperty[i].get(0));
//            Property ex1 = mo.getProperty(mergeURI + listDataTypeProperty[i].get(1));
//            Statement news = mo.createStatement(ex, ex1, "punya");
//            mo.add(news);
//        }
        String basePath = new File("").getAbsolutePath();
        String finalPath = basePath.replace("\\", "\\\\");
        String fileName = "OntologyFromJsonFile.owl";
        mo.write(new FileOutputStream("C:\\Users\\yaya_aye\\Documents\\NetBeansProjects\\TA_cli_maven\\" + fileName));

    }

    
}
