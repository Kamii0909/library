package hust.kien.project.core.bookauthor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorLink> {
}
