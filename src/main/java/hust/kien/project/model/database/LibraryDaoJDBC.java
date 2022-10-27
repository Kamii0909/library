package hust.kien.project.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import hust.kien.project.model.Book;


/**
 * Implementation class for book info warehousing.
 */
public final class LibraryDaoJDBC {
    /**
     * The SQLite database backing up.
     */
    private Connection connection;
    /**
     * SQL statement object.
     */
    private Statement statement;
    /**
     * Singleton instance.
     */
    private static LibraryDaoJDBC libDao = new LibraryDaoJDBC();

    private String addBookString = 
        "insert into book (name) "
        + "values (?)";
    private PreparedStatement addBookStatement;

    private String findBookString = 
        "select book.name, author.name from"
        + "bookAuthor join book on bookAuthor.bookId = book.id"
        + "join author on bookAuthor.authorId = author.id"
        + "where book.name like ?";
    private PreparedStatement findBookStatement;

    private LibraryDaoJDBC() {
        try {
            this.connection = DriverManager
                .getConnection("jdbc:sqlite:database/library.db");
            statement = connection.createStatement();
            
            String bookTable = 
                "create table if not exists book (" 
                + "id integer primary key autoincrement," 
                + "name string not null)";
            
            String authorTable = 
                "create table if not exists author ("
                + "id integer primary key autoincrement,"
                + "name string not null)";

            String bookAuthorTable = 
                "create table if not exists bookAuthor ("
                + "bookId integer,"
                + "authorId integer,"
                + "foreign key(bookID) references book(id),"
                + "foreign key(authorID) references author(id))";

            statement.execute(bookTable);
            statement.execute(authorTable);
            statement.execute(bookAuthorTable);

            addBookStatement = connection.prepareStatement(addBookString);
            findBookStatement = connection.prepareStatement(findBookString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Factory method.
     * @return an instance
     */
    public static LibraryDaoJDBC getInstance() {
        return libDao;
    }

    /*
    public Collection<Book> getAll() throws SQLException {
        ResultSet getAllResultSet = statement.executeQuery("select * from book");
        LinkedList<Book> bookList = new LinkedList<>();

        while(getAllResultSet.next()){
            bookList.add(new Book(getAllResultSet.getString(2)));
        }
        return bookList;
    }
    */

    public void addBook(Book book) throws SQLException {
        addBookStatement.setString(1, book.toString());
        addBookStatement.executeUpdate();
    }

    public Collection<Book> findBook(String name) throws SQLException {
        findBookStatement.setString(1, "%" + name + "%");
        ResultSet findBookResultSet = findBookStatement.executeQuery();
        LinkedList<Book> bookList = new LinkedList<>();

        while(findBookResultSet.next()){
            
        }

        return bookList;
    }
}
