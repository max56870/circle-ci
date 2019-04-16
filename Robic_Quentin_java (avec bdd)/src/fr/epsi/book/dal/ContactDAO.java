package fr.epsi.book.dal;

import fr.epsi.book.domain.Book;
import fr.epsi.book.domain.Contact;
import fr.epsi.book.domain.Contact.Type;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static fr.epsi.book.domain.Contact.*;

public class ContactDAO implements IDAO<Contact, String, Book> {

	private static final String INSERT_QUERY = "INSERT INTO contact (id, name, email, phone, type, bookId) values (?,?,?,?,?,?)";
	private static final String FIND_BY_ID_QUERY = "SELECT * FROM contact WHERE id=(?)";
	private static final String FIND_ALL_QUERY = "SELECT * FROM contact";
	private static final String UPDATE_QUERY = "UPDATE contact SET name = ?, email = ?, phone = ?, type_var = ?, " +
			"type_num= ? " +
			"WHERE id = ? ";
	private static final String REMOVE_QUERY = "delete from contact where id = ?";

	@Override
	public void create(Contact c, Book b) throws SQLException {

		Connection connection = PersistenceManager.getConnection();
		PreparedStatement st = connection.prepareStatement( INSERT_QUERY, Statement.RETURN_GENERATED_KEYS );
		st.setString( 1, c.getId() );
		st.setString( 2, c.getName() );
		st.setString( 3, c.getEmail() );
		st.setString( 4, c.getPhone() );
		st.setString( 5, c.getType().getValue() );
		st.setString( 6, b.getId());
		st.executeUpdate();
		st.close();
	}

	@Override
	public void create(Book o, Contact c) throws SQLException {

	}

	@Override
	public Contact findById( String guid) throws SQLException {
		Connection connection = PersistenceManager.getConnection();
		PreparedStatement st = connection.prepareStatement( FIND_BY_ID_QUERY, Statement.RETURN_GENERATED_KEYS );
		st.setString( 1, guid );
		st.executeQuery();
		ResultSet rs = st.getGeneratedKeys();
		Contact c = new Contact();

		if ( rs.next() ) {
			c.setId( rs.getString( 0 ) );
			c.setName( rs.getString( 1 ) );
			c.setEmail( rs.getString( 2 ) );
			c.setPhone( rs.getString( 3) );
			c.setType((rs.getString( 4).equals("PRO") ? Type.PRO : Type.PERSO));
		}
		st.close();
		return c;
	}

	@Override
	public List<Contact> findAll() throws SQLException {
		List<Contact> listContact = new ArrayList<>();
		Connection connection = PersistenceManager.getConnection();
		PreparedStatement st = connection.prepareStatement( FIND_ALL_QUERY );
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			String id = rs.getString("id");
			String name = rs.getString("name");
			String email = rs.getString("email");
			String phone = rs.getString("phone");
			Type type = (rs.getString("type").equals("PRO") ? Type.PRO : Type.PERSO);
			listContact.add(new Contact(id, name, email, phone, type));
		}
		st.close();
		return listContact;
	}

	@Override
	public Contact update ( Contact o ) throws SQLException
	{
		Connection connection = PersistenceManager.getConnection();
		PreparedStatement st = connection.prepareStatement( UPDATE_QUERY );
		st.setString( 1, o.getName() );
		st.setString( 2, o.getEmail() );
		st.setString( 3, o.getPhone() );
		st.setString( 4, o.getType().getValue() );
		st.setInt( 5, o.getType().ordinal() );
		st.setString( 6, o.getId());
		st.executeUpdate();
		st.close();
		return null;
	}

	@Override
	public void remove ( Contact o ) throws SQLException
	{
		Connection connection = PersistenceManager.getConnection();
		PreparedStatement st = connection.prepareStatement( REMOVE_QUERY );
		st.setString( 1, o.getId() );
		st.executeQuery();
		st.close();
	}
}


