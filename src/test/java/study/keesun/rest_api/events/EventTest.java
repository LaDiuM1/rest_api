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

    @Test
    public void testFree() {
        // Given
        Event event = Event
                .builder()
                .basePrice(0)
                .maxPrice(0)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isFree()).isTrue();

        // Given
        event = Event
                .builder()
                .basePrice(100)
                .maxPrice(0)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isFree()).isFalse();
    }
    @Test
    public void testOffline() {
        // Given
        Event event = Event
                .builder()
                .location("강남역")
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isOffline()).isFalse();

        // Given
        event = Event
                .builder()
                .location("")
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isOffline()).isTrue();
    }
}