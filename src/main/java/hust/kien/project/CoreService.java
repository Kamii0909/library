package hust.kien.project;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;

/**
 * The core service is responsible for initializing the "backend".
 * <p>
 * </p>
 * [kamii0909] Refactor the Gui out. Currently we are using classpath scanning,
 * which mixes both.
 */
@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Gui.class))
public class CoreService {
    
}
