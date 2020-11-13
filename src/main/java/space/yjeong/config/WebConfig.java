package space.yjeong.config;

import com.samskivert.mustache.Mustache.TemplateLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.InputStreamReader;
import java.nio.charset.Charset;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }

    private static TemplateLoader mustacheTemplateLoader() {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        String prefix = "classpath:/templates/";
        Charset charset = Charset.forName("UTF-8");
        return name -> new InputStreamReader(
                resourceLoader.getResource(prefix + name).getInputStream(), charset);
    }
}
