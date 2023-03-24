package chess.domain.piece;

import chess.domain.square.Color;
import chess.domain.square.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static chess.domain.piece.Direction.*;
import static chess.domain.square.Color.BLACK;
import static chess.domain.square.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;

class KingTest {
    static Stream<Arguments> canMoveDummy() {
        return Stream.of(
                Arguments.of(NORTH_EAST, 1),
                Arguments.of(NORTH_WEST, 1),
                Arguments.of(SOUTH_EAST, 1),
                Arguments.of(SOUTH_WEST, 1),
                Arguments.of(NORTH, 1),
                Arguments.of(EAST, 1),
                Arguments.of(SOUTH, 1),
                Arguments.of(WEST, 1)
        );
    }

    static Stream<Arguments> canNotMoveDummy() {
        return Stream.of(
                Arguments.of(NORTH_EAST, 7),
                Arguments.of(NORTH_WEST, 7),
                Arguments.of(SOUTH_EAST, 7),
                Arguments.of(SOUTH_WEST, 7)
        );
    }

    @ParameterizedTest
    @MethodSource("canMoveDummy")
    @DisplayName("킹이 이동할 수 있는 위치인지 확인한다.")
    void isMovable(final Direction direction, final int distance) {
        // when
        King king = new King(Team.from(Color.BLACK), Role.KING);

        // expected
        assertThat(king.canMove(direction, distance)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("canNotMoveDummy")
    @DisplayName("킹이 이동할 수 없는 위치인지 확인한다.")
    void isUnmovable(final Direction direction, final int distance) {
        // when
        King king = new King(Team.from(Color.BLACK), Role.KING);

        // expected
        assertThat(king.canMove(direction, distance)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("canMoveDummy")
    @DisplayName("다른 팀을 공격할 수 있다.")
    void canAttack(final Direction direction, final int distance) {
        // when
        King king = new King(Team.from(Color.BLACK), Role.KING);
        Pawn opponentPiece = new Pawn(Team.from(WHITE), Role.PAWN);

        // expected
        assertThat(king.canAttack(direction, distance, opponentPiece)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("canNotMoveDummy")
    @DisplayName("같은 팀을 공격할 수 없다.")
    void canNotAttack(final Direction direction, final int distance) {
        // when
        King king = new King(Team.from(Color.BLACK), Role.KING);
        Pawn opponentPiece = new Pawn(Team.from(BLACK), Role.PAWN);

        // expected
        assertThat(king.canAttack(direction, distance, opponentPiece)).isFalse();
    }
}
