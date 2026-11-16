
package pl.merito.biblioteka.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/books/add")
public class BookFormServlet extends HttpServlet {

    private static final String VIEW = "/WEB-INF/views/books/form.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Map<String, String> errors = validate(req);

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        Integer added = (Integer) session.getAttribute("addedCount");
        session.setAttribute("addedCount", added == null ? 1 : added + 1);
        session.setAttribute("lastTitle", req.getParameter("title"));

        resp.sendRedirect(req.getContextPath() + "/books/added");
    }

    private Map<String, String> validate(HttpServletRequest req) {
        Map<String, String> errors = new HashMap<>();
        String title = req.getParameter("title");
        String author = req.getParameter("author");

        if (title == null || title.isBlank())
            errors.put("title", "Tytuł jest wymagany.");

        if (author == null || author.isBlank())
            errors.put("author", "Autor jest wymagany.");

        try {
            int year = Integer.parseInt(req.getParameter("year"));
            if (year < 1450 || year > Year.now().getValue())
                errors.put("year", "Nieprawidłowy rok.");
        } catch (NumberFormatException e) {
            errors.put("year", "Rok wydania musi być liczbą.");
        }

        return errors;
    }
}
