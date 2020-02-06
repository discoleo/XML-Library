package data;

import java.util.Vector;

public interface GetMapsINTF {
	public DomainMap GetDomainsMap();
	public PublisherMap GetPublisherMap();
	public AuthorsDict GetAuthorsDict();
	public Vector<BookObj> GetBooks();
}
