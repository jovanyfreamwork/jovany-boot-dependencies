package cn.jovanyfreamwork.cloud.saas.core;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.jovanyfreamwork.cloud.saas.data.Scope;

@FeignClient(name = "learnyeaidemo-saas", path = "/learnyeai/demo/saas/api/scopes")
public interface ScopeFeignClient {

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<PagedResources<Scope>> findAll(Pageable pageable);

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<Resource<Scope>> save(Scope scene);

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	ResponseEntity<Resource<Scope>> findById(@PathVariable("id") String id);

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	ResponseEntity<?> delete(@PathVariable("id") String id);

}