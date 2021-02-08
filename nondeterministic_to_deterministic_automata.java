package code;

import org.apache.commons.collections.map.MultiValueMap;

import java.io.*;
import java.util.*;

public class p1_problem2_9731074 {


    public static void main(String[] args) {

        /*ArrayLists to  keep alphabet and states and final states in nfa separately*/

        ArrayList alphabet = new ArrayList();

        ArrayList states = new ArrayList();

        ArrayList FinalStates = new ArrayList();

        /*

        *temp to hold lines in txt file
         */

        ArrayList temp = new ArrayList<String>();

        /*

        *ArrayList to keep transitions in nfa
         */

        ArrayList transitionFile = new ArrayList();

        String firstState = "";

        String lambda = "λ";


        File myObj = new File("src\\code\\NFA_Input_2.txt");
        Scanner myReader = null;
        int counter = 0;
        try {
            myReader = new Scanner(myObj);


            while (myReader.hasNextLine()) {

                counter++;


                String data = myReader.nextLine();

                /*
                * add each line of txt file to temp arraylist
                * */

                temp.add(data);

            }
        } catch (FileNotFoundException e) {

            System.out.println("File Not Found");
        }
        myReader.close();

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
                    alphabet.add(lambda);

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

                    String split_First = temp.get(j).toString();
                    firstState = split_First;

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

        /*
        * lambdatransition is a arraylist of arraylist to hold lambda transition of each state for example first element of
       this arraylist contain lambda transition of first element of alphabet arraylist
        */


        /**
         * Transition Arraylist contains  MultiValueMap for each alphebet and  for example in index 0 of this arraylist
         *         is a MultiValueMap that contains transitions for index 0 of alphabet
         *          masalan dar index 0 transitionhayee ast ke be ezaye onsore aval araye alephba anjam migirad
         */


        ArrayList lambadaTransition = new ArrayList<ArrayList>();
        ArrayList Transitions = new ArrayList<MultiValueMap>();

        /*
        * in this step we initialize Transition arraylist and lambdaTransition arraylist
        * */


        for (int i = 0; i < alphabet.size(); i++) {

            MultiValueMap temp1 = new MultiValueMap();
            Transitions.add(temp1);
        }

        for (int i = 0; i < states.size(); i++) {
            ArrayList temp2 = new ArrayList();
            lambadaTransition.add(temp2);
        }

        /*
        * in this step we add transitions to their   Respective MultiValueMap
        * */


        for (int i = 0; i < transitionFile.size(); i++) {

            String test = transitionFile.get(i).toString();
            String[] splitedTrans = test.split(" ");
            String alephba = splitedTrans[1];

            int index = alphabet.indexOf(alephba);
            MultiValueMap temp2 = (MultiValueMap) Transitions.get(index);
            temp2.put(splitedTrans[0], splitedTrans[2]);

        }

        /*
        * tempLambda is a  MultiValueMap that contains lambda transitons of states
        * */


        MultiValueMap tempLambda = (MultiValueMap) Transitions.get(Transitions.size() - 1);

        /*
        *
        * add each state to lambda transition of that state
        * */


        for (int i = 0; i < states.size(); i++) {

            tempLambda.put(states.get(i), states.get(i));

        }

        /*
        * in this step we separate each lambda transition of each state and add them to separate arraylist
        * */


        Iterator iterator = tempLambda.entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry mapElement = (Map.Entry) iterator.next();
            String key = (String) mapElement.getKey();
            int index = states.indexOf(key);
            ArrayList tempClouser = (ArrayList) lambadaTransition.get(index);
            ArrayList lambdaOfState = (ArrayList) mapElement.getValue();
            for (int i = 0; i < lambdaOfState.size(); i++) {
                tempClouser.add(lambdaOfState.get(i));

            }


        }

        /**
         * in this step for example if q1 is in lambda clouser of q0 then I add lambda clouser of q1 to lambda clouser
         * of q0 because if we can go by lambda from q0 to q1 and we can go from q1 to q2 by lambda
         * so we can go from q0 to q2 by lambda
         *
         */


        for (int i = 0; i <lambadaTransition.size() ; i++) {

            ArrayList templambda1= (ArrayList) lambadaTransition.get(i);

            for (int j = 0; j <templambda1.size() ; j++) {
                if (templambda1.get(j)!=states.get(i)){

                    int index=states.indexOf(templambda1.get(j));
                    ArrayList templambda2= (ArrayList) lambadaTransition.get(index);

                    for (int k = 0; k <templambda2.size() ; k++) {
                        if (!templambda1.contains(templambda2.get(k))){

                            templambda1.add(templambda2.get(k));

                        }
                    }

                }
            }
        }


