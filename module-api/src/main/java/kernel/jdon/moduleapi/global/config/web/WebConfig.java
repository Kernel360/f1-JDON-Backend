package kernel.jdon.moduleapi.global.config.web;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatSortType;
import kernel.jdon.moduleapi.domain.jd.core.JdSearchType;
import kernel.jdon.moduleapi.domain.jd.core.JdSortType;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

        registry.addConverter(new CoffeeChatSortConverter());
        registry.addConverter(new JdSearchTypeConverter());
        registry.addConverter(new JdSortTypeConverter());
    }

    private static class CoffeeChatSortConverter implements Converter<String, CoffeeChatSortType> {
        @Override
        public CoffeeChatSortType convert(String source) {
            return CoffeeChatSortType.of(source);
        }
    }

    private static class JdSearchTypeConverter implements Converter<String, JdSearchType> {
        @Override
        public JdSearchType convert(String source) {
            return JdSearchType.of(source);
        }
    }

    private static class JdSortTypeConverter implements Converter<String, JdSortType> {
        @Override
        public JdSortType convert(String source) {
            return JdSortType.of(source);
        }
    }
}
