package study.keesun.rest_api.events;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
class EventTest {

    @Test
    public void builder(){
        Event event = Event.builder()
                .name("spring REST API")
                .description("REST API development with Spring")
                .build();
        assertThat(event).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("basePriceProvider")
    public void testFree(int basePrice, int maxPrice, boolean isFree) {
        // Given
        Event event = Event
                .builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isFree()).isEqualTo(isFree);
    }

    static Stream<Arguments> basePriceProvider() {
        return Stream.of(
                Arguments.of(0, 0, true),
                Arguments.of(100, 0, false),
                Arguments.of(0, 100, false),
                Arguments.of(100, 200, false)
        );
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