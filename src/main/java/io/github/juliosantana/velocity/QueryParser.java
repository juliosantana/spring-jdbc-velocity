package io.github.juliosantana.velocity;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class QueryParser {

	private static final String QUERY_STORE = "QUERY_STORE";

	private VelocityEngine velocityEngine;

	public QueryParser() {
		velocityEngine = new VelocityEngine();
		Properties properties = new Properties();
		properties.setProperty("resource.loader", "string");
		properties.setProperty("string.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.StringResourceLoader");
		velocityEngine.init(properties);
		velocityEngine.init();
		
	}

	public String parser(String query, SqlParameterSource sqlParameterSource) {
		String storeKey = QUERY_STORE + query.hashCode();
		StringResourceLoader.getRepository().putStringResource(storeKey, query);
		
		VelocityContext context = new VelocityContext();

		if (sqlParameterSource == null) {
			return query;
		}

		for (String param : sqlParameterSource.getParameterNames()) {
			context.put(param, sqlParameterSource.getValue(param));
		}

		StringWriter writer = new StringWriter();
		velocityEngine.mergeTemplate(storeKey, "UTF-8", context, writer);
		
		StringResourceLoader.getRepository().removeStringResource(storeKey);
		
		return writer.toString();
	}

}
