package cn.jovanyfreamwork.cloud.saas.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import cn.jovanyfreamwork.cloud.saas.data.SceneID;
import cn.jovanyfreamwork.cloud.saas.data.Scene;

@Repository
@RepositoryRestResource(path = "scenes", itemResourceRel = "scene")
public interface SceneRepository extends JpaRepository<Scene, SceneID> {

}
