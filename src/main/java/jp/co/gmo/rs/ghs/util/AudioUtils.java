/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.util;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 * AudioUtils
 *
 * @author LongVNH
 * @version 1.0
 */
public class AudioUtils {

    /**
     * @param audioInputStream
     * @param samplingRate
     * @param channel
     * @return
     */
    public AudioInputStream convertStream(AudioInputStream audioInputStream,
            int samplingRate, int channel) {

        // Step 1:chance to PCM
        // Step 2:chance channel
        audioInputStream = convertChannels(1, audioInputStream);
        // Step 3:convert sample size and endianess, if necessary.
        // Step 4: convert sample rate
        audioInputStream = convertSampleRate(samplingRate, audioInputStream);
        return audioInputStream;

    }

    /**
     * @param nChannels
     * @param sourceStream
     * @return
     */
    private static AudioInputStream convertChannels(int nChannels,
            AudioInputStream sourceStream) {
        AudioFormat sourceFormat = sourceStream.getFormat();
        AudioFormat targetFormat = new AudioFormat(sourceFormat.getEncoding(),
                sourceFormat.getSampleRate(),
                sourceFormat.getSampleSizeInBits(), nChannels,
                calculateFrameSize(nChannels,
                        sourceFormat.getSampleSizeInBits()),
                sourceFormat.getFrameRate(), sourceFormat.isBigEndian());
        return AudioSystem.getAudioInputStream(targetFormat, sourceStream);
    }

    /**
     * @param fSampleRate
     * @param sourceStream
     * @return
     */
    private static AudioInputStream convertSampleRate(float fSampleRate,
            AudioInputStream sourceStream) {
        AudioFormat sourceFormat = sourceStream.getFormat();
        AudioFormat targetFormat = new AudioFormat(sourceFormat.getEncoding(),
                fSampleRate, sourceFormat.getSampleSizeInBits(),
                sourceFormat.getChannels(), sourceFormat.getFrameSize(),
                fSampleRate, sourceFormat.isBigEndian());
        return AudioSystem.getAudioInputStream(targetFormat, sourceStream);
    }

    /**
     * @param nChannels
     * @param nSampleSizeInBits
     * @return
     */
    private static int calculateFrameSize(int nChannels, int nSampleSizeInBits) {
        return ((nSampleSizeInBits + 7) / 8) * nChannels;
    }

}
