package cn.jovanyfreamwork.cloud.saasclient;

import java.util.function.Function;

import cn.jovanyfreamwork.cloud.saasclient.resource.SceneDetails;

public @interface Scene {

	Class<? extends Function<SceneDetails, String>> func() default None.class;

	Schema[] schema() default {};

	DataSource[] dataSource() default {};

	public class None implements Function<SceneDetails, String> {

		@Override
		public String apply(SceneDetails t) {
			throw new NullPointerException();
		}

	}

}
