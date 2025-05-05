package hust.kien.project.core.model.ticket;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, String> {

    public static final LocalDate MAX = LocalDate.of(2099, 1, 1);
    public static final LocalDate MIN = LocalDate.of(1900, 1, 1);

    @Override
    public String convertToDatabaseColumn(LocalDate attribute) {
        return DateTimeFormatter.ISO_LOCAL_DATE.format(attribute);
    }

    @Override
    public LocalDate convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;
        return LocalDate.parse(dbData);
    }

}
