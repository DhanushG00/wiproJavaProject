package com.wipro.delivery_service.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wipro.delivery_service.entity.DeliveryAgent;
import com.wipro.delivery_service.repository.DeliveryAgentRepo;
import com.wipro.delivery_service.service.DeliveryAgentService;

@ExtendWith(MockitoExtension.class)
class DeliveryAgentServiceTest {

    @Mock
    private DeliveryAgentRepo repo;

    @InjectMocks
    private DeliveryAgentService service;

    private DeliveryAgent agent;

    @BeforeEach
    void setUp() {
        agent = new DeliveryAgent();
        agent.setId(1L);
        agent.setName("Agent1");
    }

    //  getAllDeliveryAgents
    @Test
    void testGetAllDeliveryAgents() {
        when(repo.findAll()).thenReturn(List.of(agent));

        List<DeliveryAgent> result = service.getAllDeliveryAgents();

        assertEquals(1, result.size());
        assertEquals("Agent1", result.get(0).getName());
    }

    //  getDeliveryAgentById - FOUND
    @Test
    void testGetDeliveryAgentById_Found() {
        when(repo.findById(1L)).thenReturn(Optional.of(agent));

        Optional<DeliveryAgent> result = service.getDeliveryAgentById(1L);

        assertTrue(result.isPresent());
        assertEquals("Agent1", result.get().getName());
    }

    // getDeliveryAgentById - NOT FOUND
    @Test
    void testGetDeliveryAgentById_NotFound() {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        Optional<DeliveryAgent> result = service.getDeliveryAgentById(1L);

        assertFalse(result.isPresent());
    }

    //  deleteAgentById
    @Test
    void testDeleteAgentById() {
        doNothing().when(repo).deleteById(1L);

        service.deleteAgentById(1L);

        verify(repo, times(1)).deleteById(1L);
    }
}
