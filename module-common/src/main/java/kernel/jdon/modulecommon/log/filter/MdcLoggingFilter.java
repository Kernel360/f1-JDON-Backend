package kernel.jdon.modulecommon.log.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import kernel.jdon.modulecommon.log.MdcPreference;
import lombok.extern.slf4j.Slf4j;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class MdcLoggingFilter implements Filter {

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final
	FilterChain chain) throws ServletException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		setMdc(httpRequest);
		chain.doFilter(request, response);
		MDC.clear();
	}

	private void setMdc(final HttpServletRequest request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

		MDC.put(MdcPreference.REQUEST_ID.name(), UUID.randomUUID().toString());
		MDC.put(MdcPreference.REQUEST_METHOD.name(), request.getMethod());
		MDC.put(MdcPreference.REQUEST_URI.name(), request.getRequestURI());
		MDC.put(MdcPreference.REQUEST_TIME.name(), LocalDateTime.now().format(formatter));
		MDC.put(MdcPreference.REQUEST_IP.name(), request.getRemoteAddr());
	}
}
