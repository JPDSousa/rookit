package org.rookit.parser.format.field;

import com.google.common.base.MoreObjects;

enum InitialScore {

    /**
     * No value
     */
    L0_VALUE(0),

    /**
     * Level 1 value
     */
    L1_VALUE(1),

    /**
     * Level 2 value
     */
    L2_VALUE(3);

    private final int score;

    InitialScore(final int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("score", this.score)
                .toString();
    }
}
