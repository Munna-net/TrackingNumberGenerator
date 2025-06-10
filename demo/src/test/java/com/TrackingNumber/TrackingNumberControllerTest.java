package com.TrackingNumber;

import com.TrackingNumber.Controller.TrackingNumberController;
import com.TrackingNumber.Dto.TrackingResponse;
import com.TrackingNumber.Entity.TrackingRequest;
import com.TrackingNumber.Service.TrackingNumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TrackingNumberControllerTest {

		private TrackingNumberService service;
		private TrackingNumberController controller;

		@BeforeEach
		public void setup() {
			service = mock(TrackingNumberService.class);
			controller = new TrackingNumberController(service);
		}

		@Test
		public void testValidTrackingNumberRequest() {
			OffsetDateTime now = OffsetDateTime.now();
			UUID customerId = UUID.randomUUID();
			TrackingRequest request = new TrackingRequest();
			request.setOrigin_country_id("MY");
			request.setDestination_country_id("ID");
			request.setWeight(1.234);
			request.setCreated_at(now);
			request.setCustomer_id(customerId);
			request.setCustomer_name("RedBox Logistics");
			request.setCustomer_slug("redbox-logistics");
			TrackingResponse mockResponse = new TrackingResponse("TRACK123456", now);
			when(service.generateTrackingNumber(eq(request))).thenReturn(mockResponse);
			ResponseEntity<Map<String, Object>> response = controller.getTrackingNumber(request);
			assertThat(response).isNotNull();
			assertThat(response.getStatusCodeValue()).isEqualTo(200);
			Map<String, Object> body = response.getBody();
			assertThat(body).isNotNull();
			assertThat(body.get("data")).isInstanceOf(TrackingResponse.class);
			TrackingResponse responseData = (TrackingResponse) body.get("data");
			assertThat(responseData.getTracking_number()).isEqualTo("TRACK123456");
			verify(service, times(1)).generateTrackingNumber(eq(request));
		}
	}
