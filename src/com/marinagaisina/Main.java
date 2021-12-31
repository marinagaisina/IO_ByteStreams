package com.marinagaisina;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*       This program has locations and destinations files from "colossal cave adventure game"!
         -Change the program to allow players to type full words, or phrases, then move to the correct location based upon their input.
         -The player should be able to type commands such as "Go West", "run South", or just "East" and the program will move to the appropriate location if there is one.
         -As at present, an attempt to move in an invalid direction should print a message and remain in the same place.
         -Single letter commands (N, W, S, E, Q) should still be available.
 */

public class  Main {
    //private static Map<Integer, Location> locations = new HashMap<>();

    private static Locations locations = new Locations();

    public static void main(String[] args) throws IOException {

        try (DataOutputStream locFile = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("locations.dat")))) {
            for (Location location : locations.values()) {
                locFile.writeInt(location.getLocationID());
                locFile.writeUTF(location.getDescription());
                System.out.println("Writing location "+ location.getLocationID()+": "+location.getDescription());
                System.out.println("Writing "+ (location.getExits().size()-1)+ " exits.");
                locFile.writeInt(location.getExits().size() -1);
                for (String direction : location.getExits().keySet()) {
                    if (!direction.equalsIgnoreCase("Q")) {
                        System.out.println("\t\t"+direction+","+location.getExits().get(direction));
                        locFile.writeUTF(direction);
                        locFile.writeInt(location.getExits().get(direction));
                    }
                }
            }
        }

        Scanner scanner = new Scanner(System.in);

        Map<String, String> vocabulary = new HashMap<>();
        vocabulary.put("QUIT", "Q");
        vocabulary.put("NORTH", "N");
        vocabulary.put("SOUTH", "S");
        vocabulary.put("WEST", "W");
        vocabulary.put("EAST", "E");

        //int loc = 1;
        int loc = 64;
        while(true) {
            System.out.println(locations.get(loc).getDescription());
            if(loc == 0) {
                break;
            }

            Map<String, Integer> exits = locations.get(loc).getExits();
            System.out.print("Available exits are ");
            for(String exit: exits.keySet()) {
                System.out.print(exit + ", ");
            }
            System.out.println();

            String direction = scanner.nextLine().toUpperCase();
            if(direction.length() > 1) {
                String[] words = direction.split(" ");
                for(String word: words) {
                    if(vocabulary.containsKey(word)) {
                        direction = vocabulary.get(word);
                        break;
                    }
                }
            }

            if(exits.containsKey(direction)) {
                loc = exits.get(direction);

            } else {
                System.out.println("You cannot go in that direction");
            }
        }

    }
}
