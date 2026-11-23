
package pl.merito.biblioteka.repository;

import pl.merito.biblioteka.model.Author;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class AuthorRepository {

    private final Map<Long, Author> authors = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public List<Author> findAll() {
        return authors.values().stream()
                .sorted(Comparator.comparing(Author::getName))
                .toList();
    }

    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(authors.get(id));
    }

    public Author save(Author author) {
        if (author.getId() == null) {
            author.setId(nextId.getAndIncrement());
        }
        authors.put(author.getId(), author);
        return author;
    }
}
