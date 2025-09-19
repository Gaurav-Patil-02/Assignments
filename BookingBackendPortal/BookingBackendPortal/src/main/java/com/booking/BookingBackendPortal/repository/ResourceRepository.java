package com.booking.BookingBackendPortal.repository;

import com.booking.BookingBackendPortal.entity.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Page<Resource> findByActiveTrue(Pageable pageable);
}