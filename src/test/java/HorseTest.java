import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

public class HorseTest {

    String nameHorse = "Spirit";
    double speedHorse = 100;
    double distanceHorse = 20;

    Horse horse = new Horse(nameHorse, speedHorse, distanceHorse);

    @Test
    void whenConstructorNameIsNullAndExceptionMessageNotEquals() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, speedHorse, distanceHorse));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void whenFirstParameterInConstructorIsEmpty(String name) {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(name, speedHorse, distanceHorse));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void whenSecondParameterInConstructorExceptionMessageNotEqualsAndNegative() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(nameHorse, -5, distanceHorse));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void whenThirdParameterInConstructorExceptionMessageNotEqualsAndNegative() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(nameHorse, speedHorse, -5));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getName() {
        assertEquals(nameHorse, horse.getName());
    }

    @Test
    void getSpeed() {
        assertEquals(speedHorse, horse.getSpeed());
    }

    @Test
    void getDistance() {
        assertEquals(distanceHorse, horse.getDistance());
        Horse horse1 = new Horse(nameHorse, speedHorse);
        assertEquals(0, horse1.getDistance());
    }

    @Test
    void move() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.5, 0.9, 1.0, 999.999, 0.0})
    void getRandomDouble(double random) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Spirit", 31, 283);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            horse.move();

            assertEquals(283 + 31 * random, horse.getDistance());
        }
    }
}
