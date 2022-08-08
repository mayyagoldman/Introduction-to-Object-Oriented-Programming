package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;


public class Shell {
    private static final String CMD_EXIT = "exit";
    private static final String CMD_SHOW_CHARS = "chars";
    private static final String CMD_RENDER = "render";
    private static final String CMD_CHANGE_CONSOLE = "console";
    private static final String CMD_ADD_CHARS = "add";
    private static final String CMD_REMOVE_CHARS = "remove";
    private static final String CMD_CHANGE_RESOLUTION = "res";
    private static final String ERROR_ARG_LEN = "enter 2 valid arguments at most";
    private static final String ERROR_CHAR_RANGE = "Invalid char range";
    private static final String ERROR_RES_CMD = "Invalid resolution command";
    private static final String ERROR_RES_LIM ="reached resolution limits";
    private static final String RES_CHANGE_MSG = "Width set to ";
    private static final String FONT_NAME = "Courier New";
    private static final String OUTPUT_FILENAME = "out.html";
    private static final String INITIAL_CHARS_RANGE = "0-9";
    private static final String ALL = "all";
    private static final String SPACE_STR = "space";
    private static final String UP = "up";
    private static final String DOWN = "down";

    private static final char SPACE = ' ';
    private static final char TILDE = '~';
    private static final char DASH = '-';
    private static final int MIN_PIXELS_PER_CHAR = 2;
    private static final int INITIAL_CHARS_IN_ROW = 64;

    private final int minCharsInRow;
    private final int maxCharsInRow;
    private int charsInRow;
    private final BrightnessImgCharMatcher charMatcher;
    private AsciiOutput output;
    private HtmlAsciiOutput htmlOutput = new HtmlAsciiOutput(OUTPUT_FILENAME, FONT_NAME);
    private ConsoleAsciiOutput consolOutput = new ConsoleAsciiOutput();
    private final Set<Character> charSet = new HashSet<>();


    /**
     * constructor of Shell class
     * @param img The Image that will be worked on
     */
    public Shell(Image img) {
        minCharsInRow = Math.max(1, img.getWidth()/img.getHeight());
        maxCharsInRow = img.getWidth() / MIN_PIXELS_PER_CHAR;
        charsInRow = Math.max(Math.min(INITIAL_CHARS_IN_ROW, maxCharsInRow), minCharsInRow);
        charMatcher = new BrightnessImgCharMatcher(img, FONT_NAME);
        output = htmlOutput;
        addChars(INITIAL_CHARS_RANGE);}

    /**
     * this method transforms user input regarding the range of chars to an array containing first and last chars
     * @param param user's string input
     * @return Char array {first_char, second_char}
     */
    private static char[] parseCharRange(String param) {
        int stringLength = param.length();
        if (stringLength == 1) {return new char[]{param.charAt(0), param.charAt(0)};}
        else if (param.equals(ALL)){return new char[]{SPACE,TILDE};}
        else if (param.equals(SPACE_STR)){return new char[]{SPACE,SPACE};}
        else if ((stringLength == 3 ) && (param.charAt(1) == DASH)) {
            if (param.charAt(0) < param.charAt(2)){return new char[]{param.charAt(0), param.charAt(2)};}
            else {return new char[]{param.charAt(2), param.charAt(0)};}}
        else {return null;}}

    /**
     * this method takes user input regarding the range of chars and adds them to charSet
     * @param s user's string input
     */
    private void addChars(String s) {
        char[] range = parseCharRange(s);
        if(range != null){Stream.iterate(range[0], c -> c <= range[1], c -> (char)((int)c+1)).forEach(charSet::add);}
        else {System.out.println(ERROR_CHAR_RANGE);}}


    /**
     * this method takes user input regarding the range of chars and removes them from charSet
     * @param s user's string input
     */
    private void removeChars(String s) {
        char[] range = parseCharRange(s);
        if(range != null){Stream.iterate(range[0], c->c <= range[1], c -> (char)((int)c+1)).forEach(charSet::remove);}
        else {System.out.println(ERROR_CHAR_RANGE);}}

    /**
     * this method checks that a given value is within the legal range for NumCharsInRow
     * @param NumCharsInRow given value for chars in row
     * @return true if valid or false otherwise
     */
    private boolean checkNumCharsInRowRange(int NumCharsInRow) {
        return NumCharsInRow <= maxCharsInRow && NumCharsInRow >= minCharsInRow;}

    /**
     * this method updates and prints charsInRow according to user's command: up \ down
     * @param s user's command: up \ down
     */
    private void resChange(String s) {
        if (s.equals(UP) || s.equals(DOWN)) {
            int newCharsInRow = charsInRow;
            if (s.equals(UP)) {newCharsInRow = charsInRow * 2;}
            if (s.equals(DOWN)) {newCharsInRow = charsInRow / 2;}
            if (checkNumCharsInRowRange(newCharsInRow)) {
                charsInRow = newCharsInRow;
                System.out.print(RES_CHANGE_MSG); System.out.println(charsInRow);}
            else  {System.out.println(ERROR_RES_LIM);}}
        else {System.out.println(ERROR_RES_CMD);}
    }

    /**
     *  this method prints all chars currently in charSet
     */
    private void showChars() {
        charSet.stream().sorted().forEach(c-> System.out.print(c + " "));System.out.println();}

    /**
     * this method changes default HTML rendering to console rendering
     */
    private void console() {
        output = consolOutput;}
    /**
     * this method creates an HTML file with the ascii version of the user's Image
     */
    private void render() {
        char[][] asciiImage = charMatcher.chooseChars(charsInRow , charSet.toArray(Character[]::new));
        output.output(asciiImage);}

    /**
     * this method mediates chars render console user commands
     * @param command command type
     */
    private void singleArgCommands(String command) {
        if (command.equals(CMD_SHOW_CHARS)) {showChars();}
        else if (command.equals(CMD_RENDER)) {render();}
        else if (command.equals(CMD_CHANGE_CONSOLE)) {console();}
        else {System.out.println(ERROR_ARG_LEN);}}

    /**
     * this method mediates add remove up down user commands
     * @param command command type
     */
    private void dualArgCommands(String command , String param) {
        if (command.equals(CMD_ADD_CHARS)) {addChars(param);}
        else if (command.equals(CMD_REMOVE_CHARS)) {removeChars(param);}
        else if (command.equals(CMD_CHANGE_RESOLUTION)) {resChange(param);}
        else {System.out.println(ERROR_ARG_LEN);}}


    /**
     * this method mediates user commands
     * @param words user input
     */
    private void handleCommands(String[] words) {
        if ( words.length > 2) {System.out.print(ERROR_ARG_LEN);}
        if(words.length > 1) {dualArgCommands(words[0] , words[1]);}
        else {singleArgCommands(words[0]);}}

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(">>> ");
        String cmd = scanner.nextLine().trim();
        String[] words = cmd.split("\\s+");
        while(!words[0].equals(CMD_EXIT)) {
            if(!words[0].equals("")) {handleCommands(words);}
            System.out.print(">>> ");
            cmd = scanner.nextLine().trim();
            words = cmd.split("\\s+");}}
}
