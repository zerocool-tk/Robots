package za.co.wethinkcode.WorldUnitTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import za.co.wethinkcode.Server.World.Obstacle;

public class ObstacleUnitTest {
    private final Obstacle obstacle = new Obstacle(5,10,1);

    @Test
    void testGetBottomLeftX(){
        assertEquals(5, obstacle.getBottomLeftX());
    }

    @Test
    void testGetBottomLeftY(){
        assertEquals(10, obstacle.getBottomLeftY());
    }

    @Test
    void testGetSize(){
        assertEquals(1,obstacle.getSize());
    }

    @Test
    void testBlocksPositionTrue(){
        assertTrue(obstacle.blocksPosition(5,10));
    }

    @Test
    void testBlocksPositionFalse(){
        assertFalse(obstacle.blocksPosition(4, 10));
        assertFalse(obstacle.blocksPosition(5, 9));

        assertFalse(obstacle.blocksPosition(9, 15));
        assertFalse(obstacle.blocksPosition(10, 14));

        assertFalse(obstacle.blocksPosition(4, 14));
        assertFalse(obstacle.blocksPosition(5, 15));

        assertFalse(obstacle.blocksPosition(10, 10));
        assertFalse(obstacle.blocksPosition(9, 9));
    }
}
