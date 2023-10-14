package com.github.zeromodsinc.cyanide.utility;

public interface ParameterizedVisitor<A, B> {
    B run(A value);
}
