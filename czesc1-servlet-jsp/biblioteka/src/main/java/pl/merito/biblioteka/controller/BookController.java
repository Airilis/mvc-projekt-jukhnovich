
package pl.merito.biblioteka.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import pl.merito.biblioteka.model.Author;
import pl.merito.biblioteka.model.Book;
import pl.merito.biblioteka.model.BookValidator;
import pl.merito.biblioteka.repository.AuthorRepository;
import pl.merito.biblioteka.repository.BookRepository;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@WebServlet(urlPatterns = {"/books", "/books/details", "/books/add"})
public class BookController extends HttpServlet {

    private BookRepository books;
    private AuthorRepository authors;

    @Override
    public void init() {
        books = (BookRepository) getServletContext()
                .getAttribute("bookRepository");

        authors = (AuthorRepository) getServletContext()
                .getAttribute("authorRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        switch (req.getServletPath()) {
            case "/books" -> list(req, resp);
            case "/books/details" -> details(req, resp);
            case "/books/add" -> showForm(req, resp);
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        if ("/books/add".equals(req.getServletPath())) {
            add(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    private void list(HttpServletRequest req,
                      HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("books", books.findAll());
        forward("books/list", req, resp);
    }

    private void details(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        Optional<Book> book = parseId(req.getParameter("id"))
                .flatMap(books::findById);

        if (book.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Nie znaleziono książki");
            return;
        }

        req.setAttribute("book", book.get());
        forward("books/details", req, resp);
    }

    private void showForm(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("authors", authors.findAll());
        forward("books/form", req, resp);
    }

    private void add(HttpServletRequest req,
                     HttpServletResponse resp)
            throws ServletException, IOException {

        Map<String, String> errors = BookValidator.validate(
                req.getParameter("title"),
                req.getParameter("authorId"),
                req.getParameter("year")
        );

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            showForm(req, resp);
            return;
        }

        Author author = authors.findById(
                Long.valueOf(req.getParameter("authorId"))
        ).orElseThrow();

        books.save(new Book(
                req.getParameter("title").trim(),
                author,
                Integer.parseInt(req.getParameter("year"))
        ));

        resp.sendRedirect(req.getContextPath() + "/books");
    }

    private Optional<Long> parseId(String value) {
        try {
            return Optional.of(Long.valueOf(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private void forward(String view,
                         HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher(
                "/WEB-INF/views/" + view + ".jsp"
        ).forward(req, resp);
    }
}
