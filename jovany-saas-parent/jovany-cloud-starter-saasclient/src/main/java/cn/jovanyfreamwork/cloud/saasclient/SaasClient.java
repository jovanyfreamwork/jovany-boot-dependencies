package cn.jovanyfreamwork.cloud.saasclient;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SaasClient {
	
	String DEFAULT = "default";

	String[] value() default { DEFAULT };

	User user() default @User;

	Scene scene() default @Scene;

	Client client() default @Client;

}
