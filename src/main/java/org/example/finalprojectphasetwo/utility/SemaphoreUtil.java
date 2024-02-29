package org.example.finalprojectphasetwo.utility;

import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;

public class SemaphoreUtil {

    private SemaphoreUtil() {
    }

    private static final Semaphore newUserSemaphore = new Semaphore(1);
    @SneakyThrows
    public static void acquireNewUserSemaphore() {
        newUserSemaphore.acquire();
    }
    public static void releaseNewUserSemaphore() {
        newUserSemaphore.release();
    }

}