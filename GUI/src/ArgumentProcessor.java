
import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Logger;

//This class processes the arguments that the user has put in the commandline calling this process.
public class ArgumentProcessor {

    public static Logger LOGGER = Logger.getLogger(ArgumentProcessor.class.getName());
    private boolean help;
    private ArrayList<String> pubmedIDs;
    private String workspace;
    private boolean PDFFiles;
    private String query;
    private String pathToConfigFile;
    private String pathToImageMagick;
    private String pathToTesseract;
    private String pathToTesseractConfigFile;
    private double verticalThresholdModifier;
    private double horizontalThresholdModifier;
    private boolean debugging;
    private boolean rotateTables;
    private int allowedHeaderSize;
    private int allowedHeaderIterations;
    private double imageMagickResolution;

    /**
     * The constructor of this class creates a commandline with the correct options (as was given by the user) and sets
     * the local variables.
     *
     * @param args The arguments as given by the user.
     * @throws org.apache.commons.cli.ParseException
     *                             if the given arguments are incorrect.
     * @throws java.io.IOException if a given file doesn't exists
     */
    public ArgumentProcessor(String[] args) throws ParseException, IOException {
        CommandLineParser parser = new PosixParser();
        Options options = new Options();

        Option help = new Option("H", "Help", false, "This is the help file of TEA.");
//        Option optionPubmedIDs = new Option("PUB", "PubmedIDFile", true, "The file with the PubmedID's");        //Currently Disabled
        Option optionWorkspace = new Option("W", "workspace", true, "The workspace of the program.");
        Option optionPDFFiles = new Option("PDF", "PDFFiles", false, "Instead of using a query or a PubmedID file, use the PDFs in the workspace.");
//        Option optionQuery= new Option("QUE", "Query", true, "Use a given query to search Pubmed and extract the articles.");       //Currently Disabled
        Option optionConfig = new Option("C", "Config", true, "Specify the path to the configuration file.");
        Option optionDebugging = new Option("D", "Debugging", false, "Enter debug mode to output additional files.");
        Option optionRotateTables = new Option("R", "RotateTables", false, "Rotate all pages to see if the tables are vertical");

        options.addOption(help);
//        options.addOption(optionPubmedIDs);
        options.addOption(optionWorkspace);
        options.addOption(optionPDFFiles);
//        options.addOption(optionQuery);
        options.addOption(optionConfig);
        options.addOption(optionDebugging);
        options.addOption(optionRotateTables);

        CommandLine line = null;
        try {
            line = parser.parse(options, args);
        } catch (UnrecognizedOptionException e) {
            LOGGER.info("There was an option given by the user that isn't supported by TEA. The system is shutting down. This is the error it produced: " + e);
            System.out.println("There was an option given by the user that isn't supported by TEA. System shutting down.");
            System.exit(1);
        } catch (MissingArgumentException e) {
            LOGGER.info("One of the options given by the user did not contain any value. " +
                    "The workspace option ( -W ) and the configuration file option ( -C ) need to contain a full path behind them. " +
                    "System shutting down.");
            System.out.println("One of the options given by the user did not contain any value. " +
                    "The workspace option ( -W ) and the configuration file option ( -C ) need to contain a full path behind them. " +
                    "System shutting down.");
            System.exit(1);
        }
        this.help = setHelp(line);
        this.pubmedIDs = setPubmedIDs(line);
        this.workspace = setWorkspace(line);
        this.PDFFiles = setPDFFiles(line);
        this.query = setQuery(line);
        this.debugging = setDebugging(line);
        this.rotateTables = setRotateTables(line);
        setPathToConfigFileValues(line);
    }
    //~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
    //The setters of this class:

    /**
     * This method sets the help boolean to check if someone used -H in their command.
     *
     * @param line the Commandline as made by the constructor.
     * @return a boolean that is only true when the commandline contains the help option
     */
    private boolean setHelp(CommandLine line) {
        if (line.hasOption("H")) {
            System.out.println("This is the help of TEA.");
            System.out.println("The following arguments are available: ");
            System.out.println("-H : Well you just pressed it. Happy?");
            System.out.println("-W [pathToWorkspace] : Define the path to the workspace");
            System.out.println("-C [pathToConfigurationFile] : Define the path to the configuration file");
            System.out.println("-D : Tells the program it needs to go in debugging mode and output additional output files.");
            System.out.println("-PDF : Tells TEA that there are PDF files in the workspace. True by default.");
            System.out.println("Please not that the TEA packages also comes with TEA Link which can be used to link similar tables and their headers with each other.");
            System.out.println("For more information on TEA Link check the README file.");
            System.exit(0);
        }
        return (line.hasOption("H"));
    }

    /**
     * This method set the pubmed IDS from the pubmedFile option.
     *
     * @param line the commandline as made by the constructor
     * @return An ArrayList containing the pubmedID's that were extracted from the file.
     * @throws java.io.IOException
     */
    private ArrayList<String> setPubmedIDs(CommandLine line) throws IOException {
        String pubmedFile;
        if (line.hasOption("PUB")) {
            pubmedFile = line.getOptionValue("PUB");
        } else {
            LOGGER.info("Couldn't find a PubmedID file.");
            return null;
        }

        //Open the file and read the PubmedIDs
        BufferedReader br = new BufferedReader(new FileReader(pubmedFile));
        String readLine;
        ArrayList<String> pubmedIDs = new ArrayList<String>();
        while ((readLine = br.readLine()) != null) {
            pubmedIDs.add(readLine);
        }
        br.close();
        LOGGER.info("Found " + pubmedIDs.size() + " pubmedIDs from the PubmedID file.");
        return pubmedIDs;
    }

