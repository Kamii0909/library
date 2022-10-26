package hust.kien.project.model;

import java.time.LocalDate;

public class Book {
    private final String name;
    private final Author[] authors;
    private final LocalDate publicationDate;
    private final int pageCount;

    private Book(
        String name,
        Author[] authors,
        LocalDate publicationDate,
        int pageCount){
        this.name = name;
        this.authors = authors;
        this.publicationDate = publicationDate;
        this.pageCount = pageCount;
    }

    /**
     * Factory method
     * @return an instance
     */
    public static Book valueOf(
        String name,
        Author[] authors,
        LocalDate publicationDate,
        int pageCount){
        
        return null;
    }
    
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Book [name=" + name + "]";
    }
}
