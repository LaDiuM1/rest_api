package study.keesun.rest_api.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EventController.class)
public class EventControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EventRepository eventRepository;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void createEvent() throws Exception {
        Event event = Event.builder()
                .id(1)
                .name("Spring")
                .description("REST APIT development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2024,4,23,22,31,25))
                .closeEnrollmentDateTime(LocalDateTime.of(2024,4,24,22,31,50))
                .beginEventDateTime(LocalDateTime.of(2024,4,25,22,31,50))
                .beginEventDateTime(LocalDateTime.of(2024,4,26,22,31,50))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역")
                .build();

        String jsonData = mapper.writeValueAsString(event);

        when(eventRepository.save(any())).thenReturn(event);

        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(jsonData))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists());
    }


}
