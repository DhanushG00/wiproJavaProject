package com.wipro.delivery_service.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.wipro.delivery_service.dto.AssignDeliveryRequest;
import com.wipro.delivery_service.dto.OrderDTO;
import com.wipro.delivery_service.dto.UpdateLocationRequest;
import com.wipro.delivery_service.entity.Delivery;
import com.wipro.delivery_service.entity.DeliveryAgent;
import com.wipro.delivery_service.exception.AgentNotFoundException;
import com.wipro.delivery_service.exception.DeliveryNotFoundException;
import com.wipro.delivery_service.repository.DeliveryAgentRepo;
import com.wipro.delivery_service.repository.DeliveryRepo;
import com.wipro.delivery_service.service.DeliveryService;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @Mock
    private DeliveryRepo deliveryRepo;

    @Mock
    private DeliveryAgentRepo agentRepo;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DeliveryService deliveryService;

    private Delivery delivery;
    private DeliveryAgent agent;
    private AssignDeliveryRequest assignRequest;

    @BeforeEach
    void setUp() throws Exception {

        //  Inject RestTemplate manually (because new keyword used)
        Field field = DeliveryService.class.getDeclaredField("restTemplate");
        field.setAccessible(true);
        field.set(deliveryService, restTemplate);

        agent = new DeliveryAgent();
        agent.setId(10L);

        delivery = new Delivery();
        delivery.setOrderId(1L);
        delivery.setAgentId(10L);
        delivery.setStatus("ASSIGNED");

        assignRequest = new AssignDeliveryRequest();
        assignRequest.setOrderId(1L);
        assignRequest.setAgentId(10L);
    }

    //  assignDelivery - SUCCESS
    @Test
    void testAssignDelivery_Success() {
        OrderDTO order = new OrderDTO();
        order.setId(1L);

        when(restTemplate.getForObject(anyString(), eq(OrderDTO.class)))
                .thenReturn(order);

        when(agentRepo.findById(10L)).thenReturn(Optional.of(agent));
        when(deliveryRepo.save(any(Delivery.class))).thenReturn(delivery);

        Delivery result = deliveryService.assignDelivery(assignRequest);

        assertNotNull(result);
        assertEquals("ASSIGNED", result.getStatus());
    }

    //  assignDelivery - INVALID ORDER
    @Test
    void testAssignDelivery_InvalidOrder() {
        when(restTemplate.getForObject(anyString(), eq(OrderDTO.class)))
                .thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            deliveryService.assignDelivery(assignRequest);
        });
    }

    //  assignDelivery - AGENT NOT FOUND
    @Test
    void testAssignDelivery_AgentNotFound() {
        OrderDTO order = new OrderDTO();
        order.setId(1L);

        when(restTemplate.getForObject(anyString(), eq(OrderDTO.class)))
                .thenReturn(order);

        when(agentRepo.findById(10L)).thenReturn(Optional.empty());

        assertThrows(AgentNotFoundException.class, () -> {
            deliveryService.assignDelivery(assignRequest);
        });
    }

    // updateLocation - SUCCESS
    @Test
    void testUpdateLocation_Success() {
        UpdateLocationRequest req = new UpdateLocationRequest();
        req.setOrderId(1L);
        req.setLatitude(12.0);
        req.setLongitude(77.0);

        when(deliveryRepo.findByOrderId(1L)).thenReturn(Optional.of(delivery));
        when(deliveryRepo.save(any(Delivery.class))).thenReturn(delivery);

        Delivery result = deliveryService.updateLocation(req);

        assertEquals("ON_THE_WAY", result.getStatus());
        assertEquals(12.0, result.getLatitude());
    }

    //  updateLocation - NOT FOUND
    @Test
    void testUpdateLocation_NotFound() {
        when(deliveryRepo.findByOrderId(1L)).thenReturn(Optional.empty());

        UpdateLocationRequest req = new UpdateLocationRequest();
        req.setOrderId(1L);

        assertThrows(DeliveryNotFoundException.class, () -> {
            deliveryService.updateLocation(req);
        });
    }

    //  getDelivery - SUCCESS
    @Test
    void testGetDelivery_Success() {
        when(deliveryRepo.findByOrderId(1L)).thenReturn(Optional.of(delivery));

        Delivery result = deliveryService.getDelivery(1L);

        assertEquals(1L, result.getOrderId());
    }

    //  getDelivery - NOT FOUND
    @Test
    void testGetDelivery_NotFound() {
        when(deliveryRepo.findByOrderId(1L)).thenReturn(Optional.empty());

        assertThrows(DeliveryNotFoundException.class, () -> {
            deliveryService.getDelivery(1L);
        });
    }

    //  getAllDeliveries
    @Test
    void testGetAllDeliveries() {
        when(deliveryRepo.findAll()).thenReturn(List.of(delivery));

        List<Delivery> result = deliveryService.getAllDeliveries();

        assertEquals(1, result.size());
    }

    //  markDelivered - SUCCESS
    @Test
    void testMarkDelivered_Success() {
        when(deliveryRepo.findByOrderId(1L)).thenReturn(Optional.of(delivery));
        when(deliveryRepo.save(any(Delivery.class))).thenReturn(delivery);

        Delivery result = deliveryService.markDelivered(1L);

        assertEquals("DELIVERED", result.getStatus());
    }

    //  markDelivered - NOT FOUND
    @Test
    void testMarkDelivered_NotFound() {
        when(deliveryRepo.findByOrderId(1L)).thenReturn(Optional.empty());

        assertThrows(DeliveryNotFoundException.class, () -> {
            deliveryService.markDelivered(1L);
        });
    }
}