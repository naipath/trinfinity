package nl.ordina;

import nl.ordina.services.BoardService;
import nl.ordina.services.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;

@ApplicationScoped
public class Game {

    @Inject private BoardService boardService;
    @Inject private UserService userService;

    public void addCoordinate(String coordinateId, String sessionId) {
        Coordinate coordinate = new Coordinate(coordinateId, userService.get(sessionId));

        if (!boardService.isOccupied(coordinate)) {
            boardService.add(coordinate);

            userService.sendCoordinateToAllUsers(coordinate);

            if (boardService.isWinningConditionMetAndAlsoTheMostUglyMethodEver(coordinate)) {
                boardService.clearBord(userService.getAllUsers());
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

    public void clearBord() {
        boardService.clearBord(userService.getAllUsers());
    }
}