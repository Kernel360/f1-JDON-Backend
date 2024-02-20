package kernel.jdon.modulecommon.log.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

import jakarta.annotation.Nullable;
import kernel.jdon.modulecommon.log.LoggingForm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConnectionProxyHandler implements MethodInterceptor {
	private static final String JDBC_PREPARE_STATEMENT_METHOD_NAME = "prepareStatement";
	private final Object connection;
	private final LoggingForm loggingForm;

	@Nullable
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		final Object result = invocation.proceed();

		if (hasConnection(result) && hasPreparedStatementInvoked(invocation)) {
			final ProxyFactory proxyFactory = new ProxyFactory(result);
			proxyFactory.addAdvice(new PreparedStatementProxyHandler(loggingForm));
			return proxyFactory.getProxy();
		}
		return result;
	}

	private boolean hasConnection(final Object result) {
		return result != null;
	}

	private boolean hasPreparedStatementInvoked(final MethodInvocation invocation) {
		return invocation.getMethod().getName().equals(JDBC_PREPARE_STATEMENT_METHOD_NAME);
	}

	public Object getProxy() {
		final ProxyFactory proxyFactory = new ProxyFactory(connection);
		proxyFactory.addAdvice(this);
		return proxyFactory.getProxy();
	}

}
