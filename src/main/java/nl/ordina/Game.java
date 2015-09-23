package nl.ordina;

import rx.Observable;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class Game {

    private Map<String, String> board = new HashMap<>();

    public void addOccupation(String coordinate, String sessionId) {
        board.put(coordinate, sessionId);
    }

    public Observable<String> getCoordinates() {
        return Observable.create(subscriber ->
                board.forEach((coordinate, sessionId) -> subscriber.onNext(coordinate)));
    }

    public void clearBord(){
        board.clear();
    }
}
