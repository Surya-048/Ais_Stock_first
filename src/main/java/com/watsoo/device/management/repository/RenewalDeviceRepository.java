package com.watsoo.device.management.repository;

import com.watsoo.device.management.dto.GenericRequestBody;
import com.watsoo.device.management.model.Device;
import com.watsoo.device.management.model.RenewalDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.List;

public interface RenewalDeviceRepository extends JpaRepository<RenewalDevice,Long>{

    @Query ( value = "select * from renewal_device where request_id =:reqId ", nativeQuery = true )
    Page < RenewalDevice > findAllByRequestId ( @Param ( "reqId" ) Long requestId , Pageable pageable );

    @Query ( value = "select count(*) from renewal_device where request_id = :requestId", nativeQuery = true )
    Integer deviceCountForRequest ( @Param ( "requestId" ) Long requestId );


    @Query ( value = "select * from renewal_device where request_id =:reqId ", nativeQuery = true )
    List < RenewalDevice > findAllByRequestId ( @Param ( "reqId" ) Long requestId );

    @Query ( value = "select * from renewal_device where imei_no like concat('%', :imeiNo ,'%')", nativeQuery = true )
    List < RenewalDevice > findAllByImeiNoContaining ( @Param ( "imeiNo" ) String imeiNo );

//    default List<RenewalDevice> deviceSearching(GenericRequestBody genericRequestBody) {
//        return findAll ( search ( genericRequestBody ) );
//    }
//     List < RenewalDevice > findAll ( Specification < RenewalDevice > s ) ;
//
//    static Specification < RenewalDevice > search ( GenericRequestBody genericRequestBody ){
//            if ( genericRequestBody.getImeiNo ( ).isEmpty ( ) ) {
//                return ( root , query , criteriaBuilder ) -> {
//                    Join <Object, Object> deviceRenewalRequest = root.join("deviceRenewalRequest", JoinType.INNER);
//                    return criteriaBuilder.equal ( deviceRenewalRequest.get ( "deviceRenewalRequest" ).get ( "id" ) , genericRequestBody.getRequestId ());
//                };
//            }
//            return ( root , query , criteriaBuilder ) -> criteriaBuilder.like ( root.get ( "imeiNo" ) , genericRequestBody.getImeiNo () );
//        }
    }


