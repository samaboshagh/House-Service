package org.example.finalprojectphasetwo.utility;

import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;

public class SemaphoreUtil {

    private SemaphoreUtil() {
    }

    private static final Semaphore newUserSemaphore = new Semaphore(1);
    private static final Semaphore newAddSuggestionSemaphore = new Semaphore(1);

    @SneakyThrows
    public static void acquireNewUserSemaphore() {
        newUserSemaphore.acquire();
    }

    @SneakyThrows
    public static void acquireNewSuggestionSemaphore() {
        newAddSuggestionSemaphore.acquire();
    }

    public static void releaseNewUserSemaphore() {
        newUserSemaphore.release();
    }

    public static void releaseNewSuggestionSemaphore() {
        newAddSuggestionSemaphore.release();
    }
}