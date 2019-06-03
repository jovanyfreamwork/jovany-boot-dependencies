package cn.jovanyfreamwork.cloud.saas;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ComponentScan(basePackages = "cn.jovanyfreamwork.cloud.saas.data")
@EntityScan(basePackages = "cn.jovanyfreamwork.cloud.saas.data")
public @interface ResourceSaaS {

}
