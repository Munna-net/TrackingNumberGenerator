package com.TrackingNumber.Controller;


import com.TrackingNumber.Dto.TrackingResponse;
import com.TrackingNumber.Entity.TrackingRequest;
import com.TrackingNumber.Service.TrackingNumberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class TrackingNumberController {


    private final TrackingNumberService trackingNumberService;
    @PostMapping("/get-tracking-number")
    public ResponseEntity<Map<String, Object>> getTrackingNumber(@Valid @RequestBody TrackingRequest request) {
        if (request.getWeight() < 0) {
            throw new IllegalArgumentException("Weight must be non-negative");
        }

        log.info("Generating tracking number for customer: {}", request.getCustomer_name());
        TrackingResponse trackingResponse = trackingNumberService.generateTrackingNumber(request);
        Map<String, Object> response = new HashMap<>();
        response.put("data", trackingResponse);
        response.put("message", "Tracking number generated successfully");
        response.put("timestamp", OffsetDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}


