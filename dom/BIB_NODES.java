package dom;

public enum BIB_NODES {
	ROOT("BibManagement", false, null),
	
	DOMAINS_LIST("Domains", false, ROOT),
		DOMAIN("Domain", false, DOMAINS_LIST),
		DOMAIN_NAME("Name", true, DOMAIN),

	AUTHORS_LIST("a:Authors", false, ROOT),
		AUTHOR("a:Author", false, AUTHORS_LIST),
		AUTHOR_NAME("Name", true, AUTHOR),
		AUTHOR_GNAME("GivenName", true, AUTHOR),

	PUBLISHERS_LIST("p:Publishers", false, ROOT),
		PUBLISHER("p:Publisher", false, PUBLISHERS_LIST),
		PUBLISHER_NAME("Name", true, PUBLISHER),
		PUBLISHER_ADDRESS("Address", true, PUBLISHER),

	BOOKS_LIST("Books", false, ROOT),
		BOOK("Book", false, BOOKS_LIST),
		BOOK_AUTHORS_LIST("Authors", false, BOOK),
		BOOK_AUTHOR("Author", false, BOOK_AUTHORS_LIST),
		BOOK_TITLE("Title", true, BOOK),
		BOOK_DOMAIN("Domain", true, BOOK),
		BOOK_RATINGS("Ratings", true, BOOK),
		BOOK_PUBLISHER_BASE("Published", false, BOOK),
		BOOK_PUBLISHER("Publisher", false, BOOK_PUBLISHER_BASE),
		BOOK_PUBLISHER_LocationID("LocationID", false, BOOK_PUBLISHER_BASE),
		DATE("Date", false, BOOK),
		DATE_YEAR("Year", true, DATE),
		RATINGS("Ratings", true, BOOK),
		RATINGS_RATING("Ratings", true, RATINGS),
		RATINGS_COMMENT("Ratings", true, RATINGS),
	
	;

	// ++++++++++++++++++
	private final BIB_NODES parent;
	private final String sNode;
	private final boolean doRead;
	// ++++++++++++++++++
	private BIB_NODES(final String node, final BIB_NODES parent) {
		this.parent = parent;
		this.sNode  = node;
		this.doRead = true;
	}
	private BIB_NODES(final String node, final boolean readElement, final BIB_NODES parent) {
		this.parent = (parent == null) ? this : parent;
		this.sNode  = node;
		this.doRead = readElement;
	}
	// ++++++++++++++++++
	public static BIB_NODES GetNode(final String node, final BIB_NODES parent) {
		for(final BIB_NODES nodeE : BIB_NODES.values()) {
			if( ! parent.equals(nodeE.parent)) {
				continue;
			}
			if(node.equals(nodeE.sNode)) {
				return nodeE;
			}
		}
		return null;
	}
	
	public String GetNode() {
		return sNode;
	}
	public BIB_NODES GetParent() {
		return parent;
	}

	public boolean ReadElement() {
		return doRead;
	}
	public boolean IsNode(final String str) {
		return this.sNode.equalsIgnoreCase(str);
	}
}
