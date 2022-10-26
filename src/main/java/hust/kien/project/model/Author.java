package hust.kien.project.model;

public class Author {
    private final String name;

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Author [name=" + name + "]";
    }
    
}
