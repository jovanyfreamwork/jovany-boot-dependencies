package cn.jovanyfreamwork.cloud.saas.sdk;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.hateoas.config.EnableHypermediaSupport;

import cn.jovanyfreamwork.cloud.saas.ResourceSaaS;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableDiscoveryClient
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@ResourceSaaS
@RibbonClient(name = "learnyeaidemo-saas")
public @interface SDKSaaS {
}
