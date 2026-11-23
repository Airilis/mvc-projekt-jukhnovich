
package pl.merito.biblioteka.model;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;

public final class BookValidator {

    private BookValidator() { }

    public static Map<String, String> validate(
            String title, String authorId, String year) {

        Map<String, String> errors = new HashMap<>();

        if (title == null || title.isBlank()) {
            errors.put("title", "Tytuł jest wymagany.");
        }

        if (authorId == null || authorId.isBlank()) {
            errors.put("authorId", "Wybierz autora.");
        }

        try {
            int y = Integer.parseInt(year);
            if (y < 1450 || y > Year.now().getValue()) {
                errors.put("year", "Nieprawidłowy rok wydania.");
            }
        } catch (NumberFormatException e) {
            errors.put("year", "Rok wydania musi być liczbą.");
        }

        return errors;
    }
}
