package fr.epsi.book.dal;

public class DAOFactory {
	
	private DAOFactory() {}
	
	public static ContactDAO getContactDAO() {
		return new ContactDAO();
	}
	
	public static BookDAO getBookDAO() {
		return new BookDAO();
	}
}
