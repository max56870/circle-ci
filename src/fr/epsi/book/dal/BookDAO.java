package fr.epsi.book.dal;

import fr.epsi.book.domain.Book;
import fr.epsi.book.domain.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements IDAO<Book, String, Contact>
{

	private static final String INSERT_QUERY = "INSERT INTO book ( id, code) " + "values" + " (?,?)";
	private static final String FIND_BY_ID_QUERY = "SELECT * FROM book WHERE id=(?)";
	private static final String FIND_ALL_QUERY = "SELECT * FROM book";
	private static final String UPDATE_QUERY = "UPDATE book SET code = ? WHERE id = ? ";
	private static final String REMOVE_QUERY = "delete from book where id = ?";

	@Override
	public void create ( Book o, Contact c ) throws SQLException
	{
		Connection connection = PersistenceManager.getConnection();
		PreparedStatement st = connection.prepareStatement( INSERT_QUERY );
		st.setString( 1, o.getId() );
		st.setString( 2, o.getCode() );
		st.executeUpdate();
		st.close();
	}

	@Override
	public void create(Contact c, Book b) throws SQLException {

	}

	@Override
	public Book findById ( String id ) throws SQLException
	{
		Connection connection = PersistenceManager.getConnection();
		PreparedStatement st = connection.prepareStatement( FIND_BY_ID_QUERY );
		st.setString( 1, id );
		ResultSet rs = st.executeQuery();
		Book b = new Book();
		if ( rs.next() )
		{
			b.setId( rs.getString( 0 ) );
			b.setCode( rs.getString( 1 ) );
		}
		st.close();
		return b;

	}

	@Override
	public List<Book> findAll () throws SQLException
	{
		List<Book> listBook = new ArrayList<>();
		Connection connection = PersistenceManager.getConnection();
		PreparedStatement st = connection.prepareStatement( FIND_ALL_QUERY );
		ResultSet rs = st.executeQuery();

		while ( rs.next() )
		{
			String id = rs.getString( "id" );
			String code = rs.getString( "code" );

			listBook.add( new Book( id, code ) );
		}
		st.close();
		return listBook;
	}

	@Override
	public Book update ( Book o ) throws SQLException
	{
		Connection connection = PersistenceManager.getConnection();
		PreparedStatement st = connection.prepareStatement( UPDATE_QUERY );
		st.setString( 1, o.getCode() );
		st.setString( 2, o.getId() );
		st.executeUpdate();
		st.close();
		return null;
	}

	@Override
	public void remove ( Book b ) throws SQLException
	{
		Connection connection = PersistenceManager.getConnection();
		PreparedStatement st = connection.prepareStatement( REMOVE_QUERY );
		st.setString( 1, b.getId() );
		st.executeQuery();
		st.close();
	}
}
