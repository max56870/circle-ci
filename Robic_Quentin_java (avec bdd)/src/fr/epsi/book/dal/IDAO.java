package fr.epsi.book.dal;

import fr.epsi.book.domain.Book;
import fr.epsi.book.domain.Contact;

import java.sql.SQLException;
import java.util.List;

public interface IDAO<E, ID, B> {
	
	public void create( E o , B b) throws SQLException;

	void create(Contact c, Book b) throws SQLException;

	void create(Book o, Contact c) throws SQLException;

	public E findById(ID id ) throws SQLException;

	Contact findById(String guid) throws SQLException;

	public List<E> findAll() throws SQLException;
	
	public E update( E o ) throws SQLException;
	
	public void remove( E o ) throws SQLException;
}
