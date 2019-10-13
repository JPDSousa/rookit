package org.rookit.utils.string.tokenizer;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(canBuild = "isBuildable")
public interface Token {

    String token();

    String payload();

}
