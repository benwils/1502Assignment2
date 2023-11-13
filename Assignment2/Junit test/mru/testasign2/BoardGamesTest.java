package mru.testasign2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import mru.model.BoardGames;

class BoardGamesTest {

    @Test
    void testConstructorAndGetters() {
        BoardGames boardGame = new BoardGames(123, "Chess", "ABC Games", 19.99, 50, 8, 2, 4, "Game Designers Inc.");

        assertEquals(123, boardGame.getSN());
        assertEquals("Chess", boardGame.getName());
        assertEquals("ABC Games", boardGame.getBrand());
        assertEquals(19.99, boardGame.getPrice(), 0.001);
        assertEquals(50, boardGame.getAvailableCount());
        assertEquals(8, boardGame.getAgeAppropriate());
        assertEquals(2, boardGame.getMinPlayers());
        assertEquals(4, boardGame.getMaxPlayers());
        assertEquals("Game Designers Inc.", boardGame.getDesigners());
    }

    @Test
    void testSetters() {
        BoardGames boardGame = new BoardGames(123, "Chess", "ABC Games", 19.99, 50, 8, 2, 4, "Game Designers Inc.");

        boardGame.setMinPlayers(3);
        boardGame.setMaxPlayers(6);
        boardGame.setDesigners("New Designers Co.");

        assertEquals(3, boardGame.getMinPlayers());
        assertEquals(6, boardGame.getMaxPlayers());
        assertEquals("New Designers Co.", boardGame.getDesigners());
    }

   
}
