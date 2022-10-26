select book.name as 'Book Name', author.name as 'Author Name'
from 
bookAuthor join book on book.id = bookAuthor.bookId join author on bookAuthor.authorId = author.id
where book.name like '%k 2%'

delete from bookAuthor 
where rowid > (
    select min(rowid) from bookAuthor bA
    where bA.bookId = bookAuthor.bookId
    and bA.authorId = bookAuthor.authorId
)