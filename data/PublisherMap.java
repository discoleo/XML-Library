package data;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class PublisherMap extends TreeMap<Integer, PublisherObj> {

	private Integer idLAST = 0;
	
	private int id = -1;
	private PublisherObj publisher = null;
	
	private final TreeMap<PublisherObj, Integer> mapPublishers = new TreeMap<> (new ComparatorPublishers());
	private boolean isMapBuilt = false;
	
	@Override
	public PublisherObj put(final Integer id, final PublisherObj publisher) {
		if(idLAST < id) {
			idLAST = id;
		}
		return super.put(id, publisher);
	}
	
	protected void Build() {
		for(final Map.Entry<Integer, PublisherObj> entry : this.entrySet()) {
			mapPublishers.put(entry.getValue(), entry.getKey());
		}
		isMapBuilt = true;
	}
	
	// old/Sax Parser
	public void SetID(final int idPublisher) {
		id = idPublisher;
		idLAST = Math.max(idLAST, id);
		publisher = new PublisherObj();
		this.put(idPublisher, publisher);
	}
	public void PutPublisher(final String sPublisherName) {
		if(id > 0) {
			publisher.sName = sPublisherName;
		}
	}
	// Updates
	public BoolPair<Integer> GetOrSetPublisher(final PublisherObj publisher) {
		if( ! isMapBuilt) {
			this.Build();
		}
		final Integer idPublisher = mapPublishers.get(publisher);
		
		if(idPublisher == null) {
			final Integer idNew = ++idLAST;
			publisher.id = idNew;
			super.put(idNew, publisher);
			mapPublishers.put(publisher, idNew);
			return new BoolPair<> (idNew, true);
		} else {
			return new BoolPair<> (idPublisher, false);
		}
	}
	
	public static class ComparatorPublishers implements Comparator<PublisherObj> {

		@Override
		public int compare(final PublisherObj pub1, final PublisherObj pub2) {
			if(pub1 == null) {
				if(pub2 != null) { return -1; }
				return 0; // NO further comparisons
			} else if(pub2 == null) { return 1; }
			
			if(pub1.sName == null) {
				if(pub2.sName != null) { return -1; }
				return 0; // NO further comparisons
			} else if(pub2.sName == null) { return 1; }
			
			return pub1.sName.compareTo(pub2.sName);
		}
	}
}
