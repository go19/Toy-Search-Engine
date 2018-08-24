package tse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages
 * in which it occurs, with frequency of occurrence in each page.
 *
 */
public class ToySearchEngine {

	/**
	 * This is a hash table of all keys. The key is the actual keyword, and the
	 * associated value is an array list of all occurrences of the keyword in
	 * documents. The array list is maintained in DESCENDING order of
	 * frequencies.
	 */
	HashMap<String, ArrayList<Occurrence>> keysIndex;

	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;

	/**
	 * Creates the keysIndex and noiseWords hash tables.
	 */
	public ToySearchEngine() {
		keysIndex = new HashMap<String, ArrayList<Occurrence>>(1000, 2.0f);
		noiseWords = new HashSet<String>(100, 2.0f);
	}

	/**
	 * Scans a document, and loads all keywords found into a hash table of key
	 * occurrences in the document. Uses the getKey method to separate keywords
	 * from other words.
	 * 
	 * @param docFile
	 *            Name of the document file to be scanned and loaded
	 * @return Hash table of keys in the given document, each associated with an
	 *         Occurrence object
	 * @throws FileNotFoundException
	 *             If the document file is not found on disk
	 */
	public HashMap<String, Occurrence> loadKeysFromDocument(String docFile) throws FileNotFoundException {
		/** COMPLETE THIS METHOD **/
	
		HashMap<String, Occurrence> map = new HashMap<String, Occurrence>(1000, 2.0f);
		Scanner sc = new Scanner(new File(docFile));

		while (sc.hasNext()) {
			String temp = getKey(sc.next());

			if (temp == null) {
				continue;
			}

			if (map.containsKey(temp)) {
				Occurrence mustupdate = map.get(temp);
				mustupdate.frequency++;
				map.put(temp, mustupdate);

			} else {
				Occurrence first = new Occurrence(docFile, 1);
				map.put(temp, first);
			}
		}
		sc.close();

		return map;
	}

	/**
	 * Merges the keys for a single document into the master keysIndex hash
	 * table. For each key, its Occurrence in the current document must be
	 * inserted in the correct place (according to descending order of
	 * frequency) in the same key's Occurrence list in the master hash table.
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws
	 *            Keywords hash table for a document
	 */
	public void mergeKeys(HashMap<String, Occurrence> kws) {

		for (String key : kws.keySet()) {

			if (keysIndex.containsKey(key)) { // if the keyIndex already
												// contains key

				ArrayList<Occurrence> test = keysIndex.get(key); // get already
																	// occuring
																	// key
				Occurrence testing = kws.get(key); //

				test.add(testing);

				insertLastOccurrence(test);
				keysIndex.put(key, test);

			} else { // inserting new key

				ArrayList<Occurrence> temp = new ArrayList<Occurrence>();
				Occurrence second = kws.get(key);
				temp.add(second);
				keysIndex.put(key, temp);
			}

		}
		// System.out.println("This is keywordIndex: " + keywordsIndex);

	}

	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped
	 * of any trailing punctuation, consists only of alphabetic letters, and is
	 * not a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * Note: No other punctuation characters will appear in grading testcases
	 * 
	 * @param word
	 *            Candidate word
	 * @return Key (word without trailing punctuation, LOWER CASE)
	 */
	public String getKey(String word) {
		/** COMPLETE THIS METHOD **/

		if (word == null) {
			return null;
		}

		for (int i = 0; i < word.length() - 1; i++) {
			if ((word.charAt(i) == ',' || word.charAt(i) == '.' || word.charAt(i) == '?' || word.charAt(i) == ':'
					|| word.charAt(i) == ';' || word.charAt(i) == '!') && Character.isLetter(word.charAt(i + 1))) {
				System.out.println("null");
				return null;

			}

		}

		word = word.replaceAll("[.,?:;!]", "").toLowerCase();

		if (!word.matches("[a-zA-Z]+")) {
			// System.out.println("null");
			return null;
		}

		if (noiseWords.contains(word)) {

			// System.out.println("null");
			return null;
		}

		// System.out.println(word);
		return word;

	}