    /**
     * This methods receives the workspace parameter from the arguments.
     *
     * @param line the commandline as made by the constructor
     * @return A string containing the full path to the workspace.
     */
    private String setWorkspace(CommandLine line) {
        String workspace;
        if (line.hasOption("W")) {
            workspace = line.getOptionValue("W");
            LOGGER.info("workspace set to: " + workspace);
            return workspace;
        } else {
            return null;
        }
    }

    /**
     * This method checks when the PDF option is enabled by the user.
     *
     * @param line the commandline as made by the constructor
     * @return a boolean that is only true when the user used -PDF in his arguments.
     */
    private boolean setPDFFiles(CommandLine line) {
        return !line.hasOption("QUE") && !line.hasOption("PUB");
    }

    /**
     * This method receives the Query if it was added as input by the user.
     *
     * @param line the commandline as made by the constructor
     * @return The query that was given after -QUE. Returns null when no query was given.
     */
    private String setQuery(CommandLine line) throws UnsupportedEncodingException {
        String query = null;
        if (line.hasOption("QUE")) {
            query = line.getOptionValue("QUE");
            query = URLEncoder.encode(query, "UTF-8");
            LOGGER.info("Used query: " + query);
        }
        return query;
    }

    /**
     * This method checks if the user added a configuration file. If so it creates a configuration object
     * and calls the getters of this class to set local variables that have to do with the configuration file.
     *
     * @param line the commandline as made by the constructor
     * @throws java.io.IOException if the location is invalid (such shame).
     */
    private void setPathToConfigFileValues(CommandLine line) throws IOException {
        if (line.hasOption("C") && !line.getOptionValue("C").equals(null)) {
            LOGGER.info("Configuration file detected.");
            pathToConfigFile = line.getOptionValue("C");
            Configuration config = new Configuration(pathToConfigFile);
            pathToImageMagick = config.getPathToImageMagick();
            pathToTesseract = config.getPathToTesseract();
            pathToTesseractConfigFile = config.getPathToTesseractConfigFile();
            imageMagickResolution = config.getImageMagickResolution();
            verticalThresholdModifier = config.getVerticalThresholdModifier();
            horizontalThresholdModifier = config.getHorizontalThresholdModifier();
            allowedHeaderSize = config.getAllowedHeaderSize();
            allowedHeaderIterations = config.getAllowedHeaderIterations();
        } else {
            LOGGER.warning("No configuration file detected or the path is incorrect! Working with default installation locations!");
            System.out.println("No configuration file detected or the path is incorrect! Working with default installation locations!");
            pathToConfigFile = null;
            pathToImageMagick = "/usr/bin/convert";
            pathToTesseract = "/usr/bin/tesseract";
            pathToTesseractConfigFile = "/usr/bin/config.txt";
            verticalThresholdModifier = 1.0;
            horizontalThresholdModifier = 2.0;
            allowedHeaderSize = 4;
            allowedHeaderIterations = 3;
        }
    }

    /**
     * This method returns true if the debugging mode has been activated by the user.
     * Debugging mode outputs extra files to help the user in debugging.
     *
     * @param line The commandline as specified by the user (-D)
     * @return True if the mode has been activated. False if not.
     */
    private boolean setDebugging(CommandLine line) {
        return line.hasOption("D");
    }

    /**
     * This method returns true if the rotate Tables option is in the command line.
     *
     * @param line the commandline.
     * @return True if the option R is in the commandline.
     */
    private boolean setRotateTables(CommandLine line) {
        return line.hasOption("R");
    }

    //~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-
    //The getters of this object:
    //Note, some of the getters have no current use but are added to make this class a bit easier to use.

    public boolean getHelp() {
        return help;
    }

    public ArrayList<String> getPubmedIDs() {
        return pubmedIDs;
    }

    public String getWorkspace() {
        return workspace;
    }

    public int getAllowedHeaderSize() {
        return allowedHeaderSize;
    }

    public int getAllowedHeaderIterations() {
        return allowedHeaderIterations;
    }

    public boolean getContainsPDFFiles() {
        return PDFFiles;
    }

    @SuppressWarnings("UnusedDeclaration")          //We want to offer these methods for future use.
    public String getQuery() {
        return query;
    }

    @SuppressWarnings("UnusedDeclaration")          //We want to offer these methods for future use.
    public String getPathToConfigFile() {
        return pathToConfigFile;
    }

    public String getPathToImageMagick() {
        return pathToImageMagick;
    }

    public String getPathToTesseract() {
        return pathToTesseract;
    }

    public String getPathToTesseractConfigFile() {
        return pathToTesseractConfigFile;
    }

    public double getVerticalThresholdModifier() {
        return verticalThresholdModifier;
    }

    public double getHorizontalThresholdModifier() {
        return horizontalThresholdModifier;
    }

    public boolean getDebugging() {
        return debugging;
    }

    public boolean getRotateTables() {
        return rotateTables;
    }

    public double getImageMagickResolution() {
        return imageMagickResolution;
    }
}
