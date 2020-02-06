package data;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class AuthorsDict extends TreeMap<Integer, AuthorObj> {
	
	private int id = -1;
	private AuthorObj author = null;
	
	private Integer idLAST = 0;
	
	protected TreeMap<AuthorObj, Integer> mapReverse = new TreeMap<> (new ComparatorAuthors());
	private boolean isMapBuilt = false;
	
	public AuthorsDict() {
	}
	
	// ++++++++ MEMBER FUNCTIONS ++++++++++++
	
	// ++++ Authors ++++

	@Override
	public AuthorObj put(final Integer id, final AuthorObj publisher) {
		if(idLAST < id) {
			idLAST = id;
		}
		return super.put(id, publisher);
	}
	
	protected void Build() {
		for(final Map.Entry<Integer, AuthorObj> entry : this.entrySet()) {
			mapReverse.put(entry.getValue(), entry.getKey());
		}
		isMapBuilt = true;
	}
	
	// old Sax code
	public void SetID(final int id) {
		this.id = id;
		this.author = new AuthorObj();
		this.put(id, author);
		if(idLAST < id) {
			idLAST = id;
		}
	}
	public void Put(final String sName, final boolean isName) {
		if(id > 0) {
			if(isName) {
				author.sName = sName;
			} else {
				author.sGivenName = sName;
			}
		}
	}

	public BoolPair<Integer> GetOrSetAuthor(final AuthorObj author) {
		if( ! isMapBuilt) {
			this.Build();
		}
		final BoolPair<Integer> id = new BoolPair<> ();
		{
			final Integer idAuthor = mapReverse.get(author);
			if(idAuthor != null) {
				id.isE = false; // NOT new Author
				id._E  = idAuthor;
				return id; 
			}
		}
		
		final Integer idAuthor = ++ idLAST;
		author.idAuthor = idAuthor;
		
		this.put(idLAST, author);
		mapReverse.put(author, idLAST);
		id.isE = true;
		id._E  = idAuthor;
		return id;
	}
	
	
	public static class ComparatorAuthors implements Comparator<AuthorObj> {

		@Override
		public int compare(final AuthorObj aut1, final AuthorObj aut2) {
			if(aut1 == null) {
				if(aut2 != null) { return -1; }
				return 0; // NO further comparisons
			} else if(aut2 == null) { return 1; }
			
			if(aut1.sName == null) {
				if(aut2.sName != null) { return -1; }
			} else if(aut2.sName == null) { return 1; }
			{
				final int cmpName = aut1.sName.compareTo(aut2.sName);
				if(cmpName != 0) { return cmpName; }
			}
			
			if(aut1.sGivenName == null) {
				if(aut2.sGivenName != null) { return -1; }
				return 0; // NO further comparisons
			} else if(aut2.sGivenName == null) { return 1; }
			
			return aut1.sGivenName.compareTo(aut2.sGivenName);
		}
	}
}
