package nl.ordina;

import nl.ordina.message.CoordinateMessage;
import nl.ordina.message.SignupMessage;
import nl.ordina.services.BoardService;
import nl.ordina.services.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;

@ApplicationScoped
public class Game {

    @Inject private BoardService boardService;
    @Inject private UserService userService;

    public void addCoordinate(CoordinateMessage message, String sessionId) {
        User user = userService.get(sessionId);
        if (!user.hasSignedup()) {
            return;
        }

        Coordinate coordinate = new Coordinate(message.getCoordinate(), user);

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

    public void signup(Session s, SignupMessage signupMessage) {
        userService.get(s.getId()).signupUser(signupMessage.getUsername());
    }
}