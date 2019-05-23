package cn.jovanyfreamwork.cloud.saas.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import cn.jovanyfreamwork.cloud.saas.data.Scope;
import cn.jovanyfreamwork.cloud.saas.data.ScopeID;

@Repository
@RepositoryRestResource(path = "scopes", itemResourceRel = "scope")
public interface ScopeRepository extends JpaRepository<Scope, ScopeID> {

}
