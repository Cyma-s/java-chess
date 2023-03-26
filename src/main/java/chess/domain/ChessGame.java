package chess.domain;

import chess.dao.ChessDao;
import chess.domain.board.Board;
import chess.domain.square.Color;
import chess.domain.square.Square;
import chess.domain.square.Team;
import chess.domain.state.State;
import chess.domain.state.WaitingStart;
import chess.dto.MoveDto;
import java.util.Map;

public class ChessGame {
    private State state;

    public ChessGame() {
        this.state = new WaitingStart();
    }

    public void start() {
        this.state = state.start();
    }

    public void move(final Square sourceSquare, final Square targetSquare, final ChessDao chessDao) {
        this.state = state.move(sourceSquare, targetSquare);
        chessDao.saveHistory(MoveDto.of(sourceSquare, targetSquare));
    }

    public Map<Team, Double> status() {
        return Map.of(
                Team.from(Color.WHITE), state.calculateScore(Team.from(Color.WHITE)),
                Team.from(Color.BLACK), state.calculateScore(Team.from(Color.BLACK))
        );
    }

    public void end() {
        this.state = state.end();
    }

    public Board getBoard() {
        return state.getBoard();
    }

    public boolean isEnd() {
        return state.isEnd();
    }

    public Team getWinner() {
        return state.getWinner();
    }
}
