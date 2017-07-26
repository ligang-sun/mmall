package com.mmall.interceptor;

import java.util.Properties;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAccessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Intercepts({
		@org.apache.ibatis.plugin.Signature(type = org.apache.ibatis.executor.Executor.class, method = "update", args = {
				MappedStatement.class, Object.class }),
		@org.apache.ibatis.plugin.Signature(type = org.apache.ibatis.executor.Executor.class, method = "query", args = {
				MappedStatement.class, Object.class, org.apache.ibatis.session.RowBounds.class,
				org.apache.ibatis.session.ResultHandler.class }) })
public class ZipkinInterceptor implements Interceptor {
	private static Logger logger = LoggerFactory.getLogger(ZipkinInterceptor.class);
	private SpanAccessor spanAccessor;

	public ZipkinInterceptor(SpanAccessor spanAccessor) {
		this.spanAccessor = spanAccessor;
	}

	public Object intercept(Invocation invocation) throws Throwable {
		Object returnValue = null;
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		String sqlId = mappedStatement.getId();
		long start = System.currentTimeMillis();
		returnValue = invocation.proceed();
		long end = System.currentTimeMillis();
		long time = end - start;
		if ((this.spanAccessor == null) || (this.spanAccessor.getCurrentSpan() == null)) {
			return returnValue;
		}
		try {
			BoundSql boundSql = mappedStatement.getBoundSql(invocation.getArgs()[1]);
			String sql = boundSql.getSql();
			Span span = this.spanAccessor.getCurrentSpan();
			StringBuilder builder = new StringBuilder(sql).append("  :").append(time).append("ms");
			span.tag(sqlId, builder.toString());
		} catch (Exception e) {
			logger.warn("ZipkinInterceptor error: {}", e);
		}
		return returnValue;
	}

	public Object plugin(Object object) {
		return Plugin.wrap(object, this);
	}

	public void setProperties(Properties properties) {
	}

}

@Configuration
class ZipkinInterceptorBean{
	@Bean
	public String zipkinInterceptor(SqlSessionFactory sqlSessionFactory, SpanAccessor spanAccessor) {
		ZipkinInterceptor zipkinInterceptor = new ZipkinInterceptor(spanAccessor);
		sqlSessionFactory.getConfiguration().addInterceptor(zipkinInterceptor);
		return "";
	}
}
