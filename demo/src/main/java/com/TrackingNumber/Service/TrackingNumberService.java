package com.TrackingNumber.Service;

import com.TrackingNumber.Dto.TrackingResponse;
import com.TrackingNumber.Entity.TrackingRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class TrackingNumberService {

    private final AtomicLong counter = new AtomicLong(System.currentTimeMillis());

    public TrackingResponse generateTrackingNumber(TrackingRequest request) {
        String base = request.getOrigin_country_id()
                + request.getDestination_country_id()
                + request.getCustomer_slug().toUpperCase().replace("-", "").substring(0, Math.min(4, request.getCustomer_slug().length()));

        String unique = Long.toHexString(counter.incrementAndGet()).toUpperCase();
        String trackingNumber = (base + unique).replaceAll("[^A-Z0-9]", "")
                .substring(0, Math.min(16, base.length() + unique.length()));

        log.debug("Generated tracking number: {}", trackingNumber);

        return new TrackingResponse(trackingNumber, OffsetDateTime.now());
    }
}