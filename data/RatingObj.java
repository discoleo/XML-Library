package data;

public class RatingObj {
	public String sRating = null;
	public String sComment = null;
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		if(sRating != null) {
			sb.append(sRating).append("\n");
		}
		if(sComment != null) {
			sb.append("Comment: ").append(sComment).append("\n");
		}
		
		return sb.toString();
	}
}
