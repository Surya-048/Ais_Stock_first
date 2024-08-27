package com.watsoo.device.management.repository;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.model.DeviceRenewalRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.Optional;

@Repository
public interface DeviceRenewalRequestRepository extends JpaRepository<DeviceRenewalRequest,Long> {

    @Query(value = "SELECT COUNT(*) FROM device_renewal_request", nativeQuery = true)
    int countTotalItems();



//    List<DeviceRenewalRequest> getRenewalDevices(Pageable pageable);

    //@Query(value = "select * from device_renewal_request",nativeQuery = true)
  //     Page<DeviceRenewalRequest> getAllRenewalDevices(Pageable pageable, @Param("search")String search, @Param("fromDate")Date fromDate,@Param("toDate")Date toDate);

    @Query(value = "SELECT * FROM device_renewal_request  INNER JOIN renewal_device  ON device_renewal_request.id = renewal_device.request_id",nativeQuery = true)
       Page<DeviceRenewalRequest> getAllRenewalDevices(Pageable pageable);


    @Query(value = "select * from device_renewal_request order by created_at desc",nativeQuery = true)
    Page<DeviceRenewalRequest> findAll(Pageable pageable);

    @Query(value="select * from device_renewal_request req where req_code like :reqCode",nativeQuery = true)
    Page<DeviceRenewalRequest> findByReqCode(@Param("reqCode") String reqCode, Pageable pageable);


    @Query(value="select * from device_renewal_request req where req_code = :reqCode",nativeQuery = true)
    Optional<DeviceRenewalRequest> findByReqCode(String reqCode);

    @Query(value = "select * from device_renewal_request where  created_at >=  :fromDate and  created_at <= :toDate",nativeQuery = true)
    Page<DeviceRenewalRequest> findAllCreatedAtBetween(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable pageable);

    default Page<DeviceRenewalRequest> findAllForSearching(GenericRequestBody genericRequestBody, Pageable pageable) {
        return findAll(searchingSpecification(genericRequestBody), pageable);
    }

    Page<DeviceRenewalRequest> findAll(Specification<DeviceRenewalRequest> specification, Pageable pageable);
    //specification for searching
    static Specification <DeviceRenewalRequest> searchingSpecification( GenericRequestBody genericRequestBody) {
        Specification<DeviceRenewalRequest> deviceRenewalRequestSpecification =  new Specification<DeviceRenewalRequest>() {
            @Override
            public Predicate toPredicate( Root <DeviceRenewalRequest> root, CriteriaQuery <?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Predicate predicate = criteriaBuilder.conjunction();

                if (genericRequestBody.getSearch() != null && !genericRequestBody.getSearch().isEmpty() && !genericRequestBody.getSearch().equals("")) {

                    predicate
                            .getExpressions()
                            .add(
                                    criteriaBuilder.like(root.get("reqCode"),genericRequestBody.getSearch())
                            );

                }else if(genericRequestBody.getFromDate() == 0 && genericRequestBody.getToDate() == 0
                        && genericRequestBody.getSearch().equals("") && genericRequestBody.getSearch().isEmpty()){

                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));

                }else if ((genericRequestBody.getFromDate() != null && genericRequestBody.getToDate() != null) &&
                        genericRequestBody.getPageSize() != 0 ) {

                    predicate
                            .getExpressions()
                            .add(
                                    criteriaBuilder.and(
                                            criteriaBuilder.greaterThan(root.get("createdAt"), genericRequestBody.getFromDateToDateType()),
                                            criteriaBuilder.lessThan(root.get("createdAt"), genericRequestBody.getToDateToDateType())
                                    )
                            );
                }

                return predicate;
            }
        };
        return deviceRenewalRequestSpecification;
    }
}
