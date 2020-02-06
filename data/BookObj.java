package data;

import java.util.Vector;

public class BookObj {
	public String sTitle = null;
	public final Vector<AuthorObj> vAuthors = new Vector<> ();
	public PublisherObj publisher = null;
	public DomainObj domain = null;
	public RatingObj rating = null;
	//
	public Integer idBook = null;
	
	// Date
	// TODO: full Date
	public int iYear = 0;
	
	public void AddAuthor(final AuthorObj author) {
		vAuthors.add(author);
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Title: ").append(sTitle).append('\n');
		
		// Date
		if(iYear > 0) {
			sb.append("[").append(iYear).append("]\n");
		}
		
		// Authors
		int countA = 1;
		for(final AuthorObj author : vAuthors) {
			if(countA > 1) {
				sb.append("\n");
			}
			sb.append("Author ").append(countA++).append(": ");
			if(author != null) {
				sb.append(author.toString());
			}
		}
		
		// Domain
		sb.append('\n').append("Domain: ");
		if(domain != null) {
			sb.append(domain.toString());
		}
		
		// Publisher
		sb.append('\n').append("Publisher: ");
		if(publisher != null) {
			sb.append(publisher.toString());
		}
		
		// Rating
		sb.append('\n').append("Rating: ");
		if(rating != null) {
			sb.append(rating.toString());
		}
		
		return sb.toString();
	}
}
