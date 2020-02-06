package dom;

import java.io.File;
import java.util.List;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import data.BookObj;
import data.DomainMap;
import data.DomainObj;
import data.AuthorObj;
import data.AuthorsDict;
import data.GetMapsINTF;
import data.PublisherMap;
import data.PublisherObj;
import data.RatingObj;


public class Parser implements GetMapsINTF {
	
	// Authors
	protected static final String sXP_AUTHORS  = "/BibManagement/a:Authors/a:Author";
	// Publishers
	protected static final String sXP_PUBLISHERS = "/BibManagement/p:Publishers/p:Publisher";
	// Domains: TODO
	protected static final String sXP_DOMAINS = "/BibManagement/Domains/Domain";
	// Books
	protected static final String sXP_ARTICLES = "/BibManagement/Books/Book";
	
	// Dicts
	protected final DomainMap mapDomains = new DomainMap();
	protected final PublisherMap mapPublishers = new PublisherMap();
	protected final AuthorsDict mapAuthors = new AuthorsDict();

	private final Vector<BookObj> vBooks = new Vector<> ();
	
	// +++++++++++ MEMBER FUNCTIONS +++++++++++++
	
	public Vector<BookObj> GetBooks() {
		return vBooks;
	}
	public PublisherMap GetPublisherMap() {
		return mapPublishers;
	}
	public AuthorsDict GetAuthorsDict() {
		return mapAuthors;
	}
	public DomainMap GetDomainsMap() {
		return mapDomains;
	}
	
	// ++++ Parser
	
	public Document Parse(final File file) {
		final SAXReader reader = new SAXReader();
		try {
			final Document document = reader.read(file);
			
			// Authors
			this.ExtractAuthors(document);
			
			// Publishers
			this.ExtractPublishers(document);
			
			// Domains
			this.ExtractDomains(document);
			
			// Books
			this.ExtractBooks(document);
			
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected void ExtractAuthors(final Document document) {
		final List<Node> listAuthors = document.selectNodes(sXP_AUTHORS);
		System.out.println("\nExtracting Authors:");
		
		for(final Node nodeAuthor : listAuthors) {
			final Integer idAuthor = Integer.parseInt(nodeAuthor.valueOf("@idAuthor"));
			final String sName = nodeAuthor.valueOf("Name");
			final String sGName = nodeAuthor.valueOf("GivenName");
			
			final AuthorObj author = new AuthorObj();
			author.idAuthor = idAuthor; // currently not yet used
			author.sName = sName;
			author.sGivenName = sGName;
			mapAuthors.put(idAuthor, author);
			
			// System.out.println("" + idAuthor + " = " + author.toString());
		}
		System.out.println("Authors have been extracted!\n");
	}
	
	protected void ExtractPublishers(final Document document) {
		final List<Node> listPublishers = document.selectNodes(sXP_PUBLISHERS);
		for(final Node nodePublisher : listPublishers) {
			final int idPublisher = Integer.parseInt(nodePublisher.valueOf("@idPub"));
			final String sName = nodePublisher.valueOf("Name");
			final String sAddress = nodePublisher.valueOf("Address");
			
			final PublisherObj publisher = new PublisherObj();
			publisher.sName = sName;
			publisher.sAddress = sAddress;
			publisher.id = idPublisher;
			
			mapPublishers.put(publisher.id, publisher);
		}
	}
	
	protected void ExtractDomains(final Document document) {
		final List<Node> listDomains = document.selectNodes(sXP_DOMAINS);
		for(final Node nodePublisher : listDomains) {
			final int idDomain = Integer.parseInt(nodePublisher.valueOf("@idDomain"));
			final String sName = nodePublisher.valueOf("Name");
			
			final DomainObj domain = new DomainObj(sName, idDomain);
			
			mapDomains.put(domain.id, domain);
		}
	}
	
	protected void ExtractBooks(final Document document) {
		final List<Node> listBooks = document.selectNodes(sXP_ARTICLES);
		for(final Node nodeBook : listBooks) {
			final String sTitle = nodeBook.valueOf("Title");
			final int iYear = Integer.parseInt(nodeBook.valueOf("Date/Year"));
			// Domain
			final int idDomain = Integer.parseInt(nodeBook.valueOf("Domain/@idDomainRef"));
			final DomainObj domain = mapDomains.get(idDomain);
			// Publisher
			final int idPublisher = Integer.parseInt(nodeBook.valueOf("Published/Publisher/@idPubRef"));
			final PublisherObj publisher = mapPublishers.get(idPublisher);
			// Rating
			final String sRating = nodeBook.valueOf("Ratings/Rating");
			final String sRatingComment = nodeBook.valueOf("Ratings/Comment");
			final RatingObj rating = new RatingObj();
			rating.sRating = sRating;
			rating.sComment = sRatingComment;
			
			final BookObj book = new BookObj();
			vBooks.add(book);
			book.sTitle = sTitle;
			book.iYear = iYear;
			book.domain = domain;
			book.publisher = publisher;
			book.rating = rating;
			
			for(final Node nodeAuthor : nodeBook.selectNodes("Authors/Author/@idAuthorRef")) {
				final int idAuthor = Integer.parseInt(nodeAuthor.getStringValue());
				final AuthorObj author = mapAuthors.get(idAuthor);
				if(author != null) {
					book.AddAuthor(author);
				}
			}
		}
	}
}
