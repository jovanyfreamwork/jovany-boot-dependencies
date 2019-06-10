package cn.jovanyfreamwork.cloud.saasclient;

public @interface DataSource {

	String value() default "default";

	String name() default "default";

}
