package org.yjkellyjoo;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.FileUtils;

import org.yjkellyjoo.ccd.Algorithms;
import org.yjkellyjoo.utils.Constants;


public class SolidityCCD {

    private static Algorithms algorithms = new Algorithms();
    private static Constants constants = new Constants();

    public static void main(String[] args) {

        if (args.length == 0) {
            SolidityCCD.run("nicad", 1);
            SolidityCCD.run("nicad", 2);
            SolidityCCD.run("vuddy", 1);
            SolidityCCD.run("vuddy", 2);
            SolidityCCD.run("sourcerercc", 1);
            SolidityCCD.run("sourcerercc", 2);
        }

        else {
            SolidityCCD.run(args[0], Integer.parseInt(args[1]));
        }

    }

    private static void run(String algoName, int vulnLevel) {
        /* run abstraction algorithm */
        // read files from the original folder
        Map<Integer, Object> abstCodesMap = new HashMap<>();
        String vulnLevelFolder = "mintVuln" + vulnLevel + "/";

        File originalFiles = new File(constants.ORIGINAL_FOLDER + vulnLevelFolder);
        if (originalFiles.listFiles() == null) {
            System.err.println("original folder does not exist.. exiting program..");
            System.exit(1);
        }

        System.out.println("=== running " + algoName + " with vulnLevel " + vulnLevel);
        for (File file : originalFiles.listFiles()) {
            System.out.println("-- working on " + file.getName());
            try {
                String code = FileUtils.readFileToString(file, "UTF-8");

                // run the selected CCD on the read files and get abstracted codes
                Integer fileName = new Integer(file.getName().split("\\.")[0]);
                Object abstCode = algorithms.runAlgo(algoName, code);
                abstCodesMap.put(fileName, abstCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // if vulnLevelFolder exists, empty it first.
        String currFolder = algorithms.getAlgoFolder(algoName, vulnLevelFolder);
        File f = new File(currFolder);
        if (f.exists()) {
            try {
                FileUtils.cleanDirectory(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // categorize abstracted codes accordingly
        Map<String , List<Integer>> abstGroup = algorithms.compareAbstCodes(algoName, abstCodesMap, currFolder);

        /* triage into sub-folders in the corresponding CCD folder */
        // write text files into according subgroup folders
        // skip below in case of SourcererCC
        // TODO: reformulate below
        if (abstGroup == null) {
            return;
        }

        int subFolderCount = 0;
        Set<String> abstCodeSet = abstGroup.keySet();
        for (String abstCode: abstCodeSet) {
            List<Integer> files = abstGroup.get(abstCode);
            File tmp = new File(originalFiles.getPath() + '/' + files.get(0) + ".txt");
            String code = null;
            try {
                code = FileUtils.readFileToString(tmp, "UTF-8");
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            File subFolder = new File(currFolder + subFolderCount);
            subFolder.mkdirs();

            for (int fileName : files) {
                File file = new File(subFolder.getPath() + '/' + fileName + ".txt");
                try {
                    file.createNewFile();
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(code);
                    fileWriter.flush();
                    fileWriter.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String fileName = "abstCode.txt";
            File file = new File(currFolder + subFolderCount + '/' + fileName);
            try {
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(abstCode);
                fileWriter.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            subFolderCount++;
        }
    }



}
