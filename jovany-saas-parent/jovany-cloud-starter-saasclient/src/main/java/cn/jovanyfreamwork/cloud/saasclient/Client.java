package cn.jovanyfreamwork.cloud.saasclient;

import java.util.function.Function;

import org.springframework.security.oauth2.provider.ClientDetails;

public @interface Client {

	String[] value() default {};

	Class<? extends Function<ClientDetails, String>> func() default None.class;

	public class None implements Function<ClientDetails, String> {

		@Override
		public String apply(ClientDetails t) {
			throw new NullPointerException();
		}

	}

	Schema[] schema() default {};

	DataSource[] dataSource() default {};

}
