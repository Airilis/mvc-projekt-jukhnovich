
package pl.merito.biblioteka.repository;

import pl.merito.biblioteka.model.Book;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class BookRepository {

    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public List<Book> findAll() {
        return books.values().stream()
                .sorted(Comparator.comparing(Book::getId))
                .toList();
    }

    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(nextId.getAndIncrement());
        }
        books.put(book.getId(), book);
        return book;
    }

    public void deleteById(Long id) {
        books.remove(id);
    }
}
