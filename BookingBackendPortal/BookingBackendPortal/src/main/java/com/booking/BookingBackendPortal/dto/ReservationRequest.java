package com.booking.BookingBackendPortal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;


public class ReservationRequest {
    
    @NotNull
    private Long resourceId;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Instant startTime;

    @NotNull
    private Instant endTime;

    public ReservationRequest() {}

	public ReservationRequest(@NotNull Long resourceId, @NotNull BigDecimal price, @NotNull Instant startTime,
			@NotNull Instant endTime) {
		super();
		this.resourceId = resourceId;
		this.price = price;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Instant getStartTime() {
		return startTime;
	}

	public void setStartTime(Instant startTime) {
		this.startTime = startTime;
	}

	public Instant getEndTime() {
		return endTime;
	}

	public void setEndTime(Instant endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "ReservationRequest [resourceId=" + resourceId + ", price=" + price + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}

	

}