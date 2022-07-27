import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {
    private static Horse horse;

    @BeforeEach
    public void init() {
        horse = new Horse("Bella", 10, 100);

    }

    @Test
    public void nullNameHorses() {
        assertThrows(IllegalArgumentException.class, () ->
                new Horse(null, 10, 10));
    }

    @Test
    public void nullNameMessage() {
        try {
            new Horse(null, 10, 10);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\n"})
    public void blankNameException(String name) {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
                new Horse(name, 10, 10));
        assertEquals("Name cannot be blank.", illegalArgumentException.getMessage());

    }

    @Test
    public void SpeedExceptionHorses() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new Horse("Valet", -1, 10));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void distanceExceptionHorses() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new Horse("Valet", 10, -5));
        assertEquals("Distance cannot be negative. ", exception.getMessage());
    }

    @Test
    public void getNameTest() throws NoSuchFieldException, IllegalAccessException {
        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String nameValue = (String) name.get(horse);
        assertEquals("Bella", nameValue);
    }

    @Test
    public void getSpeedTest() {
        double result = horse.getSpeed();
        assertEquals(10, result);

    }

    @Test
    public void getDistanceTest() {
        double result = horse.getDistance();
        assertEquals(100, result);
    }

    @Test
    public void zeroDistance() {
        Horse horse1 = new Horse("Valet", 10);
        assertEquals(0, horse1.getDistance());
    }

    @Test
    public void moveTest() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horse.move();
            horseMockedStatic.verify(()->Horse.getRandomDouble(0.2, 0.9));
        }
    }
}
