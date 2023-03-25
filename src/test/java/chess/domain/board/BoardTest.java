package chess.domain.board;

import chess.domain.piece.Piece;
import chess.domain.piece.Role;
import chess.domain.square.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class BoardTest {
    private Board board;

    @BeforeEach
    void setup() {
        board = BoardFactory.create();
    }

    @ParameterizedTest
    @MethodSource("pieceDummy")
    @DisplayName("초기 세팅 후 위치를 확인한다.")
    void create(final File file, final Rank rank, final Role expectedRole, final Team team) {
        // expected
        Piece piece = board.findPiece(file, rank);

        assertThat(piece).isInstanceOf(expectedRole.create(team).getClass());

    }

    static Stream<Arguments> pieceDummy() {
        return Stream.of(
                // 폰을 제외한 백의 기물
                Arguments.arguments(File.A, Rank.ONE, Role.ROOK, Team.from(Color.WHITE)),
                Arguments.arguments(File.B, Rank.ONE, Role.KNIGHT, Team.from(Color.WHITE)),
                Arguments.arguments(File.C, Rank.ONE, Role.BISHOP, Team.from(Color.WHITE)),
                Arguments.arguments(File.D, Rank.ONE, Role.QUEEN, Team.from(Color.WHITE)),
                Arguments.arguments(File.E, Rank.ONE, Role.KING, Team.from(Color.WHITE)),
                Arguments.arguments(File.F, Rank.ONE, Role.BISHOP, Team.from(Color.WHITE)),
                Arguments.arguments(File.G, Rank.ONE, Role.KNIGHT, Team.from(Color.WHITE)),
                Arguments.arguments(File.H, Rank.ONE, Role.ROOK, Team.from(Color.WHITE)),
                // 백의 폰
                Arguments.arguments(File.A, Rank.TWO, Role.INITIAL_PAWN, Team.from(Color.WHITE)),
                Arguments.arguments(File.B, Rank.TWO, Role.INITIAL_PAWN, Team.from(Color.WHITE)),
                Arguments.arguments(File.C, Rank.TWO, Role.INITIAL_PAWN, Team.from(Color.WHITE)),
                Arguments.arguments(File.D, Rank.TWO, Role.INITIAL_PAWN, Team.from(Color.WHITE)),
                Arguments.arguments(File.E, Rank.TWO, Role.INITIAL_PAWN, Team.from(Color.WHITE)),
                Arguments.arguments(File.F, Rank.TWO, Role.INITIAL_PAWN, Team.from(Color.WHITE)),
                Arguments.arguments(File.G, Rank.TWO, Role.INITIAL_PAWN, Team.from(Color.WHITE)),
                Arguments.arguments(File.H, Rank.TWO, Role.INITIAL_PAWN, Team.from(Color.WHITE)),
                // 흑의 폰
                Arguments.arguments(File.A, Rank.SEVEN, Role.INITIAL_PAWN, Team.from(Color.BLACK)),
                Arguments.arguments(File.B, Rank.SEVEN, Role.INITIAL_PAWN, Team.from(Color.BLACK)),
                Arguments.arguments(File.C, Rank.SEVEN, Role.INITIAL_PAWN, Team.from(Color.BLACK)),
                Arguments.arguments(File.D, Rank.SEVEN, Role.INITIAL_PAWN, Team.from(Color.BLACK)),
                Arguments.arguments(File.E, Rank.SEVEN, Role.INITIAL_PAWN, Team.from(Color.BLACK)),
                Arguments.arguments(File.F, Rank.SEVEN, Role.INITIAL_PAWN, Team.from(Color.BLACK)),
                Arguments.arguments(File.G, Rank.SEVEN, Role.INITIAL_PAWN, Team.from(Color.BLACK)),
                Arguments.arguments(File.H, Rank.SEVEN, Role.INITIAL_PAWN, Team.from(Color.BLACK)),
                // 폰을 제외한 흑의 기물
                Arguments.arguments(File.A, Rank.EIGHT, Role.ROOK, Team.from(Color.BLACK)),
                Arguments.arguments(File.B, Rank.EIGHT, Role.KNIGHT, Team.from(Color.BLACK)),
                Arguments.arguments(File.C, Rank.EIGHT, Role.BISHOP, Team.from(Color.BLACK)),
                Arguments.arguments(File.D, Rank.EIGHT, Role.QUEEN, Team.from(Color.BLACK)),
                Arguments.arguments(File.E, Rank.EIGHT, Role.KING, Team.from(Color.BLACK)),
                Arguments.arguments(File.F, Rank.EIGHT, Role.BISHOP, Team.from(Color.BLACK)),
                Arguments.arguments(File.G, Rank.EIGHT, Role.KNIGHT, Team.from(Color.BLACK)),
                Arguments.arguments(File.H, Rank.EIGHT, Role.ROOK, Team.from(Color.BLACK))
        );
    }

    @Test
    @DisplayName("내 말이 아닌 경우 예외를 발생한다.")
    void moveExceptionWhenIsNotTurn() {
        // given
        Square sourceSquare = Square.of(File.A, Rank.SEVEN);
        Square targetSquare = Square.of(File.A, Rank.FIVE);

        // expected
        assertThatThrownBy(() -> board.makeMove(sourceSquare, targetSquare))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("같은 위치로 이동할 경우 예외가 발생한다.")
    void moveExceptionWhenSameSquare() {
        // given
        Square sourceSquare = Square.of(File.A, Rank.TWO);
        Square targetSquare = Square.of(File.A, Rank.TWO);

        // expected
        assertThatThrownBy(() -> board.makeMove(sourceSquare, targetSquare))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이동하려는 말이 없을 경우 예외가 발생한다.")
    void moveExceptionWhenEmptySquare() {
        // given
        Square sourceSquare = Square.of(File.A, Rank.FIVE);
        Square targetSquare = Square.of(File.A, Rank.SEVEN);

        // expected
        assertThatThrownBy(() -> board.makeMove(sourceSquare, targetSquare))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("막혀있는 경우 예외가 발생한다.")
    void moveExceptionWhenIsNotEmptyPath() {
        // given
        Square sourceSquare = Square.of(File.A, Rank.ONE);
        Square targetSquare = Square.of(File.A, Rank.FIVE);

        // expected
        assertThatThrownBy(() -> board.makeMove(sourceSquare, targetSquare))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이동하려는 위치에 같은 진영 말이 있을 경우 예외가 발생한다.")
    void moveExceptionWhenIsSameSide() {
        // given
        Square sourceSquare = Square.of(File.C, Rank.ONE);
        Square targetSquare = Square.of(File.B, Rank.TWO);

        // expected
        assertThatThrownBy(() -> board.makeMove(sourceSquare, targetSquare))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("squareDummy")
    @DisplayName("체스판의 말을 움직인다.")
    void move(final File sourceFile, final Rank sourceRank,
              final File targetFile, final Rank targetRank,
              final Role expectedRole) {
        Square sourceSquare = Square.of(sourceFile, sourceRank);
        Square targetSquare = Square.of(targetFile, targetRank);

        board.makeMove(sourceSquare, targetSquare);

        Piece piece = board.findPiece(targetFile, targetRank);

        assertThat(piece.getRole()).isEqualTo(expectedRole);
    }

    static Stream<Arguments> squareDummy() {
        return Stream.of(
                Arguments.of(File.A, Rank.TWO, File.A, Rank.FOUR, Role.PAWN),
                Arguments.of(File.B, Rank.ONE, File.C, Rank.THREE, Role.KNIGHT)
        );
    }

    @Test
    @DisplayName("초기 보드의 백팀 점수를 계산한다.")
    void calculateScore() {
        // when
        double score = board.calculateScore(Team.from(Color.WHITE));

        // expected
        assertThat(score).isCloseTo(38.0, withinPercentage(100));
    }

    @Test
    @DisplayName("같은 색깔 폰이 한 줄에 있을 때 각 폰의 점수는 0.5점이다.")
    void calculateSamePawnScore() {
        // given
        Map<Square, Piece> pieces = Map.of(
                Square.of(File.A, Rank.TWO), Role.PAWN.create(Team.from(Color.WHITE)),
                Square.of(File.A, Rank.THREE), Role.PAWN.create(Team.from(Color.WHITE))
        );
        Board board = BoardFactory.create(pieces);

        // when
        double score = board.calculateScore(Team.from(Color.WHITE));

        // expected
        assertThat(score).isCloseTo(1.0, withinPercentage(100));
    }

    @Test
    @DisplayName("게임의 승자를 찾는다.")
    void findWinner() {
        // given
        Map<Square, Piece> pieces = Map.of(
                Square.of(File.A, Rank.TWO), Role.KING.create(Team.from(Color.WHITE)),
                Square.of(File.A, Rank.THREE), Role.PAWN.create(Team.from(Color.BLACK))
        );
        Board board = BoardFactory.create(pieces);

        // expected
        assertThat(board.findWinner()).isSameAs(Team.from(Color.WHITE));
    }

    @Test
    @DisplayName("승자를 찾을 때 킹이 없으면 예외를 던진다.")
    void throwWhenBoardHasNotKing() {
        // given
        Map<Square, Piece> pieces = Map.of(
                Square.of(File.A, Rank.TWO), Role.PAWN.create(Team.from(Color.WHITE)),
                Square.of(File.A, Rank.THREE), Role.PAWN.create(Team.from(Color.BLACK))
        );
        Board board = BoardFactory.create(pieces);

        // expected
        assertThatThrownBy(board::findWinner)
                .isInstanceOf(IllegalStateException.class);
    }

    @ParameterizedTest
    @MethodSource("isEndDummy")
    @DisplayName("게임이 끝났는지 확인한다.")
    void isEnd(final Map<Square, Piece> pieces, final boolean expected) {
        // given
        Board board = BoardFactory.create(pieces);

        // expected
        assertThat(board.isEnd()).isEqualTo(expected);
    }

    static Stream<Arguments> isEndDummy() {
        return Stream.of(
                Arguments.of(
                        Map.of(
                                Square.of(File.A, Rank.TWO), Role.KING.create(Team.from(Color.WHITE)),
                                Square.of(File.A, Rank.THREE), Role.KING.create(Team.from(Color.BLACK))
                        ), false
                ), Arguments.of(
                        Map.of(
                                Square.of(File.A, Rank.TWO), Role.KING.create(Team.from(Color.WHITE)),
                                Square.of(File.A, Rank.THREE), Role.PAWN.create(Team.from(Color.BLACK))
                        ), true
                )
        );
    }
}
