package cn.jovanyfreamwork.cloud.saas.sdk;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import cn.jovanyfreamwork.cloud.saas.resource.ResourceSaaS;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableDiscoveryClient
@EnableFeignClients(basePackages="com.learnyeai.demo.sdk.saas.client")
@ResourceSaaS
public @interface SDKSaaS {

}
