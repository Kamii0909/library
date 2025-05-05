package hust.kien.project.core.bookauthor;

import java.util.List;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;

import hust.kien.project.core.author.AuthorId;
import hust.kien.project.core.author.ReadonlyAuthor;
import hust.kien.project.core.book.BookId;
import hust.kien.project.core.book.ReadonlyBook;

public record BooksAuthorsImpl(
        ImmutableMap<BookId, ReadonlyBook> books,
        ImmutableMap<AuthorId, ReadonlyAuthor> authors,
        ImmutableListMultimap<BookId, AuthorId> bookAuthorsEdge,
        ImmutableListMultimap<AuthorId, BookId> authorBooksEdge) {



}
