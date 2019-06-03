package cn.jovanyfreamwork.cloud.saas;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@ComponentScan(basePackages = "cn.jovanyfreamwork.cloud.saas")
@EnableJpaRepositories(basePackages = { "cn.jovanyfreamwork.cloud.saas" })
@ResourceSaaS
public @interface EnableSaaS {

}
