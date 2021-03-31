package org.yjkellyjoo;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.FileUtils;

import org.yjkellyjoo.ccd.algorithms;
import org.yjkellyjoo.utils.Constants;


public class SolidityCCD {

    public static void main(String[] args) {

        if (args.length == 0) {
            SolidityCCD.run("nicad", 1);
            SolidityCCD.run("nicad", 2);
//            SolidityCCD.run("ccfinder", 1);
//            SolidityCCD.run("ccfinder", 2);
//            SolidityCCD.run("vuddy", 1);
//            SolidityCCD.run("vuddy", 2);
//            SolidityCCD.run("sourcerercc", 1);
//            SolidityCCD.run("sourcerercc", 2);
        }

        else {
            SolidityCCD.run(args[0], Integer.parseInt(args[1]));
        }

    }

    private static void run(String algoName, int vulnLevel) {
        /* run abstraction algorithm */
        // read files from the original folder
        Map<String , List<Integer>> abstGroup = new HashMap<>();
        String vulnLevelFolder = "mintVuln" + vulnLevel + "/";

        File originalFiles = new File(Constants.ORIGINAL_FOLDER + vulnLevelFolder);
        if (originalFiles.listFiles() == null) {
            System.err.println("original folder does not exist.. exiting program..");
            System.exit(1);
        }
        for (File file : originalFiles.listFiles()) {
            try {
                String code = FileUtils.readFileToString(file, "UTF-8");

                // run the selected CCD on the read files and categorize them accordingly
                String abstCode = SolidityCCD.runAlgo(algoName, code);

                Integer fileName = new Integer(file.getName().split("\\.")[0]);
                // if the abstracted code is already in the group,
                // add the file number to the group's list
                if (abstGroup.get(abstCode) != null) {
                    List<Integer> filesList = abstGroup.get(abstCode);

                    filesList.add(fileName);
                    abstGroup.put(abstCode, filesList);
                }
                else {
                    List<Integer> filesList = new ArrayList<>();

                    filesList.add(fileName);
                    abstGroup.put(abstCode, filesList);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        /* triage into sub-folders in the corresponding CCD folder */
        // if vulnLevelFolder exists, empty it first.
        String currFolder = SolidityCCD.getAlgoFolder(algoName, vulnLevelFolder);
        File f = new File(currFolder);
        if (f.exists()) {
            try {
                FileUtils.cleanDirectory(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // write text files into according subgroup folders
        int subFolderCount = 0;
        Set<String> abstCodes = abstGroup.keySet();
        for (String abstCode: abstCodes) {
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

    private static String getAlgoFolder(String algoName, String vulnLevelFolder) {
        String currFolder = null;
        switch (algoName) {
            case "nicad":
                currFolder = Constants.NICAD_FOLDER + vulnLevelFolder;
                break;
            case "ccfinder":
                currFolder = Constants.CCFINDER_FOLDER + vulnLevelFolder;
                break;
            case "vuddy":
                currFolder = Constants.VUDDY_FOLDER + vulnLevelFolder;
                break;
            case "sourcerercc":
                currFolder = Constants.SOURCERERCC_FOLDER + vulnLevelFolder;
                break;
            default:
                System.err.println("wrong algorithm name.");
        }
        return currFolder;
    }

    private static String runAlgo(String algoName, String code) {
        String abstCode = null;
        switch (algoName) {
            case "nicad":
                abstCode = algorithms.runNicad(code);
                break;
            case "ccfinder":
                abstCode = algorithms.runCCFinder(code);
                break;
            case "vuddy":
                abstCode = algorithms.runVuddy(code);
                break;
            case "sourcerercc":
                abstCode = algorithms.runSourcererCC(code);
                break;
            default:
                System.err.println("wrong algorithm name.");
        }
        return abstCode;
    }


}
