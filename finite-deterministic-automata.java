package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class p1_problem1_9731074 {

    public static void main(String[] args) {


        /**
         * ArrayLists to  keep alphabet and states and final states in nfa separately
         */

        ArrayList alphabet = new ArrayList();

        ArrayList states = new ArrayList();

        ArrayList FinalStates = new ArrayList();

        /*
        * temp to hold lines in txt file
        * */

        ArrayList temp = new ArrayList<String>();

        /*
        * ArrayList to keep transitions in nfa
        * */

        ArrayList transitionFile = new ArrayList();

        String firstState = "";


        try {
            File myObj = new File("src\\code\\DFA_Input_1.txt");
            Scanner myReader = new Scanner(myObj);
            int counter = 0;
            while (myReader.hasNextLine()) {

                counter++;


                String data = myReader.nextLine();

                /**
                 * add each line of txt file to temp arraylist
                 */

                temp.add(data);

            }

            /*
            * iterate trough each element of arraylist that contains each line of txt file
            * */


            for (int j = 0; j < temp.size(); j++) {

                switch (j) {

                    /*
                    * first line include alphabet so we split it by space and keep them in alphabet arraylist
                    * */

                    case 0:

                        String[] split_alphabet = temp.get(j).toString().split(" ");
                        for (int i = 0; i < split_alphabet.length; i++) {

                            alphabet.add(split_alphabet[i]);

                        }
                        break;

                        /*
                        * line 2 include all states so we split line 2 by space and them to the states arraylist to keep them
                        * */

                    case 1:

                        String[] split_states = temp.get(j).toString().split(" ");
                        for (int i = 0; i < split_states.length; i++) {

                            states.add(split_states[i]);

                        }
                        break;

                        /*
                        * line 3 include first state so we hold in first  state variable
                        * */

                    case 2:

                        String split_Final = temp.get(j).toString();
                        firstState = split_Final;

                        break;


                        /*
                        * line 4 include final states so we split them by space and add them to the final states arraylist to keep them
                        * */

                    case 3:

                        String[] split_FinalStates = temp.get(j).toString().split(" ");
                        for (int i = 0; i < split_FinalStates.length; i++) {

                            FinalStates.add(split_FinalStates[i]);

                        }
                        break;

                        /*
                        * remain lines include transitions in nfa so we add them to transition arraylist
                        * */

                    default:


                        String split_transition = temp.get(j).toString();

                        transitionFile.add(split_transition);


                        break;


                }
            }


            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        /*
        * Transition Arraylist contains  HashMap for each alphebet and  for example in index 0 of this arraylist
        is a HashMap that contains transitions for index 0 of alphabet
         masalan dar index 0 transitionhayee ast ke be ezaye onsore aval araye alephba anjam migirad
   * */


/*in this step we initialize Transition arraylist
* */
        ArrayList Transitions = new ArrayList<HashMap>();

        for (int i = 0; i < alphabet.size(); i++) {
            HashMap<String, String> temp1 = new HashMap<String, String>();
            Transitions.add(temp1);
        }

        /*
        in this step we add transitions to their   Respective HashMap
         */

        for (int i = 0; i < transitionFile.size(); i++) {

            String test = transitionFile.get(i).toString();
            String[] splitedTrans = test.split(" ");
            String alephba = splitedTrans[1];
            int index = alphabet.indexOf(alephba);
            HashMap temp2 = (HashMap) Transitions.get(index);
            temp2.put(splitedTrans[0], splitedTrans[2]);

        }

        /*
        * in this step we get string from input then iterate through the string from the first state
        * of machine and  then at the end if we stay in final state we say the machine accept the string
        * and if we stay in non final state we say the machine not accepted the string
        * */


        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        String TempState = firstState;

        for (int i = 0; i < input.length(); i++) {
            char alph = input.charAt(i);
            String alph1 = Character.toString(alph);
            int num = alphabet.indexOf(alph1);
            HashMap transitions = (HashMap) Transitions.get(num);
            TempState = (String) transitions.get(TempState);

        }

        if (FinalStates.contains(TempState)) {
            System.out.println("accepted :))");
        } else System.out.println("not accepted :((");


    }
}




