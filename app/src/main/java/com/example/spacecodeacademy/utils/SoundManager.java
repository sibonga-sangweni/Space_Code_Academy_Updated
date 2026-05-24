package com.example.spacecodeacademy.utils;

import android.content.Context;
import android.media.MediaPlayer;
import com.example.spacecodeacademy.R;

public class SoundManager {

    private static MediaPlayer backgroundMusic;
    private static MediaPlayer clickSound;
    private static MediaPlayer successSound;
    private static MediaPlayer errorSound;
    private static MediaPlayer unlockSound;
    private static MediaPlayer xpSound;

    // Play button click sound
    public static void playClick(Context context) {
        if (clickSound != null) {
            clickSound.release();
        }
        clickSound = MediaPlayer.create(context, R.raw.click);
        clickSound.start();
        clickSound.setOnCompletionListener(MediaPlayer::release);
    }

    // Play success sound (correct answer)
    public static void playSuccess(Context context) {
        if (successSound != null) {
            successSound.release();
        }
        successSound = MediaPlayer.create(context, R.raw.success);
        successSound.start();
        successSound.setOnCompletionListener(MediaPlayer::release);
    }

    // Play error sound (wrong answer)
    public static void playError(Context context) {
        if (errorSound != null) {
            errorSound.release();
        }
        errorSound = MediaPlayer.create(context, R.raw.error);
        errorSound.start();
        errorSound.setOnCompletionListener(MediaPlayer::release);
    }

    // Play planet unlock sound
    public static void playUnlock(Context context) {
        if (unlockSound != null) {
            unlockSound.release();
        }
        unlockSound = MediaPlayer.create(context, R.raw.planet_unlock);
        unlockSound.start();
        unlockSound.setOnCompletionListener(MediaPlayer::release);
    }

    // Play XP gain sound
    public static void playXPGain(Context context) {
        if (xpSound != null) {
            xpSound.release();
        }
        xpSound = MediaPlayer.create(context, R.raw.xp_gain);
        xpSound.start();
        xpSound.setOnCompletionListener(MediaPlayer::release);
    }

    // Start background space ambience music
    public static void startBackgroundMusic(Context context) {
        if (backgroundMusic == null) {
            backgroundMusic = MediaPlayer.create(context, R.raw.space_ambience);
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.3f, 0.3f);
            backgroundMusic.start();
        }
    }

    // Stop background music
    public static void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            if (backgroundMusic.isPlaying()) {
                backgroundMusic.stop();
            }
            backgroundMusic.release();
            backgroundMusic = null;
        }
    }

    // Pause background music
    public static void pauseBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.pause();
        }
    }

    // Resume background music
    public static void resumeBackgroundMusic() {
        if (backgroundMusic != null && !backgroundMusic.isPlaying()) {
            backgroundMusic.start();
        }
    }
}