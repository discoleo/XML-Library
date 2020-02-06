package data;

public class DomainObj {
	public final String sDomain;
	public final Integer id;
	
	public DomainObj(final String sDomain, final Integer id) {
		this.sDomain = sDomain;
		this.id = id;
	}
	
	@Override
	public String toString() {
		return (sDomain != null) ? sDomain : "";
	}
}
