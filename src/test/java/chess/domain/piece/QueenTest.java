package chess.domain.piece;

import chess.domain.Color;
import chess.domain.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class QueenTest {
    @ParameterizedTest
    @MethodSource("differenceDummy")
    @DisplayName("이동할 수 있는지 확인한다.")
    void isMovable(final int fileDifference, final int rankDifference) {
        // when
        Queen queen = new Queen(Side.from(Color.BLACK));

        // expected
        assertThat(queen.isMovable(fileDifference, rankDifference)).isTrue();
    }

    static Stream<Arguments> differenceDummy() {
        return Stream.of(
                Arguments.of(0, 7),
                Arguments.of(7, 0),
                Arguments.of(0, -7),
                Arguments.of(-7, 0),
                Arguments.of(7, 7),
                Arguments.of(-7, -7),
                Arguments.of(7, -7),
                Arguments.of(-7, 7)
        );
    }
    @ParameterizedTest
    @MethodSource("unmovableDifferenceDummy")
    @DisplayName("이동할 수 없는지 확인한다.")
    void isUnmovable(final int fileDifference, final int rankDifference) {
        // when
        Queen queen = new Queen(Side.from(Color.BLACK));

        // expected
        assertThat(queen.isMovable(fileDifference, rankDifference)).isFalse();
    }


    static Stream<Arguments> unmovableDifferenceDummy() {
        return Stream.of(
                Arguments.of(2, 1),
                Arguments.of(3, 1),
                Arguments.of(1, 5),
                Arguments.of(3, -4)
        );
    }
}
