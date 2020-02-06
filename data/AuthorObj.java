package data;


public class AuthorObj {
	public Integer idAuthor = null;
	public String sName = null;
	public String sGivenName = null;
	
	public AuthorObj() {}
	public AuthorObj(final String sName) {
		this.sName = sName;
	}
	
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(sGivenName).append(' ').append(sName);
		
		return sb.toString();
	}
}
