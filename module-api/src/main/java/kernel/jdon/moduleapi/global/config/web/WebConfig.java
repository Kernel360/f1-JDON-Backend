package kernel.jdon.moduleapi.global.config.web;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatSortCondition;
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
	}

	private static class CoffeeChatSortConverter implements Converter<String, CoffeeChatSortCondition> {
		@Override
		public CoffeeChatSortCondition convert(String source) {
			return CoffeeChatSortCondition.of(source);
		}
	}
}
