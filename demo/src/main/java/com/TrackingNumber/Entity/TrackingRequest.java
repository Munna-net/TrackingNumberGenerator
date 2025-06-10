package com.TrackingNumber.Entity;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackingRequest {

    @NotBlank(message = "Origin country ID must not be blank")
    private String origin_country_id;

    @NotBlank(message = "Destination country ID must not be blank")
    private String destination_country_id;

    @Positive(message = "Weight must be a positive number")
    private double weight;

    @NotNull(message = "Created at timestamp is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime created_at;

    @NotNull(message = "Customer ID is required")
    private UUID customer_id;

    @NotBlank(message = "Customer name must not be blank")
    private String customer_name;

    @NotBlank(message = "Customer slug must not be blank")
    private String customer_slug;
}