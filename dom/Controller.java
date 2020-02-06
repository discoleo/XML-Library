package dom;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.AuthorObj;
import data.AuthorsDict;
import data.BookObj;
import data.DomainMap;
import data.GetMapsINTF;
import data.PublisherMap;

public class Controller {

	// Dicts
	protected final DomainMap mapDomains;
	protected final PublisherMap mapPublishers;
	protected final AuthorsDict mapAuthors;

	protected final Vector<BookObj> vBooks;
	
	public Controller(final GetMapsINTF maps) {
		mapDomains = maps.GetDomainsMap();
		mapPublishers = maps.GetPublisherMap();
		mapAuthors = maps.GetAuthorsDict();
		
		vBooks = maps.GetBooks();
	}
	
	// +++++++++++++++++++++++++++++++++++
	
	// Search Title of books
	public Vector<BookObj> FindBooksByTitle(final String sTitle) {
		final String smTitle = sTitle.toLowerCase();
		final Vector<BookObj> vBooksRes = new Vector<> ();
		
		vBooks.stream().forEach((book) -> {
			if(book.sTitle.toLowerCase().contains(smTitle)) {
				vBooksRes.add(book);
			}
		});
		
		return vBooksRes;
	}
	// Search Title of books using a Regex
	public Vector<BookObj> FindBooksByRegexTitle(final String sRegexTitle) {
		final Pattern pattTitle = Pattern.compile(sRegexTitle);
		final Matcher matcher = pattTitle.matcher("");
		
		final Vector<BookObj> vBooksRes = new Vector<> ();
		
		vBooks.stream().forEach((book) -> {
			matcher.reset(book.sTitle);
			if(matcher.find()) {
				vBooksRes.add(book);
			}
		});
		
		return vBooksRes;
	}
	
	// Search Author of a book
	public Vector<BookObj> FindBooksByAuthor(final String sAuthor) {
		final String smAuthor = sAuthor.toLowerCase();
		final Vector<BookObj> vBooksRes = new Vector<> ();
		
		vBooks.stream().forEach((book) -> {
			for(final AuthorObj author : book.vAuthors) {
				// TODO: Name + Given Name
				if(author.sName.toLowerCase().contains(smAuthor)) {
					vBooksRes.add(book);
				}
			}
		});
		
		return vBooksRes;
	}
	
	// Search after number of Authors of a book
	public Vector<BookObj> FindBooksByMinAuthors(final int minCount) {
		final Vector<BookObj> vBooksRes = new Vector<> ();
		
		vBooks.stream().forEach((book) -> {
			if(book.vAuthors.size() >= minCount) {
				vBooksRes.add(book);
			}
		});
		
		return vBooksRes;
	}

	// Search books by Domain
	public Vector<BookObj> FindBooksByDomain(final String sDomain) {
		final String smDomain = sDomain.toLowerCase();
		final Vector<BookObj> vBooksRes = new Vector<> ();
		
		vBooks.stream().forEach((book) -> {
			if(book.domain.sDomain.toLowerCase().contains(smDomain)) {
				vBooksRes.add(book);
			}
		});
		
		return vBooksRes;
	}
	
	// +++ Date +++

	// Search for books published during a Year
	public Vector<BookObj> FindBooksByYear(final int iYear) {
		final Vector<BookObj> vBooksRes = new Vector<> ();
		
		vBooks.stream().forEach((book) -> {
			if(book.iYear == iYear) {
				vBooksRes.add(book);
			}
		});
		
		return vBooksRes;
	}

	// Search for books published during a Year or later
	public Vector<BookObj> FindBooksByMinYear(final int iYear) {
		final Vector<BookObj> vBooksRes = new Vector<> ();
		
		vBooks.stream().forEach((book) -> {
			if(book.iYear >= iYear) {
				vBooksRes.add(book);
			}
		});
		
		return vBooksRes;
	}

	// Search for books published prior to a Year
	public Vector<BookObj> FindBooksByMaxYear(final int iYear) {
		final Vector<BookObj> vBooksRes = new Vector<> ();
		
		vBooks.stream().forEach((book) -> {
			if(book.iYear <= iYear) {
				vBooksRes.add(book);
			}
		});
		
		return vBooksRes;
	}
}
