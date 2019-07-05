package cn.jovanyframework.cloud.fileupload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableSwagger2
public @interface EnableFileUploadServer {

}
