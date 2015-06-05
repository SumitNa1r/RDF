package edu.neu.cs8764.rdf;


import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
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
        String activityURI1    = "http://cs8764/neu/edu/TEMP";
        String activityIDURI = "http://cs8764/neu/edu/Activity/ID";
        String activityNameURI = "http://cs8764/neu/edu/Activity/NAME";
        String has_property = "http://cs8764/neu/edu/Activity/HAS_PROPERTY";
        
        int id = 0;
        String name   = "name";
        
        // create an empty model

		Model model = ModelFactory.createDefaultModel();
		Model model1 = ModelFactory.createDefaultModel();
		

		for(int i= 0; i< 10; i++){
		Property id_property = model.getProperty(activityIDURI);
		Property name_property = model.getProperty(activityNameURI);
		Property hp = model.getProperty(has_property);
		
		Resource r1 = model1.createResource(activityURI1+"#"+i)
				.addProperty(id_property, i+"")
				.addProperty(name_property, name + i);
		
		Resource r = model.createResource(activityURI+"#"+i)
				.addProperty(id_property, i+"")
				.addProperty(name_property, name + i)
				.addProperty(hp, r1);
		}
		
		/*RDFDataMgr.write(System.out, model1, Lang.NTRIPLES) ;
		System.out.println("--------------------------------------------------");
		RDFDataMgr.write(System.out, model, Lang.NTRIPLES) ;*/
		//print_details(model);
		
		String queryString = 
	            "SELECT ?name_property WHERE {?name_property <http://cs8764/neu/edu/Activity/NAME> \"name9\"}" ;
		Query query = QueryFactory.create(queryString) ;
		
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
       
        try {
            ResultSet rs = qexec.execSelect() ;
            while(rs.hasNext()) {
            	System.out.println("Name: ") ;
                QuerySolution rb = rs.nextSolution() ;
                //System.out.println(rb + "\n");
                
                RDFNode x = rb.get("name_property") ;
                System.out.println("   "+x) ;
            }
        } finally  {
            qexec.close() ;
        }

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
