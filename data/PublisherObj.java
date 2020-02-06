package data;

public class PublisherObj {
	public String sName = null;
	public String sAddress = null;
	public Integer id = null;
	
	@Override
	public String toString() {
		final String sOut = sName
				+ ((sAddress != null) ? "\nAddress: " + sAddress : "");
		return sOut;
	}
}
