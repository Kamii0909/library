package hust.kien.project.model.rent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate,String> {

    @Override
    public String convertToDatabaseColumn(LocalDate attribute) {
        return DateTimeFormatter.ISO_LOCAL_DATE.format(attribute);
    }

    @Override
    public LocalDate convertToEntityAttribute(String dbData) {
        return LocalDate.parse(dbData);
    }
    
}
