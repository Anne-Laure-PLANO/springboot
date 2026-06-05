package com.mon_projet.demo.sensor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HeartbeatController.class)
class HeartbeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HeartbeatSensor heartbeatSensor;

    @Test
    void heartbeat_shouldReturnValue() throws Exception {
        when(heartbeatSensor.get()).thenReturn(42);

        mockMvc.perform(get("/heartbeat"))
                .andExpect(status().isOk())
                .andExpect(content().string("42"));
    }
}
