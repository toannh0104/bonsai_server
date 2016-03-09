/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.model;

/**
 * create TextTranslate class
 * 
 * @author ThuyTTT
 *
 */
public class TextTranslate {
    private Text input;
    private Text output;

    /**
     * 
     * @param input is text input
     * @param loutput code language output
     */
    public TextTranslate(Text input, String loutput) {
        this.input = input;
        output = new Text(loutput);
    }

    /**
     * @return the input
     */
    public Text getInput() {
        return input;
    }

    /**
     * @param input the input to set
     */
    public void setInput(Text input) {
        this.input = input;
    }

    /**
     * @return the output
     */
    public Text getOutput() {
        return output;
    }

    /**
     * @param output the output to set
     */
    public void setOutput(Text output) {
        this.output = output;
    }

}
