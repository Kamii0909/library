package hust.kien.project.gui.config;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {
    
    @Bean
    public NumberFormat formatter() {
        return new DecimalFormat("###,###,###.##");
    }
}
