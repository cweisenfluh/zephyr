package com.zephyr.index;

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
	
	private static List<AuthorEntity> mmAuthorsList  = new ArrayList<AuthorEntity>();	
	private static final String PUB_MED_JSONFILE = "data/pubmed_result.json";
	
	static {
		mmAuthorsList.add(new AuthorEntity("William", "Bensinger", "", "Fred Hutchinson Cancer Research Center, Seattle, WA"));
		mmAuthorsList.add(new AuthorEntity("Leif", "Bergsagel",   "",  "Mayo Clinic, Scottsdale, AZ"));
		mmAuthorsList.add(new AuthorEntity("Adam", "Cohen",   "",  "Fox Chase Cancer Center, Philadelphia, PA"));
		mmAuthorsList.add(new AuthorEntity("Hermann", "Einsele",  "",  "University of Würzburg, Germany"));
		mmAuthorsList.add(new AuthorEntity("Amrita", "Krishnan",  "",  "City of Hope, Duarte, CA"));
		
		AuthorEntity ae = new AuthorEntity("Edward", "Libby", "",  "Fred Hutchinson Cancer Research Center");
		ae.addAffilition("University of Washington, Seattle, WA");
		mmAuthorsList.add(ae);

		ae = new AuthorEntity("Sagar", "Lonial", "", "Winship Cancer Institute");
		ae.addAffilition("Emory University School of Medicine, Atlanta, GA");
		mmAuthorsList.add(ae);

		mmAuthorsList.add(new AuthorEntity("Heinz", "Ludwig", "", "Wilhelminenspital, Vienna, Austria"));
		
		mmAuthorsList.add(new AuthorEntity("María-Victoria", "Mateos",  "",  "University Hospital of Salamanca, Spain"));
		mmAuthorsList.add(new AuthorEntity("Philip", "McCarthy",  "", "Roswell Park Cancer Institute, Buffalo, NY"));
		mmAuthorsList.add(new AuthorEntity("S Vincent", "Rajkumar", "",  "Dana-Farber Cancer Institute Harvard Medical School, Boston, MA"));
		mmAuthorsList.add(new AuthorEntity("Paul", "Richardson", "",  "Wilhelminenspital, Vienna, Austria"));
		mmAuthorsList.add(new AuthorEntity("Jatin", "Shah", "",  "MD Anderson The University of Texas, Houston, TX"));
		mmAuthorsList.add(new AuthorEntity("Saad", "Zafar Usmani", "",  "Wilhelminenspital, Vienna, Austria"));
		mmAuthorsList.add(new AuthorEntity("David", "Vesole",  "",  "John Theurer Cancer Center Hackensack University Medical Center, Hackensack, NJ"));
		
		mmAuthorsList.add(new AuthorEntity("Ravi", "Vij", "",  "Washington University in Saint Louis, MO"));
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
			
		//	System.out.println(authorSet.size());
			
			for (AuthorEntity ae : mmAuthorsList) {
				String firstLast = ae.getFirstLast();
				
				if (firstLast == null) {
					// should never happen
					Assert.fail();
				}

				
			//	System.out.println(((Author)ae).hashCode());

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


		for ( Entry<AuthorEntity, Integer> entry: countMap.entrySet()) {
			StringBuffer sb = new StringBuffer();
			sb.append("author: ").
			   append(entry.getKey().getFirstName() + " " + entry.getKey().getLastName()  ).
			   append("\tpubs: ").
			   append(entry.getValue());
		
			System.out.println(sb.toString());
		}
	}
}


