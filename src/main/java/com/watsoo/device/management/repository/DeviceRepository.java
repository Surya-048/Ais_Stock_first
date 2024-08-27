package com.watsoo.device.management.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

	Optional<Device> findByImeiNo(String imeiNo);
	List<Device>  findByIccidNoIn(List<String> iccidNo);
	Optional<Device> findByIccidNo(String iccidNo);

	default Page<Device> findAll(GenericRequestBody genericRequestBody, Pageable pageRequest) {
		try {
			return findAll(search(genericRequestBody), pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	default Page<Device> findAllV2(GenericRequestBody genericRequestBody, Pageable pageRequest) {
		try {
			return findAll(search2(genericRequestBody), pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	Page<Device> findAll(Specification<Device> search, Pageable pageable);

	static Specification<Device> search(GenericRequestBody entity) {
		return (root, cq, cb) -> {
			// To ensure we start with a predicate
			Predicate p = cb.conjunction();
			boolean isNotPresent = true;
			if (entity.getFromDate() != null && entity.getToDate() != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date(entity.getFromDate()));
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				Date dateFrom = cal.getTime();
				Calendar calendar = Calendar.getInstance();
				Date endDate = new Date(entity.getToDate());
				calendar.setTime(endDate);
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
				Date dateTo = calendar.getTime();
				p.getExpressions().add(cb.between(root.get("updatedAt"), dateFrom, dateTo));
				isNotPresent = false;
			}

			// if (entity.getCreatedBy() != null) {
			// p.getExpressions().add(cb.equal(root.get("createdBy"),
			// entity.getCreatedBy()));
			// isNotPresent = false;
			// }

			if ((entity.getCreatedByName() != null && !entity.getCreatedByName().isEmpty())
					|| (entity.getCreatedById() != null && entity.getCreatedById() != 0)) {
				Join<?, ?> joinUser = root.join("createdBy");
				p.getExpressions().add(cb.or(cb.like(joinUser.get("name"), "%" + entity.getCreatedByName() + "%"),
						cb.equal(joinUser.get("id"), entity.getCreatedById())));
				isNotPresent = false;
			}
			// if ((entity.getUpdatedByName() != null &&
			// !entity.getUpdatedByName().isEmpty())
			// || (entity.getUpdatedById() != null && entity.getUpdatedById() != 0)) {
			// Join<?, ?> joinUser = root.join("updatedBy");
			// p.getExpressions().add(cb.or(cb.like(joinUser.get("name"), "%" +
			// entity.getUpdatedByName() + "%"),
			// cb.equal(joinUser.get("id"), entity.getUpdatedById())));
			// isNotPresent = false;
			// }

			if (entity.getStateId() != null) {
				p.getExpressions().add(cb.equal(root.get("state").get("id"), entity.getStateId()));
				isNotPresent = false;
			}

			if (entity.getStatusMaster() != null && !entity.getStatusMaster().name().isEmpty()
					&& entity.getStatusMaster().name() != null) {
				p.getExpressions().add(cb.equal(root.get("status"), entity.getStatusMaster()));
				isNotPresent = false;
			}

			if (entity.getSearch() != null && !entity.getSearch().isEmpty()) {
				p.getExpressions()
						.add(cb.or(cb.equal(root.get("imeiNo"), entity.getSearch()),
								cb.equal(root.get("iccidNo"), entity.getSearch()),
								cb.equal(root.get("uuidNo"), entity.getSearch())));
				isNotPresent = false;
			}

			if (isNotPresent) {
				return null;
			} else {
				return p;
			}
		};
	}

	static Specification<Device> search2(GenericRequestBody entity) {
		return (root, cq, cb) -> {
			// To ensure we start with a predicate
			Predicate p = cb.conjunction();
			boolean isNotPresent = true;
			boolean isDateSelected = false;
			if (entity.getFromDate() != null && entity.getToDate() != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date(entity.getFromDate()));
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				Date dateFrom = cal.getTime();
				Calendar calendar = Calendar.getInstance();
				Date endDate = new Date(entity.getToDate());
				calendar.setTime(endDate);
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
				Date dateTo = calendar.getTime();
				if (entity.getIsConfigActive() != null) {

					if (entity.getIsConfigActive()) {
						p.getExpressions().add(cb.equal(root.get("isConfigActivated"), true));
						p.getExpressions().add(cb.between(root.get("deviceActivatedDate"), dateFrom, dateTo));
						isDateSelected = true;
					} else {
						p.getExpressions().add(cb.or(cb.equal(root.get("isConfigActivated"), false),
								cb.isNull(root.get("isConfigActivated"))));
					}

				}
				if (entity.getIsConfigDone() != null) {

					if (!isDateSelected && entity.getIsConfigDone()) {
						p.getExpressions().add(cb.between(root.get("configDoneDate"), dateFrom, dateTo));
						isDateSelected = true;
					}
					if (entity.getIsConfigDone()) {
						p.getExpressions().add(cb.equal(root.get("isConfigurationComplete"), true));
					} else {
						p.getExpressions().add(cb.or(cb.equal(root.get("isConfigurationComplete"), false),
								cb.isNull(root.get("isConfigurationComplete"))));
					}
				}
				if (entity.getIsConfigSent() != null) {

					if (!isDateSelected && entity.getIsConfigSent()) {
						p.getExpressions().add(cb.between(root.get("commandSendDate"), dateFrom, dateTo));
						isDateSelected = true;
					}
					if (entity.getIsConfigSent()) {
						p.getExpressions().add(cb.equal(root.get("isCommandSend"), true));
					} else {
						p.getExpressions().add(cb.or(cb.equal(root.get("isCommandSend"), false),
								cb.isNull(root.get("isCommandSend"))));
					}

				}

				if (!isDateSelected) {
					p.getExpressions().add(cb.between(root.get("updatedAt"), dateFrom, dateTo));
				}
				isNotPresent = false;
			}

			// if (entity.getCreatedBy() != null) {
			// p.getExpressions().add(cb.equal(root.get("createdBy"),
			// entity.getCreatedBy()));
			// isNotPresent = false;
			// }

			if ((entity.getCreatedByName() != null && !entity.getCreatedByName().isEmpty())
					|| (entity.getCreatedById() != null && entity.getCreatedById() != 0)) {
				Join<?, ?> joinUser = root.join("createdBy");
				p.getExpressions().add(cb.or(cb.like(joinUser.get("name"), "%" + entity.getCreatedByName() + "%"),
						cb.equal(joinUser.get("id"), entity.getCreatedById())));
				isNotPresent = false;
			}
			// if ((entity.getUpdatedByName() != null &&
			// !entity.getUpdatedByName().isEmpty())
			// || (entity.getUpdatedById() != null && entity.getUpdatedById() != 0)) {
			// Join<?, ?> joinUser = root.join("updatedBy");
			// p.getExpressions().add(cb.or(cb.like(joinUser.get("name"), "%" +
			// entity.getUpdatedByName() + "%"),
			// cb.equal(joinUser.get("id"), entity.getUpdatedById())));
			// isNotPresent = false;
			// }

			if (entity.getStateId() != null) {
				p.getExpressions().add(cb.equal(root.get("state").get("id"), entity.getStateId()));
				isNotPresent = false;
			}

			if (entity.getStatusMaster() != null && !entity.getStatusMaster().name().isEmpty()
					&& entity.getStatusMaster().name() != null) {
				p.getExpressions().add(cb.equal(root.get("status"), entity.getStatusMaster()));
				isNotPresent = false;
			}
			if (entity.getImeiNumber() != null && !entity.getImeiNumber().isEmpty()) {
				p.getExpressions().add(cb.equal(root.get("imeiNo"), entity.getImeiNumber()));
				isNotPresent = false;
			}
			if (entity.getClientIds() != null && !entity.getClientIds().isEmpty()) {
				p.getExpressions().add(cb.in(root.get("clientId")).value(entity.getClientIds()));

				isNotPresent = false;
			}
			if (entity.getSoftwareVersion() != null && !entity.getSoftwareVersion().isEmpty()) {
				p.getExpressions().add(cb.equal(root.get("softwareVersion"), entity.getSoftwareVersion()));

				isNotPresent = false;
			}

			if (entity.getSearch() != null && !entity.getSearch().isEmpty()) {
				p.getExpressions()
						.add(cb.or(cb.equal(root.get("imeiNo"), entity.getSearch()),
								cb.equal(root.get("iccidNo"), entity.getSearch()),
								cb.equal(root.get("uuidNo"), entity.getSearch())));
				isNotPresent = false;
			}

			if (isNotPresent) {
				return null;
			} else {
				return p;
			}
		};
	}



	@Query(value = "select * from device where DATE(created_at)=DATE(:today)", nativeQuery = true)
	List<Device> findByCreatedAt(@Param("today") String today);

	Page<Device> findAllByStateId(Long stateId, Pageable pageRequest);

	@Query(value = "select * from box_device where box_id=:boxId", nativeQuery = true)
	List<Device> findByBoxId(Long boxId);

	@Query(value = "SELECT bd.box_id FROM device b INNER JOIN box_device bd ON b.id=bd.box_id WHERE b.id=?1", nativeQuery = true)
	List<Long> getBoxIds(Long id);

	@Query(value = "SELECT d.* FROM device d INNER JOIN box_device bd ON d.box_id = bd.box_id WHERE bd.box_id IN :boxIds", nativeQuery = true)
	List<Device> getByBoxId(@Param("boxIds") List<Long> boxIds);

	@Query(value = "select * from device bd where bd.id IN(:deviceSet)", nativeQuery = true)
	List<Device> findBySetOfIds(Set<Long> deviceSet);

	List<Device> findAllByImeiNoIn(List<String> imeiNos);

	@Query(value = "select * from device where issue_date IS NOT NULL AND issue_date <=:today AND ( is_configuration_complete IS NULL OR is_configuration_complete != 1)", nativeQuery = true)
	List<Device> findNotConfigDevices(@Param("today") String today);

	// @Query(value = "SELECT * FROM device d where d.imei_no IN
	// (:imeiNumberList)",nativeQuery = true)
	List<Device> findAllByImeiNoInAndState_idNotNullAndClientIdNotNull(List<String> imeiNumberList);

	@Query(value = "SELECT * FROM device d where d.id IN (:deviceIds)", nativeQuery = true)
	List<Device> findAllByDeviceIds(List<Long> deviceIds);

	@Query(value = "select * from device u where u.imei_no like %:imei% AND u.status = 'ISSUED_DEVICES' ", nativeQuery = true)
	List<Device> findByLikeImeiNo(@Param("imei") String imei);

	@Query(value = "SELECT * FROM device d where d.imei_no IN (:imeiNumberList)", nativeQuery = true)
	List<Device> findAllByImeiNo(List<String> imeiNumberList);

	@Query(value = "SELECT * FROM device d where d.is_created_from_excel=1", nativeQuery = true)
	List<Device> findAllDeviceCreatedFromExcel();

	// @Query(value = "select * from device where issue_date IS NOT NULL AND
	// issue_date <=:today AND config_done_date IS NOT NULL AND (
	// is_configuration_complete IS NULL OR is_configuration_complete != 1)",
	// nativeQuery = true)
	// List<Device> findAllNotConfigDevices(String date);

	@Query(value = "select * from device d where d.imei_no like %:search% OR d.iccid_no like %:search% OR d.uuid_no like %:search%", nativeQuery = true)
	List<Device> findByLikeImeiNoOrIccidNoOrUuidNo(@Param("search") String search);

	@Query(value = "SELECT imei_no FROM device", nativeQuery = true)
	List<String> findAllImeiNo();
	
	@Query(value="SELECT * FROM device where iccid_no LIKE %:iccidNo% LIMIT 1",nativeQuery = true)
	Optional<Device> findByLikeIccidNo(String iccidNo);
	
}
