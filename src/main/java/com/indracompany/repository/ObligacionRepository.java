package com.indracompany.repository;

import com.indracompany.domain.Obligacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Obligacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObligacionRepository extends JpaRepository<Obligacion, Long> {

}
