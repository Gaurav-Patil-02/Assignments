package com.booking.BookingBackendPortal.repository;

import com.booking.BookingBackendPortal.entity.Reservation;
import com.booking.BookingBackendPortal.entity.ReservationStatus;
import com.booking.BookingBackendPortal.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findByUser(User user, Pageable pageable);

    @Query("SELECT r FROM Reservation r WHERE " +
            "(:status IS NULL OR r.status = :status) AND " +
            "(:minPrice IS NULL OR r.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR r.price <= :maxPrice)")
    Page<Reservation> findByFilters(
            @Param("status") ReservationStatus status,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable);

    @Query("SELECT r FROM Reservation r WHERE r.user = :user AND " +
            "(:status IS NULL OR r.status = :status) AND " +
            "(:minPrice IS NULL OR r.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR r.price <= :maxPrice)")
    Page<Reservation> findByUserAndFilters(
            @Param("user") User user,
            @Param("status") ReservationStatus status,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable);

    List<Reservation> findByResourceIdAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
            Long resourceId, ReservationStatus status, Instant endTime, Instant startTime);
}