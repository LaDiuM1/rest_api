package study.keesun.rest_api.events;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

    @Test
    public void builder(){
        Event event = Event.builder()
                .name("spring REST API")
                .description("REST API development with Spring")
                .build();
        assertThat(event).isNotNull();
    }

//    @Test
//    public void javaBean(){
//        Event event = new Event();
//        event.setName("Event");
//        event.setDescription("Spring");
//
//        assertThat();
//
//    }
}