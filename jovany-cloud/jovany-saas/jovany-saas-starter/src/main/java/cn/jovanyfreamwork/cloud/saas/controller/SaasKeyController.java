package cn.jovanyfreamwork.cloud.saas.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import cn.jovanyfreamwork.cloud.saas.data.Scene;
import cn.jovanyfreamwork.cloud.saas.data.SceneID;
import cn.jovanyfreamwork.cloud.saas.jpa.SceneRepository;

@RestController
public class SaasKeyController {

	@Autowired
	SceneRepository sceneRepository;

	public String applySaasKey(String content) {
		Scene scene = new Scene(content);
		Optional<Scene> sceneOptional = sceneRepository.findById(new SceneID(content));
		if (sceneOptional.isPresent()) {
			scene = sceneOptional.get();
		} else {
			scene = sceneRepository.save(scene);
		}
		return null;
	}

}
