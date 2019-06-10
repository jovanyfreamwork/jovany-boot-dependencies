package cn.jovanyfreamwork.cloud.saasclient;

import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

public @interface User {

	String[] value() default {};

	Class<? extends Function<UserDetails, String>> func() default None.class;

	public class None implements Function<UserDetails, String> {

		@Override
		public String apply(UserDetails t) {
			throw new NullPointerException();
		}

	}

}
