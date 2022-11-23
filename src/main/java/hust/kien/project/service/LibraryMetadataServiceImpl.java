package hust.kien.project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import hust.kien.project.dao.AuthorRepository;
import hust.kien.project.dao.BookRepository;
import hust.kien.project.dao.ClientRepository;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.client.Client;
import hust.kien.project.service.dynamic.AuthorSpecificationBuilder;
import hust.kien.project.service.dynamic.BookSpecificationBuilder;
import hust.kien.project.service.dynamic.ClientSpecficationBuilder;

@Service
@Transactional
public class LibraryMetadataServiceImpl implements LibraryMetadataService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired 
    private AuthorRepository authorRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Book saveOrUpdateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Author saveOrUpdateAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Client saveOrUpdateClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public void deleteAuthor(Author author) {
        authorRepository.delete(author);
    }

    @Override
    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }

    @Override
    public <T> List<T> dynamicFind(Specification<T> spec) {
        throw new UnsupportedOperationException("Will implement with enough usage");
    }

    @Override
    public List<Book> findBookByExactName(String name) {
        return bookRepository.findByBookInfo_Name(name);
    }

    @Override
    public List<Author> findAuthorByNameContains(String name) {
        return authorRepository.findByAuthorInfo_NameIgnoreCaseLike("%" + name + "%");
    }

    @Override
    public List<Book> dynamicFindBook(BookSpecificationBuilder specs) {
        List<Book> books = bookRepository.findAll(specs.build());

        if(specs.isCollectionInit()){
            for (Book book : books) {
                book.getBookInfo().getAuthors().size();
                book.getBookInfo().getBookGenres().size();
            }
        }

        return books;
    }

    @Override
    public List<Author> dynamicFindAuthor(AuthorSpecificationBuilder specs) {
        List<Author> authors = authorRepository.findAll(specs.build());

        if(specs.isCollectionInit()) {
            for (Author author : authors) {
                author.getAuthorInfo().getBooks().size();
            }
        }

        return authors;
    }

    @Override
    public List<Client> dynamicFindClient(ClientSpecficationBuilder specs) {
        List<Client> clients = clientRepository.findAll(specs.build());

        if(specs.isCollectionInit()) {
            for (Client client : clients) {
                
            }
        }
        return null;
    }
    
}
