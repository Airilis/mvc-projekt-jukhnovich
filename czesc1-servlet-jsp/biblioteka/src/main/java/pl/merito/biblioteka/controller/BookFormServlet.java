
package pl.merito.biblioteka.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/books/add")
public class BookFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        renderForm(resp, new ArrayList<>(), "", "", "");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");

        String title = trim(req.getParameter("title"));
        String author = trim(req.getParameter("author"));
        String year = trim(req.getParameter("year"));

        List<String> errors = new ArrayList<>();

        if (title.isEmpty()) {
            errors.add("Tytuł jest wymagany.");
        }

        if (author.isEmpty()) {
            errors.add("Autor jest wymagany.");
        }

        try {
            int y = Integer.parseInt(year);
            int now = Year.now().getValue();

            if (y < 1450 || y > now) {
                errors.add("Rok musi mieścić się w zakresie 1450–" + now + ".");
            }
        } catch (NumberFormatException e) {
            errors.add("Rok wydania musi być liczbą.");
        }

        if (!errors.isEmpty()) {
            renderForm(resp, errors, title, author, year);
            return;
        }

        String encoded = URLEncoder.encode(title, StandardCharsets.UTF_8);
        resp.sendRedirect(req.getContextPath() + "/books/added?title=" + encoded);
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }

    private void renderForm(HttpServletResponse resp, List<String> errors,
                            String title, String author, String year)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html><html><body><h1>Dodaj książkę</h1>");

        for (String error : errors) {
            out.println("<p style='color:red'>" + error + "</p>");
        }

        out.println("<form method='post'>");
        out.println("<p>Tytuł: <input name='title' value='" + title + "'></p>");
        out.println("<p>Autor: <input name='author' value='" + author + "'></p>");
        out.println("<p>Rok wydania: <input name='year' value='" + year + "'></p>");
        out.println("<button type='submit'>Zapisz</button></form></body></html>");
    }
}
