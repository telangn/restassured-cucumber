package SupportClasses;



public class Book {

	private String id;
	private String title;
	private String author;
	private Catalog[] catalog;

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

	public Catalog[] getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog[] catalog) {
		this.catalog = catalog;
	}

}
