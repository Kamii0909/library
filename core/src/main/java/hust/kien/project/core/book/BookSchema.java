package hust.kien.project.core.book;

public final class BookSchema {
    private boolean authors;
    
    private boolean genres;
    
    private boolean ongoingContracts;
    
    private boolean completedContracts;
    
    public BookSchema() {
    }
    
    public BookSchema authors() {
        this.authors = true;
        return this;
    }
    
    public BookSchema genres() {
        this.genres = true;
        return this;
    }
    
    public BookSchema ongoingContracts() {
        this.ongoingContracts = true;
        return this;
    }
    
    public BookSchema completedContracts() {
        this.completedContracts = true;
        return this;
    }
    
    public boolean isAuthors() {
        return authors;
    }
    
    public boolean isGenres() {
        return genres;
    }
    
    public boolean isOngoingContracts() {
        return ongoingContracts;
    }
    
    public boolean isCompletedContracts() {
        return completedContracts;
    }
}
