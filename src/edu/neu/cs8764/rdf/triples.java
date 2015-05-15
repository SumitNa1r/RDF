package edu.neu.cs8764.rdf;


import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class triples extends Object {
	public static void main(String args[]) {
		// some definitions
        String activityURI    = "http://cs8764/neu/edu/Activity";
        String activityIDURI = "http://cs8764/neu/edu/Activity/ID";
        String activityNameURI = "http://cs8764/neu/edu/Activity/NAME";
        int id = 0;
        String name   = "name";
        
        // create an empty model

		Model model = ModelFactory.createDefaultModel();

		for(int i= 0; i< 10; i++){
		Property id_property = model.getProperty(activityIDURI + "#"+i);
		Property name_property = model.getProperty(activityNameURI + "#"+i);

		Resource r = model.createResource(activityURI+i)
				.addProperty(id_property, i+"")
				.addProperty(name_property, name + i);
		}
		RDFDataMgr.write(System.out, model, Lang.NTRIPLES) ;
		//print_details(model);

	}
	
	public static void print_details(Model model){
		// list the statements in the graph
        StmtIterator iter = model.listStatements();
        
        // print out the predicate, subject and object of each statement
        while (iter.hasNext()) {
            Statement stmt      = iter.nextStatement();         // get next statement
            com.hp.hpl.jena.rdf.model.Resource  subject   = stmt.getSubject();   // get the subject
            Property  predicate = stmt.getPredicate(); // get the predicate
            RDFNode   object    = stmt.getObject();    // get the object
            
            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }
            System.out.println(" .");
        }
	}
}
