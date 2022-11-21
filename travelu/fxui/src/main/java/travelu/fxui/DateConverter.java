package travelu.fxui;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.util.StringConverter;

/**
 * Standardize date format from/to string.
 * <p>
 * Code from {@link DatePicker#setConverter}
 * <p>
 * See: <a href=
 * "https://shorturl.at/lnOX9
 * ">docs.oracle.com</a>
 * <p>
 * Link shortened to satisfy CheckStyle
 */
public class DateConverter extends StringConverter<LocalDate> {

    /**
     * Standard format for date.
     */
    private String format = "dd/MM/yyyy";

    /**
     * Date formatter using {@link #format}.
     */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

    /**
     * Generates string from LocalDate object.
     * <p>
     * Returns empty string if date is invalid
     */
    @Override
    public String toString(final LocalDate date) {
        try {
            if (date != null) {
                return formatter.format(date);
            }
        } catch (DateTimeException dte) {
        }
        return "";
    }

    /**
     * Generates LocalDate object from string, used for validation.
     */
    @Override
    public LocalDate fromString(final String string) {
        try {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string, formatter);
            }
        } catch (DateTimeParseException dtpe) {
        }
        return null;
    }

}
