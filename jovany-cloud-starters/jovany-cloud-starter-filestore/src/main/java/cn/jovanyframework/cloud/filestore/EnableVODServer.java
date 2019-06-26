package cn.jovanyframework.cloud.filestore;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import cn.jovanyframework.cloud.filestore.core.CorsConfig;
import cn.jovanyframework.cloud.filestore.core.FileStoreConfig;
import cn.jovanyframework.cloud.filestore.core.FileStoreController;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ComponentScan(basePackages = { "cn.jovanyframework.cloud.filestore" })
@Import({ CorsConfig.class, FileStoreConfig.class, FileStoreController.class })
public @interface EnableVODServer {

}
