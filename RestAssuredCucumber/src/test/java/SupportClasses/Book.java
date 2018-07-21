package SupportClasses;



public class Book {

	private String id;
	private String title;
	private String author;
	private Catalog[] info;

	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public String getAuthor() {

		return author;
	}

	public void setAuthor(String author) {

		this.author = author;
	}

	public Catalog[] getInfo() {
		return info;
	}

	public void setInfo(Catalog[] info) {
		this.info = info;
	}

}