        /*
        * new first state of dfa is lambda clouser of first state of nfa
        * */


        String newFirstSate = "";
        ArrayList firstLambda = (ArrayList) lambadaTransition.get(0);
        for (int i = 0; i < firstLambda.size(); i++) {


            newFirstSate = newFirstSate + "-" + firstLambda.get(i);

        }

        /*
        * make arraylist to keep new states and new transitions and new final states of dfa
        * */


        ArrayList newStates = new ArrayList();
        ArrayList newStatesTemp = new ArrayList();
        ArrayList newTransitions = new ArrayList<HashMap>();
        ArrayList newFinalStates = new ArrayList();


        /*
        * initialize  new transition
        * */

        for (int i = 0; i < alphabet.size() - 1; i++) {
            HashMap hashMap = new HashMap();
            newTransitions.add(hashMap);
        }

        /**
         *      i make newStatesTemp arraylist to keep them and make new states by effecting alphabet on them and remove each states
         *         that we effect alphabet on it
         *         but new states keep all new states
         */

        newStates.add(newFirstSate);
        newStatesTemp.add(newFirstSate);


        /*
        * check if a new first is a final state or not
        * */


        String[] new2 = newFirstSate.split("-");
        ArrayList<String> newTemp2 = new ArrayList<String>(Arrays.asList(new2));
        boolean isFinal = false;


        for (int i = 0; i < FinalStates.size(); i++) {
            if (newTemp2.contains(FinalStates.get(i))) {
                isFinal = true;
            }
        }

        String tempnew = "";

        for (int i = 0; i < newTemp2.size(); i++) {
            tempnew += newTemp2.get(i);
        }

        if (isFinal) {
            newFinalStates.add(tempnew);
        }


      /*
      *   in this step first we effect alphabet on new first state and find some new states then we effect alphabet on
        new states until no new states found
      * */


        while (newStatesTemp.size() != 0) {

            /*
            * because i separate new statates by dash(-) so i split it to effect alphabet easily
            * */


            String[] temp1 = newStatesTemp.get(0).toString().split("-");

            String FindNew = "";
            ArrayList findNew = new ArrayList();

            for (int j = 0; j < alphabet.size() - 1; j++) {
                FindNew = "";


                for (int k = 0; k < temp1.length; k++) {


                    String state = (temp1[k]);

                  /* check to this string is not empty
                    and fill find new string by states that we can  achieve from that state and seperate name
                    of state by dash(-)

                    */

                    if (!state.isEmpty()) {


                        MultiValueMap multiValueMap = (MultiValueMap) Transitions.get(j);
                        if (multiValueMap.containsKey(state)) {


                            List list = (List) multiValueMap.getCollection(state);


                            for (int l = 0; l < list.size(); l++) {
                                if (!FindNew.contains((CharSequence) list.get(l))) {


                                    FindNew = FindNew + "-" + list.get(l);
                                }

                            }
                        }

                    }


                }

                /*
                * this flag is to check that the new state tha we find has been found before or not
                * */


                boolean flag = false;
                String[] new1 = FindNew.split("-");
                ArrayList<String> newTemp = new ArrayList<String>(Arrays.asList(new1));
                String FindNew2 = "";

               /*

                in this step effect lambda clouser of each state of new state because by lambda we can go to some states that
                we dont consider them before
                yani ma tahesh azash landa clouser migirim chon dar vaghe dar in statehayee ke hastim dar landa clouser anha
                ham hastim

                */



                for (int i = 0; i < newTemp.size(); i++) {

                    if (!newTemp.get(i).isEmpty()) {

                        int index = states.indexOf(newTemp.get(i));
                        ArrayList lambdaofThis = (ArrayList) lambadaTransition.get(index);

                        for (int k = 0; k < lambdaofThis.size(); k++) {
                            if (!FindNew2.contains((CharSequence) lambdaofThis.get(k))) {


                                FindNew2 = FindNew2 + "-" + lambdaofThis.get(k);


                            }

                        }
                    }

                }

                /*
                * in this step we check that this new state is a final state or not
                * */


                String[] new3 = FindNew2.split("-");
                ArrayList<String> newTemp3 = new ArrayList<String>(Arrays.asList(new3));
                boolean isFinal1 = false;


                for (int i = 0; i < FinalStates.size(); i++) {
                    if (newTemp3.contains(FinalStates.get(i))) {
                        isFinal1 = true;
                    }
                }


                String[] temp3 = FindNew2.split("-");
                String state = "";
                for (int k = 0; k < temp3.length; k++) {
                    state += temp3[k];
                }
                FindNew2 = state;


                if (!newFinalStates.contains(FindNew2)) {
                    newFinalStates.add(FindNew2);
                }

                /*
                * in this step sort new step because has been messed up before
                * */


                for (int i = 0; i < newStatesTemp.size(); i++) {
                    String[] temp2 = newStatesTemp.get(i).toString().split("-");
                    ArrayList<String> tempp2 = new ArrayList<String>(Arrays.asList(temp2));

                    Collections.sort(tempp2);
                    Collections.sort(newTemp3);
                    if (tempp2.equals(newTemp3)) {
                        flag = true;
                    }
                }
                FindNew2 = "";


                for (int i = 0; i < newTemp3.size(); i++) {
                    FindNew2 = FindNew2 + "-" + newTemp3.get(i);
                }

                /*
                * check if this state han not been found before and add it to new states arraylist
                * */


                if (flag == false && !newStatesTemp.contains(FindNew2) && !newStates.contains(FindNew2)) {


                    newStatesTemp.add(FindNew2);
                    newStates.add(FindNew2);

                }

              /*
                in this step fill out the  new transitions that contain hash map for each alphabet
                I split the states to  stick together

                */


                HashMap DFATrans = (HashMap) newTransitions.get(j);
                String nowState = "";
                for (int i = 0; i < temp1.length; i++) {
                    nowState = nowState + temp1[i];
                }


                String[] temp2 = FindNew2.split("-");
                String state1 = "";
                for (int k = 0; k < temp2.length; k++) {
                    state1 += temp2[k];
                }
                FindNew2 = state1;

                DFATrans.put(nowState, FindNew2);

            }


            newStatesTemp.remove(newStatesTemp.get(0));

        }


