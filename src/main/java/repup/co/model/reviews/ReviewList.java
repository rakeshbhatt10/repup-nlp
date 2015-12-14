package repup.co.model.reviews;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ReviewList {

	private List<String> reviewsList;

	public List<String> getReviewsList() {
		return reviewsList;
	}

	public void setReviewsList(List<String> reviewsList) {
		this.reviewsList = reviewsList;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
