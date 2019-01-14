package com.hackerrank.github.core.usecases;

public abstract class UseCase<I extends UseCase.InputValues, O extends UseCase.OutputValues> {
    public abstract O execute(I input);

    public interface InputValues {
    }

    public interface OutputValues {
    }

    public static final class NoInput implements InputValues {
    }

    public static NoInput noInput() {
        return new NoInput();
    }

}
