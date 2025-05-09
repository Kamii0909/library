package hust.kien.project.core.author;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

public final class AuthorFilter {

    private List<Specification<Author>> specifications = new ArrayList<>();

    public AuthorFilter nameContains(String text) {
        specifications.add((root, _, cb) -> cb.like(root.get(Author_.name), "%" + text + "%"));
        return this;
    }

    public AuthorFilter ageBetween(int from, int to) {
        LocalDate now = LocalDate.now();
        LocalDate fromDate = now.minusYears(from);
        LocalDate toDate = now.minusYears(to);
        specifications.add((root, _, cb) -> cb.between(root.get(Author_.dateOfBirth), fromDate, toDate));
        return this;
    }

    Specification<Author> build() {
        return Specification.allOf(specifications);
    }
}
