package com.zephyr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import com.zephyr.load.PubMedDataSource;
import com.zephyr.model.Author;
import com.zephyr.model.AuthorEntity;
import com.zephyr.model.PubMedEntry;

import junit.framework.Assert;


public class MultipleMyelomaAuthorsTest {
	
	private static Set<AuthorEntity> mmAuthorsSet  = new HashSet<AuthorEntity>();	
	private static final String PUB_MED_JSONFILE = "data/pubmed_result.json";
	
	static {
		/*
		 * note:  the pub counts here are used as a control for the unit test, to make sure the result set coming back is correct
		 * they were generated vi bin/control.sh
		 */
		mmAuthorsSet.add(new AuthorEntity("William", "Bensinger", "M.D.", "Fred Hutchinson Cancer Research Center, Seattle, WA", 5));
		mmAuthorsSet.add(new AuthorEntity("P Leif", "Bergsagel",   "M.D.",  "Mayo Clinic, Scottsdale, AZ", 27));
		mmAuthorsSet.add(new AuthorEntity("Adam D", "Cohen",   "M.D.",  "Fox Chase Cancer Center, Philadelphia, PA", 3));
		mmAuthorsSet.add(new AuthorEntity("Hermann", "Einsele",  "M.D.",  "University of Würzburg, Germany", 23));
		mmAuthorsSet.add(new AuthorEntity("Amrita Y", "Krishnan",  "M.D., FACP",  "City of Hope, Duarte, CA", 2));
		
		AuthorEntity ae = new AuthorEntity("Edward", "Libby", "M.D.",  "Fred Hutchinson Cancer Research Center", 0);
		ae.addAffilition("University of Washington, Seattle, WA");
		mmAuthorsSet.add(ae);

		ae = new AuthorEntity("Sagar", "Lonial", "M.D.", "Winship Cancer Institute", 35);
		ae.addAffilition("Emory University School of Medicine, Atlanta, GA");
		mmAuthorsSet.add(ae);

		mmAuthorsSet.add(new AuthorEntity("Heinz", "Ludwig", "M.D.", "Wilhelminenspital, Vienna, Austria", 23));
		
		mmAuthorsSet.add(new AuthorEntity("María-Victoria", "Mateos",  "M.D., Ph.D.",  "University Hospital of Salamanca, Spain", 4));
		mmAuthorsSet.add(new AuthorEntity("P", "McCarthy",  "M.D.", "Roswell Park Cancer Institute, Buffalo, NY", 8));
		mmAuthorsSet.add(new AuthorEntity("S Vincent", "Rajkumar", "M.D.",  "Dana-Farber Cancer Institute Harvard Medical School, Boston, MA", 71));
		mmAuthorsSet.add(new AuthorEntity("Paul", "Richardson", "M.D.",  "Wilhelminenspital, Vienna, Austria", 23));
		mmAuthorsSet.add(new AuthorEntity("Jatin", "Shah", "M.D.",  "MD Anderson The University of Texas, Houston, TX", 3));
		mmAuthorsSet.add(new AuthorEntity("Saad", "Usmani", "M.D., FACP",  "Wilhelminenspital, Vienna, Austria", 2));
		mmAuthorsSet.add(new AuthorEntity("David", "Vesole",  "M.D., FACP",  "John Theurer Cancer Center Hackensack University Medical Center, Hackensack, NJ", 6));
		
		mmAuthorsSet.add(new AuthorEntity("Ravi", "Vij", "M.D.",  "Washington University in Saint Louis, MO", 18));
	}
	
	@Test
	public void testAuthorHash() {
		
		Author author = new Author("María-Victoria", "Mateos", "University Hospital of Salamanca, Spain");
		AuthorEntity ae = new AuthorEntity(author);
		int authorEntityHash = ae.hashCode();
		int authorHash = author.hashCode();
		
		Assert.assertTrue(authorEntityHash == authorHash);
		
		List<AuthorEntity> aeList = new ArrayList<AuthorEntity>();
		aeList.add(ae);
		
		Set<Author> authorSet = new HashSet<Author>(aeList);
		
		Assert.assertTrue(authorSet.contains(author));
	
	}
		

	@Test
	public void testLoader() {
		
		PubMedDataSource pmds = null;
		Map<AuthorEntity, Integer> countMap = new HashMap<AuthorEntity, Integer>();
		
			// load pub med
		try {
			pmds = new PubMedDataSource(PUB_MED_JSONFILE);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("unable to load data object");
		}
		
		Map<String, AuthorEntity> authorMap = pmds.getAuthors();

		List<PubMedEntry> pubMedList = pmds.getEntries();
		
		for (PubMedEntry pme : pubMedList) {
			List<Author> authorList = pme.getAuthorList();

			Set<Author> authorSet = new HashSet<Author>(authorList);
						
			for (AuthorEntity ae : mmAuthorsSet) {
				String firstLast = ae.getFirstLast();
				
				if (firstLast == null) {
					// should never happen
					Assert.fail();
				}

				if (authorSet.contains((Author)ae)) {
					Integer ct = 1;
					
					if (countMap.containsKey(ae)) {
						ct = countMap.get(ae);
						ct++;
					}
					countMap.put(ae, ct);
				}
			}
		}

			// these are the goodies we found
		for ( Entry<AuthorEntity, Integer> entry: countMap.entrySet()) {
			StringBuffer sb = new StringBuffer();
			sb.append("author: ").
			   append(entry.getKey().getFirstName() + " " + entry.getKey().getLastName()  ).
			   append("\tpubs: ").
			   append(entry.getValue());
		
			System.out.println(sb.toString());			
		}
		
			// sanity check against control
		for (AuthorEntity authorEntity : mmAuthorsSet) {
			if (countMap.containsKey(authorEntity)) {
				int pubs = countMap.get(authorEntity);
				Assert.assertEquals(pubs,authorEntity.getPubs());
			} else {
				if (authorEntity.getPubs() != 0) {
					Assert.fail();
				}
			}
		}
	}
}


