package cn.jovanyfreamwork.cloud.saasclient;

public @interface Schema {

	String value() default "default";

	String name() default "default";

	String dataSource() default "default";

}