package com.watsoo.device.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.watsoo.device.management.model.CommandRequestMaster;


public interface CommandRequestMasterRepository extends JpaRepository<CommandRequestMaster, Long>, JpaSpecificationExecutor<CommandRequestMaster> {

	@Query(value="SELECT * FROM command_request_master WHERE status !='COMPLETED' OR status IS NULL ORDER BY ID ASC LIMIT 1",nativeQuery = true)
	List<CommandRequestMaster> findPendingRequest();

}