	/**
	 * Inserts the last occurrence in the parameter list in the correct position
	 * in the list, based on ordering occurrences on descending frequencies. The
	 * elements 0..n-2 in the list are already in the correct order. Insertion
	 * is done by first finding the correct spot using binary search, then
	 * inserting at that spot.
	 * 
	 * @param occs
	 *            List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the
	 *         binary search process, null if the size of the input list is 1.
	 *         This returned array list is only used to test your code - it is
	 *         not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/
		
		//System.out.println(occs);
		if(occs.size()==1){
			System.out.println("kanye west is mad");
			return null;
		}

		ArrayList<Integer> midpoints = new ArrayList<Integer>();
		int low = 0;
		int high = occs.size() - 2; // at Start high = 5

		int lastfrequency = occs.get(occs.size() - 1).frequency;
		// System.out.println("size " + occs);

		if (occs.size() == 2) // all cases for this project since two documents
								// only
		{
			if (occs.get(1).frequency > occs.get(0).frequency) {
				Occurrence secondelement = occs.remove(1);
				occs.add(0, secondelement);
			}

			midpoints.add(0); // since 0 is only one for mid

			return midpoints;

		}

		while (high >= low) {

			int mid = (low + high) / 2;
			// System.out.println(mid);
			int midfrequency = occs.get(mid).frequency; // 1

			midpoints.add(mid);

			if (lastfrequency < midfrequency) {
				// System.out.println("this is mid " + midfrequency);
				low = mid + 1;
			}
			if (lastfrequency > midfrequency) {
				// System.out.println( "Yea + " + midfrequency);
				high = mid - 1;
			}

			if (lastfrequency == midfrequency) {
				//midpoints.add(mid);
				high = mid-1;

				break;
			}

		}

	//	System.out.println(occs  + " before insert");
		Occurrence elementinsert = occs.remove(occs.size() - 1);
		//System.out.println(elementinsert + " to insert");

		occs.add(high + 1, elementinsert);

		//System.out.println(midpoints);
		return midpoints;

	}

	/**
	 * This method indexes all words found in all the input documents. When this
	 * method is done, the keysIndex hash table will be filled with all keys,
	 * each of which is associated with an array list of Occurrence objects,
	 * arranged in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile
	 *            Name of file that has a list of all the document file names,
	 *            one name per line
	 * @param noiseWordsFile
	 *            Name of file that has a list of noise words, one noise word
	 *            per line
	 * @throws FileNotFoundException
	 *             If there is a problem locating any of the input files on disk
	 */
	public void buildIndex(String docsFile, String noiseWordsFile) throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}

		// index all words
		sc = new Scanner(new File(docsFile));

		while (sc.hasNext()) {
			String docFile = sc.next();

			HashMap<String, Occurrence> kws = loadKeysFromDocument(docFile);

			mergeKeys(kws);

		}

		sc.close();
	}

	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or
	 * kw2 occurs in that document. Result set is arranged in descending order
	 * of document frequencies. (Note that a matching document will only appear
	 * once in the result.) Ties in frequency values are broken in favor of the
	 * first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is
	 * in doc2 also with the same frequency f1, then doc1 will take precedence
	 * over doc2 in the result. The result set is limited to 5 entries. If there
	 * are no matches at all, result is null.
	 * 
	 * @param kw1
	 *            First keyword
	 * @param kw1
	 *            Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in
	 *         descending order of frequencies. The result size is limited to 5
	 *         documents. If there are no matches, returns null.
	 */

	public ArrayList<String> top5search(String kw1, String kw2) {

		ArrayList<Occurrence> a1 = keysIndex.get(kw1);
		ArrayList<Occurrence> a2 = keysIndex.get(kw2);

		ArrayList<String> result = new ArrayList<String>();



		ArrayList<Integer> t1 = new ArrayList<Integer>();
		ArrayList<Integer> t2 = new ArrayList<Integer>();
		
		
		
		
		ArrayList<Occurrence> result1 = new ArrayList<Occurrence>();
		ArrayList<Occurrence> result2 = new ArrayList<Occurrence>();
		

		if (a1 != null && a2 != null) {
			for (int i = 0; i < a1.size(); i++) {

				String a = a1.get(i).document;
				int ai = a1.get(i).frequency;

				for (int j = 0; j < a2.size(); j++) {
					String b = a2.get(j).document;
					int bi = a2.get(j).frequency;
					if (a == b && ai > bi && !t2.contains(j)) {
						result2.add(a2.get(j));
						//t2.add(j);
					
					} else if (a == b && ai < bi && !t1.contains(i)) {
						result1.add(a1.get(i));
						//t1.add(i);
					
					} else if (a == b && ai == bi && !t2.contains(j)) {
						//System.out.println(j);
		
						result2.add(a2.get(j));
						//t2.add(j);
						
					}
					else if( a!=b && ai==bi && !t2.contains(j) ){
						
						result2.add(a2.get(j));
						//t2.add(j);				
					}
			
				}
			}
	
	
			a1.removeAll(result1);
	
			
			
		}

	


		if (a1 != null && a2 != null) {
			for (int doclength = 0; (doclength < a1.size() && doclength < a2.size()); doclength++) {

				int frequency1 = a1.get(doclength).frequency;
				int frequency2 = a2.get(doclength).frequency;

				if (frequency1 > frequency2) {

					result.add(a1.get(doclength).document);

					if (a1.size() > doclength + 1) {
						if (a1.get(doclength + 1).frequency > frequency2) {
							result.add(a1.get(doclength + 1).document);
							result.add(a2.get(doclength).document);
						} else {
							
							result.add(a2.get(doclength).document);
						}
					} else {
				
						result.add(a2.get(doclength).document);
					}

				} else {
				

					result.add(a2.get(doclength).document);

					if (a2.size() > doclength + 1) {
						if (a2.get(doclength + 1).frequency > frequency1) {
							result.add(a2.get(doclength + 1).document);
							result.add(a1.get(doclength).document);
						} else {
							
							result.add(a1.get(doclength).document);
						}
					} else {
				
						result.add(a1.get(doclength).document);
					}

				}

			}

		}
		
		LinkedHashSet<String> as = new LinkedHashSet<String>();
		as.addAll(result);
		result.clear();
		result.addAll(as);
		
		
		if (a1 != null && a2 == null) {
			for (int i = 0; i < a1.size(); i++) {
				result.add(a1.get(i).document);
			}
		}

		if (a1 == null && a2 != null) {
			for (int i = 0; i < a2.size(); i++) {
				result.add(a2.get(i).document);
			}
		}

		if (a1 != null && a2 != null) {
			if (result.size() < 5 && a1.size() > a2.size()) {
				for (int i = a2.size(); i < a1.size(); i++) {
					result.add(a1.get(i).document);
				}

			}

			if (result.size() < 5 && a2.size() > a1.size()) {
				for (int i = a1.size(); i < a1.size(); i++) {
					result.add(a2.get(i).document);
				}

			}

		}
		if (result.size() > 5) {
			while (result.size() > 5) {
				result.remove(result.size() - 1);
			}
		}

		System.out.println("HERE + " + result);

		return result;

	}
}
