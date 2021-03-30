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
        // TODO: choose which CCD to run


        // TODO: choose vuln_level


        SolidityCCD.run("nicad", 1);
    }

    private static void run(String algoName, int vulnLevel) {
        // read files from the original folder
        Map<String , List<Integer>> abstGroup = new HashMap<String, List<Integer>>();
        String vulnLevelFolder = "mintVuln" + vulnLevel + "/";

        File originalFiles = new File(Constants.ORIGINAL_FOLDER + vulnLevelFolder);
        for (File file : originalFiles.listFiles()) {
            try {
                String code = FileUtils.readFileToString(file, "UTF-8");

                //run the selected CCD on the read files and categorize them accordingly
                String abstCode = algorithms.runAlgo(algoName, code);

                Integer fileName = new Integer(file.getName().split("\\.")[0]);
                /// if the abstracted code is already in the group,
                /// add the file number to the group's list
                if (abstGroup.get(abstCode) != null) {
                    List<Integer> filesList = abstGroup.get(abstCode);

                    filesList.add(fileName);
                    abstGroup.put(abstCode, filesList);
                }
                else {
                    List<Integer> filesList = new ArrayList<Integer>();

                    filesList.add(fileName);
                    abstGroup.put(abstCode, filesList);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // triage into sub-folders in the corresponding CCD folder
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

            String currFolder = SolidityCCD.getAlgoFolder(algoName, vulnLevelFolder);
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

}
