package chess.domain.piece;

import chess.domain.Color;
import chess.domain.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static chess.domain.Color.BLACK;
import static chess.domain.Color.WHITE;
import static chess.domain.piece.Direction.*;
import static org.assertj.core.api.Assertions.assertThat;

class BishopTest {
    @ParameterizedTest
    @MethodSource("canMoveDummy")
    @DisplayName("이동할 수 있는지 확인한다.")
    void isMovable(final Direction direction, final int distance) {
        // when
        Bishop bishop = new Bishop(Side.from(Color.BLACK));

        // expected
        assertThat(bishop.canMove(direction, distance)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("canNotMoveDummy")
    @DisplayName("이동할 수 없는지 확인한다.")
    void isUnmovable(final Direction direction, final int distance)  {
        // when
        Bishop bishop = new Bishop(Side.from(Color.BLACK));

        // expected
        assertThat(bishop.canMove(direction, distance)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("canMoveDummy")
    @DisplayName("공격할 수 있는지 확인한다.")
    void canAttack(final Direction direction, final int distance)  {
        // when
        Bishop bishop = new Bishop(Side.from(Color.BLACK));
        Pawn opponentPiece = new Pawn(Side.from(WHITE));

        // expected
        assertThat(bishop.canAttack(direction, distance, opponentPiece)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("canNotMoveDummy")
    @DisplayName("공격할 수 없는지 확인한다.")
    void canNotAttack(final Direction direction, final int distance)  {
        // when
        Bishop bishop = new Bishop(Side.from(Color.BLACK));
        Pawn opponentPiece = new Pawn(Side.from(BLACK));

        // expected
        assertThat(bishop.canAttack(direction, distance, opponentPiece)).isFalse();
    }

    static Stream<Arguments> canMoveDummy() {
        return Stream.of(
                Arguments.of(NORTH_EAST, 7),
                Arguments.of(NORTH_WEST, 7),
                Arguments.of(SOUTH_EAST, 7),
                Arguments.of(SOUTH_WEST, 7)
        );
    }

    static Stream<Arguments> canNotMoveDummy() {
        return Stream.of(
                Arguments.of(NORTH, 7),
                Arguments.of(EAST, 7),
                Arguments.of(SOUTH, 7),
                Arguments.of(WEST, 7)
        );
    }
}
