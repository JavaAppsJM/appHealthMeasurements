package be.hvwebsites.healthmeasurements.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BellyFile extends SuperFile{
    private File bellyfile;
    private Scanner inFile;
    private PrintWriter outFile;
    private List<String> bellyLines = new ArrayList<>();
    private Boolean bestaat;

    public BellyFile(String basedir){
        super(basedir);
        setFileName("belly.txt");

        bellyfile = new File(basedir, fileName);
        if (bellyfile.exists()){
            // file bestaat
            try {
                inFile = new Scanner(bellyfile);
                int i = 0;
                while (inFile.hasNext()){
                    bellyLines.add(inFile.nextLine());
                }
                inFile.close();
                bestaat = true;

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        } else {
            // file bestaat niet
            bestaat = false;
        }
    }

    public boolean writeBelliesToFile(){
        // test of file bestaat
        if (bellyfile.exists()){
            // file bestaat dan moet ie leeg gemaakt worden
            bellyfile.delete();
        }
        try {
            outFile = new PrintWriter(bellyfile);
            for (int i = 0; i < bellyLines.size(); i++) {
                outFile.println(bellyLines.get(i));
            }
            outFile.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public File getBellyfile() {
        return bellyfile;
    }

    public List<String> getBellyLines() {
        return bellyLines;
    }

    public void setBellyLines(List<String> bellyLines) {
        this.bellyLines = bellyLines;
    }

    public Boolean getBestaat() {
        return bestaat;
    }

    public void setBestaat(Boolean bestaat) {
        this.bestaat = bestaat;
    }
}
