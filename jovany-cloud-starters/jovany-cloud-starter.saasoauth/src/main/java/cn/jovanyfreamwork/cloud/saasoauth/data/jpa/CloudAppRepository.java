package cn.jovanyfreamwork.cloud.saasoauth.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.jovanyfreamwork.cloud.saasoauth.data.CloudUser;

@Repository
public interface CloudAppRepository extends JpaRepository<CloudUser, String> {

}
