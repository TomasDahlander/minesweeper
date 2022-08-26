/**
 * created by: darja
 * created at: 2022-07-11
 * using: IntelliJ IDEA
 */
public class MineSweeper {
    public static void main(String[] args) {
        try{
            String startingWithBatFile = args[0];
            new Controller(startingWithBatFile.equalsIgnoreCase("run-bat-file"));
        }catch(IndexOutOfBoundsException e){
            new Controller(false);
        }
    }
}
