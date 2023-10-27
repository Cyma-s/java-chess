package chess.domain.piece;

import chess.domain.square.Color;
import chess.domain.square.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static chess.domain.piece.Direction.*;
import static chess.domain.square.Color.BLACK;
import static chess.domain.square.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;

class PawnTest {
    static Stream<Arguments> canAttackDummy() {
        return Stream.of(
                Arguments.of(SOUTH_WEST, 1, Side.from(BLACK), Side.from(WHITE)),
                Arguments.of(SOUTH_EAST, 1, Side.from(BLACK), Side.from(WHITE)),
                Arguments.of(NORTH_WEST, 1, Side.from(WHITE), Side.from(BLACK)),
                Arguments.of(NORTH_EAST, 1, Side.from(WHITE), Side.from(BLACK))
        );
    }

    static Stream<Arguments> canNotAttackDummy() {
        return Stream.of(
                Arguments.of(SOUTH_WEST, 1, Side.from(BLACK), Side.from(BLACK)),
                Arguments.of(SOUTH_EAST, 1, Side.from(BLACK), Side.from(BLACK)),
                Arguments.of(NORTH_WEST, 1, Side.from(WHITE), Side.from(WHITE)),
                Arguments.of(NORTH_EAST, 1, Side.from(WHITE), Side.from(WHITE)),
                Arguments.of(SOUTH_WEST, 1, Side.from(WHITE), Side.from(BLACK)),
                Arguments.of(SOUTH_EAST, 1, Side.from(WHITE), Side.from(BLACK)),
                Arguments.of(NORTH_WEST, 1, Side.from(BLACK), Side.from(WHITE)),
                Arguments.of(NORTH_EAST, 1, Side.from(BLACK), Side.from(WHITE))
        );
    }

    @Test
    @DisplayName("폰이 이동할 수 있는 위치인지 확인한다.")
    void isMovable() {
        // when
        Pawn blackPawn = new Pawn(Side.from(Color.BLACK), Role.PAWN);
        Pawn whitePawn = new Pawn(Side.from(Color.WHITE), Role.PAWN);

        // expected
        assertThat(blackPawn.canMove(SOUTH, 1)).isTrue();
        assertThat(whitePawn.canMove(NORTH, 1)).isTrue();
    }

    @Test
    @DisplayName("폰이 이동할 수 없는 위치인지 확인한다.")
    void canNotMove() {
        // when
        Pawn blackPawn = new Pawn(Side.from(Color.BLACK), Role.PAWN);
        Pawn whitePawn = new Pawn(Side.from(Color.WHITE), Role.PAWN);

        // expected
        assertThat(blackPawn.canMove(NORTH, 1)).isFalse();
        assertThat(whitePawn.canMove(SOUTH, 1)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("canAttackDummy")
    @DisplayName("다른 팀은 공격할 수 있다.")
    void canAttack(final Direction direction, final int distance, final Side side, final Side opponentSide) {
        // when
        Pawn pawn = new Pawn(side, Role.PAWN);
        Pawn opponentPiece = new Pawn(opponentSide, Role.PAWN);

        // expected
        assertThat(pawn.canAttack(direction, distance, opponentPiece)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("canNotAttackDummy")
    @DisplayName("같은 팀은 공격할 수 없다.")
    void canNotAttack(final Direction direction, final int distance, final Side side, final Side opponentSide) {
        // when
        Pawn pawn = new Pawn(side, Role.PAWN);
        Pawn opponentPiece = new Pawn(opponentSide, Role.PAWN);
        // expected
        assertThat(pawn.canAttack(direction, distance, opponentPiece)).isFalse();
    }
}
