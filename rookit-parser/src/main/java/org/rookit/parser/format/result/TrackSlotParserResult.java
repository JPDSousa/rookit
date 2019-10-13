package org.rookit.parser.format.result;

import accumulator.dm.album.slot.TrackSlotAccumulator;

import java.util.Optional;

public interface TrackSlotParserResult extends TrackSlotAccumulator, Comparable<TrackSlotParserResult> {

    TrackSlotParserResult createResultStage(TrackSlotParserResultFactory factory);

    int getScore();

    void setScore(int score);

    Optional<String> getExpression();

    void setExpression(String expression);
}
