package nl.ordina;

import nl.ordina.services.BoardService;
import nl.ordina.services.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;
import java.util.List;

@ApplicationScoped
public class Game {

    @Inject private BoardService boardService;
    @Inject private UserService userService;

    public void addCoordinate(String coordinateId, String sessionId) {
        Coordinate coordinate = new Coordinate(coordinateId, userService.get(sessionId));

        if (!boardService.isOccupied(coordinate)) {
            boardService.add(coordinate);

            userService.sendCoordinateToAllUsers(coordinate);

            if (boardService.isWinningConditionMet(coordinate)) {
                boardService.gameEnding(userService.getAllUsers());
            }
        }
    }

    public void addUser(Session session) {
        userService.add(session);
        User user = userService.get(session.getId());
        boardService.getAllCoordinates().subscribe(user::sendCoordinate);
    }

    public void removeUser(Session session) {
        userService.remove(session);
    }

    public void resetGame() {
        boardService.resetGame();
        userService.sendReset();
    }
}