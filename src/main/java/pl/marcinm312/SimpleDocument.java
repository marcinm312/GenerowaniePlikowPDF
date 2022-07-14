package pl.marcinm312;

public class SimpleDocument {

	private final String title;
	private final String content;

	public SimpleDocument(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
}
