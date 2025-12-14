package com.example.geektrust;

public class Main {
    private static final int MIN_ARGS_WITH_CONFIG = 2;
    private static final int CONFIG_ARG_INDEX = 1;
    private static final int INPUT_FILE_INDEX = 0;

    public static void main(String [] args){

        if(args.length < 1){
            throw new IllegalArgumentException("Missing input file path");
        }

        String inputFilePath = args[INPUT_FILE_INDEX];
        String configFilePath = "config.properties";

        if(args.length >= MIN_ARGS_WITH_CONFIG){
            configFilePath = args[CONFIG_ARG_INDEX];
        }

        App app = new App(configFilePath);
        try {
            app.run(inputFilePath);
        } catch (Exception e) {
            throw new RuntimeException("Application failed to run: " + e.getMessage(), e);
        }

    }

}

