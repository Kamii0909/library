package hust.kien.project.core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.hibernate.HibernateException;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractClassJavaType;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcTypeIndicators;

public class LocalDateAsStringType extends AbstractClassJavaType<LocalDate> {

    private final DateTimeFormatter format;

    public LocalDateAsStringType() {
        super(LocalDate.class, ImmutableMutabilityPlan.instance());
        this.format = DateTimeFormatter.ISO_LOCAL_DATE;
    }

    @Override
    public JdbcType getRecommendedJdbcType(JdbcTypeIndicators indicators) {
        return indicators.getTypeConfiguration().getJdbcTypeRegistry().getDescriptor(SqlTypes.VARCHAR);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <X> X unwrap(LocalDate value, Class<X> type, WrapperOptions options) {
        if (String.class.equals(type)) {
            return (X) format.format(value);
        }
        return (X) value;
    }

    @Override
    public <X> LocalDate wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }

        if (value instanceof String s) {
            return format.parse(s, LocalDate::from);
        }

        return (LocalDate) value;
    }

}
