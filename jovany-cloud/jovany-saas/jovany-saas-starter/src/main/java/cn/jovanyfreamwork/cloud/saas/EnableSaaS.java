package cn.jovanyfreamwork.cloud.saas;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import cn.jovanyfreamwork.cloud.saas.oauth2.AuthorizationServerConfig;
import cn.jovanyfreamwork.cloud.saas.oauth2.OAuth2Properties;
import cn.jovanyfreamwork.cloud.saas.oauth2.ResourceServerConfig;
import cn.jovanyfreamwork.cloud.saas.oauth2.RestMvcConfiguration;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EntityScan(basePackages = "cn.jovanyfreamwork.cloud.saas")
@ComponentScan(basePackages = "cn.jovanyfreamwork.cloud.saas")
@EnableJpaRepositories(basePackages = { "cn.jovanyfreamwork.cloud.saas" })
@ImportAutoConfiguration(classes = { 
	AuthorizationServerConfig.class, 
	ResourceServerConfig.class,
	RestMvcConfiguration.class, 
})
@EnableConfigurationProperties({ OAuth2Properties.class })
public @interface EnableSaaS {

}
