
package tse;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Driver {

	public static void main(String[] args) throws FileNotFoundException{
		ToySearchEngine test = new ToySearchEngine();
		String noise = "noisewords.txt";
		String docs = "docs.txt";
		
/*		
	
		
		ArrayList<Occurrence> occs = new ArrayList<Occurrence>();
		Occurrence e = new Occurrence("testing1.txt", 100);
		Occurrence d = new Occurrence("testing1.txt", 90);
		Occurrence f = new Occurrence("testing1.txt", 80);
		Occurrence g = new Occurrence("testing1.txt", 70);
		Occurrence h = new Occurrence("testing1.txt", 60);
		Occurrence i = new Occurrence("testing1.txt", 50 );
		Occurrence h1 = new Occurrence("testing1.txt", 50);
		
		Occurrence h2= new Occurrence("testing1.txt", 50);
		Occurrence h3 = new Occurrence("testing1.txt", 38);
		Occurrence h4= new Occurrence("testing1.txt", 37);
		Occurrence h5= new Occurrence("testing1.txt", 36);
		Occurrence h22= new Occurrence("testing1.txt", 35);
		Occurrence h6 = new Occurrence("testing1.txt", 34);
		Occurrence h7= new Occurrence("testing1.txt", 33);
		Occurrence h8= new Occurrence("testing1.txt", 32);
		Occurrence h9= new Occurrence("testing1.txt", 30);
		Occurrence h21= new Occurrence("testing1.txt", 50);
		occs.add(e );
		occs.add(d);
		occs.add(f);
		occs.add(g);
		occs.add(h);
		occs.add(i);
		occs.add(h1);
		occs.add(h2);
		occs.add(h3);
		occs.add(h4);
		occs.add(h5);
		occs.add(h22);
		occs.add(h6);
		occs.add(h7);
		occs.add(h8);
		occs.add(h9);
		occs.add(h21);
	
		
		System.out.println(occs);
		test.insertLastOccurrence(occs);
		System.out.println(occs);
		*/
		test.buildIndex(docs, noise);
		test.top5search("deep", "weqrwr");
		
	}

}
