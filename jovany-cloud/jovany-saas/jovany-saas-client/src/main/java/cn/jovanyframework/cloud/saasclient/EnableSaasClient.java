package cn.jovanyframework.cloud.saasclient;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import cn.jovanyframework.cloud.saasclient.scene.SaasClientConfig;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@ComponentScan(basePackages = "cn.jovanyfreamwork.cloud.saasclient")
@ImportAutoConfiguration(classes = { SaasClientConfig.class })
public @interface EnableSaasClient {

}