        FileWriter myWriter = null;

        try {
            myWriter = new FileWriter("src\\code\\DFA_Output_2.txt");


        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        try {


            String alphabetFile = "";

            for (int i = 0; i < alphabet.size(); i++) {
                if (alphabet.get(i) != "λ") {
                    alphabetFile += alphabet.get(i) + " ";
                    System.out.print(alphabet.get(i) + " ");
                }
            }
            System.out.println();
            myWriter.write(alphabetFile);
            myWriter.write("\r\n");


            for (int i = 0; i < newStates.size(); i++) {

                String[] temp2 = newStates.get(i).toString().split("-");
                String state = "";
                for (int j = 0; j < temp2.length; j++) {
                    state += temp2[j];
                }
                newStates.set(i, state);

            }


            /*
            *
            * and finally we show the information of new dfa
            * */
            String FileSates = "";

            for (int i = 0; i < newStates.size(); i++) {
                System.out.print(newStates.get(i) + " ");
                FileSates += newStates.get(i) + " ";
            }

            System.out.println();
            myWriter.write(FileSates);
            myWriter.write("\r\n");

            System.out.println(newStates.get(0));
            myWriter.write((String) newStates.get(0));
            myWriter.write("\r\n");


            String FileFinalState = "";

            for (int i = 0; i < newFinalStates.size(); i++) {

                System.out.print(newFinalStates.get(i) + " ");
                FileFinalState += newFinalStates.get(i) + " ";
            }

            System.out.println();
            myWriter.write(FileFinalState);
            myWriter.write("\r\n");

            String FileTrans = "";


            for (int i = 0; i < newTransitions.size(); i++) {

                HashMap trans = (HashMap) newTransitions.get(i);


                Iterator iterator1 = trans.entrySet().iterator();
                while (iterator1.hasNext()) {

                    Map.Entry mapElement = (Map.Entry) iterator1.next();
                    String key = (String) mapElement.getKey();
                    String value = (String) mapElement.getValue();
                    System.out.println(key + " " + alphabet.get(i) + " " + value);
                    FileTrans += key + " " + alphabet.get(i) + " " + value;
                    myWriter.write(FileTrans);
                    myWriter.write("\r\n");
                     FileTrans = "";


                }


            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


        try {
            myWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }



    }
}
