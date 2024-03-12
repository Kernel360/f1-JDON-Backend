package kernel.jdon.modulecommon.log.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import jakarta.annotation.Nullable;
import kernel.jdon.modulecommon.log.LoggingForm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PreparedStatementProxyHandler implements MethodInterceptor {
    private static final List<String> JDBC_QUERY_METHOD = List.of("executeQuery", "execute", "executeUpdate");
    private final LoggingForm loggingForm;

    @Nullable
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        final Method method = invocation.getMethod();
        if (JDBC_QUERY_METHOD.contains(method.getName())) {
            final long startTime = System.currentTimeMillis();
            final Object result = invocation.proceed();
            final long endTime = System.currentTimeMillis();

            loggingForm.addQueryTime(endTime - startTime);
            loggingForm.queryCountup();

            return result;
        }

        return invocation.proceed();
    }
}
