package study.keesun.rest_api.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void createEvent() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2024,4,23,22,31,25))
                .closeEnrollmentDateTime(LocalDateTime.of(2024,4,24,22,31,50))
                .beginEventDateTime(LocalDateTime.of(2024,4,25,22,31,50))
                .endEventDateTime(LocalDateTime.of(2024,4,26,22,31,50))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역")
                .build();

        String jsonData = mapper.writeValueAsString(eventDto);

        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(jsonData))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(Matchers.not(true)))
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void createEvent_Bad_Request() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2024,4,23,22,31,25))
                .closeEnrollmentDateTime(LocalDateTime.of(2024,4,24,22,31,50))
                .beginEventDateTime(LocalDateTime.of(2024,4,25,22,31,50))
                .beginEventDateTime(LocalDateTime.of(2024,4,26,22,31,50))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역")
                .build();

        ModelMapper modelMapper = new ModelMapper();

//        String jsonData = mapper.writeValueAsString(eventDto);
        String jsonData = mapper.writeValueAsString(modelMapper.map(eventDto, Event.class));

        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(jsonData))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createEvent_Bad_Request_Empty_Input() throws Exception{
        EventDto eventDto = EventDto.builder().build();

        String jsonData = mapper.writeValueAsString(eventDto);

        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void createEvent_Bad_Request_Wrong_Input() throws Exception{
        EventDto eventDto = EventDto
                .builder()
                .name("Spring")
                .description("REST API development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2024,4,26,22,31,25))
                .closeEnrollmentDateTime(LocalDateTime.of(2024,4,25,22,31,50))
                .beginEventDateTime(LocalDateTime.of(2024,4,24,22,31,50))
                .endEventDateTime(LocalDateTime.of(2024,4,23,22,31,50))
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역")
                .build();

        String jsonData = mapper.writeValueAsString(eventDto);

        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


}
