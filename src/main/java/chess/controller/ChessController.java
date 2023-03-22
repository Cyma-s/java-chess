package chess.controller;

import chess.controller.command.Command;
import chess.controller.command.CommandFactory;
import chess.domain.ChessGame;
import chess.domain.piece.Piece;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import chess.view.InputView;
import chess.view.OutputView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessController {
    private static final int SOURCE_SQUARE_INDEX = 0;
    private static final int TARGET_SQUARE_INDEX = 1;

    private final InputView inputView;
    private final OutputView outputView;
    private final ChessGame chessGame;

    public ChessController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.chessGame = new ChessGame();
    }

    public void run() {
        showStartMessage();

        while (chessGame.isRunning()) {
            repeatIfExceptionOccur(this::play);
        }
    }

    private void repeatIfExceptionOccur(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            outputView.printExceptionMessage(e.getMessage());
            repeatIfExceptionOccur(runnable);
        }
    }

    private void play() {
        String inputCommand = inputView.readCommand();
        Command command = CommandFactory.from(inputCommand);
        command.execute(this);
    }

    public void start() {
        chessGame.start();
        showBoard();
    }

    public void move(final List<String> parameters) {
        Square sourceSquare = convertSquare(parameters.get(SOURCE_SQUARE_INDEX));
        Square targetSquare = convertSquare(parameters.get(TARGET_SQUARE_INDEX));
        chessGame.move(sourceSquare, targetSquare);
        showBoard();
    }

    public void end() {
        chessGame.end();
    }

    private Square convertSquare(final String square) {
        return Square.from(square);
    }

    private void showStartMessage() {
        outputView.printStartMessage();
    }

    private void showBoard() {
        outputView.printBoard(convertBoard());
    }

    private List<List<Piece>> convertBoard() {
        List<List<Piece>> pieces = new ArrayList<>();
        List<File> files = List.of(File.values());
        List<Rank> ranks = List.of(Rank.values());
        for (Rank rank : ranks) {
            List<Piece> rankPieces = files.stream()
                    .map(file -> chessGame.getBoard().findPiece(file, rank))
                    .collect(Collectors.toList());
            pieces.add(rankPieces);
        }
        return pieces;
    }
}
