package com.wipro.admin_service.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.wipro.admin_service.dto.DeliveryDTO;
import com.wipro.admin_service.dto.MenuDTO;
import com.wipro.admin_service.dto.OrderItemDTO;
import com.wipro.admin_service.dto.OrderResponse;
import com.wipro.admin_service.dto.UsersDTO;
import com.wipro.admin_service.exception.ExternalServiceException;
import com.wipro.admin_service.service.AdminService;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AdminService adminService;

    private OrderResponse order;
    private DeliveryDTO delivery;

    @BeforeEach
    void setUp() {
        //  Menu
        MenuDTO menu = new MenuDTO();
        menu.setId(1L);
        menu.setName("Pizza");

        OrderItemDTO item = new OrderItemDTO();
        item.setMenu(menu);
        item.setQuantity(2);

        // Order
        order = new OrderResponse();
        order.setId(1L);
        order.setUserId(100L);
        order.setTotalPrice(400);
        order.setItems(List.of(item));
        //  Delivery
        delivery = new DeliveryDTO();
        delivery.setAgentId(10L);
        delivery.setStatus("DELIVERED");
    }

    // getOrders - SUCCESS
    @Test
    void testGetOrders_Success() {
        when(restTemplate.getForObject(anyString(), eq(OrderResponse[].class)))
            .thenReturn(new OrderResponse[]{order});

        List<OrderResponse> result = adminService.getOrders();

        assertEquals(1, result.size());
        assertEquals(400, result.get(0).getTotalPrice());
    }

    // getOrders - EXCEPTION
    @Test
    void testGetOrders_Exception() {
        when(restTemplate.getForObject(anyString(), eq(OrderResponse[].class)))
            .thenThrow(new RuntimeException());

        assertThrows(ExternalServiceException.class, () -> {
            adminService.getOrders();
        });
    }

    //  getAllUsers
    @Test
    void testGetAllUsers() {
        UsersDTO user = new UsersDTO();
        user.setUsername("test");

        when(restTemplate.getForObject(anyString(), eq(UsersDTO[].class)))
            .thenReturn(new UsersDTO[]{user});

        List<UsersDTO> result = adminService.getAllUsers();

        assertEquals(1, result.size());
    }

    //  getDeliveries
    @Test
    void testGetDeliveries() {
        when(restTemplate.getForObject(anyString(), eq(DeliveryDTO[].class)))
            .thenReturn(new DeliveryDTO[]{delivery});

        List<DeliveryDTO> result = adminService.getDeliveries();

        assertEquals(1, result.size());
        assertEquals("DELIVERED", result.get(0).getStatus());
    }

    //  getTotalRevenue
    @Test
    void testGetTotalRevenue() {
        when(restTemplate.getForObject(anyString(), eq(OrderResponse[].class)))
            .thenReturn(new OrderResponse[]{order});

        double revenue = adminService.getTotalRevenue();

        assertEquals(400, revenue);
    }

    //  getTopSellingPizzas
    @Test
    void testGetTopSellingPizzas() {
        when(restTemplate.getForObject(anyString(), eq(OrderResponse[].class)))
            .thenReturn(new OrderResponse[]{order});

        Map<String, Integer> result = adminService.getTopSellingPizzas();

        assertEquals(2, result.get("Pizza"));
    }

    //  getDeliveryPerformance
    @Test
    void testGetDeliveryPerformance() {
        when(restTemplate.getForObject(anyString(), eq(DeliveryDTO[].class)))
            .thenReturn(new DeliveryDTO[]{delivery});

        Map<Long, Integer> result = adminService.getDeliveryPerformance();

        assertEquals(1, result.get(10L));
    }

    // getCustomerTrends
    @Test
    void testGetCustomerTrends() {
        when(restTemplate.getForObject(anyString(), eq(OrderResponse[].class)))
            .thenReturn(new OrderResponse[]{order});

        Map<String, Integer> result = adminService.getCustomerTrends();

        assertEquals(1, result.size());
    }
}