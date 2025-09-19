package com.booking.BookingBackendPortal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceRequest {
    // Getters and setters
    @NotBlank
    private String name;
    private String type;
    private String description;
    private Integer capacity;

    public ResourceRequest() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public ResourceRequest(@NotBlank String name, String type, String description, Integer capacity) {
		super();
		this.name = name;
		this.type = type;
		this.description = description;
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "ResourceRequest [name=" + name + ", type=" + type + ", description=" + description + ", capacity="
				+ capacity + "]";
	}
    
    

}