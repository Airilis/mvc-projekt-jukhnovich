
package pl.merito.biblioteka.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import pl.merito.biblioteka.model.Author;
import pl.merito.biblioteka.model.Book;
import pl.merito.biblioteka.repository.AuthorRepository;
import pl.merito.biblioteka.repository.BookRepository;

@WebListener
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {

        AuthorRepository authors = new AuthorRepository();
        BookRepository books = new BookRepository();

        Author lem = authors.save(new Author("Stanisław Lem"));
        Author tokarczuk = authors.save(new Author("Olga Tokarczuk"));

        books.save(new Book("Solaris", lem, 1961));
        books.save(new Book("Cyberiada", lem, 1965));
        books.save(new Book("Bieguni", tokarczuk, 2007));

        ServletContext ctx = event.getServletContext();

        ctx.setAttribute("authorRepository", authors);
        ctx.setAttribute("bookRepository", books);
    }
}
