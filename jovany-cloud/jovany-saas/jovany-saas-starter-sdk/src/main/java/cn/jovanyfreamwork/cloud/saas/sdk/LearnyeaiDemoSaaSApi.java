package cn.jovanyfreamwork.cloud.saas.sdk;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import cn.jovanyfreamwork.cloud.saas.data.Scene;
import cn.jovanyfreamwork.cloud.saas.data.SceneID;

@FeignClient(name = "learnyeai-demo-saas", path = "/learnyeai/demo/saas")
public interface LearnyeaiDemoSaaSApi
		extends PagingAndSortingRepository<Scene, SceneID>, QueryByExampleExecutor<Scene> {

	@Override
	default <S extends Scene> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default <S extends Scene> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default Optional<Scene> findById(SceneID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default boolean existsById(SceneID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	default Iterable<Scene> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default Iterable<Scene> findAllById(Iterable<SceneID> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	default void deleteById(SceneID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default void delete(Scene entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default void deleteAll(Iterable<? extends Scene> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	default <S extends Scene> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default <S extends Scene> Iterable<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default <S extends Scene> Iterable<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default <S extends Scene> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default <S extends Scene> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	default <S extends Scene> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	default Iterable<Scene> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default Page<Scene> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}