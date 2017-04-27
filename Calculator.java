

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by andrewnyaisonga on 2/26/17.
 */
public class Calculator {
    private Scanner sc1;
    public ArrayList<String> operator = new ArrayList<>();
    public ArrayList<String> infix = new ArrayList<>();
    public ArrayList<String> postfix = new ArrayList<>();
    public Stacks<String> stack = new Stacks();



    Calculator() {
        infix = null;
        postfix = null;
        operator.add("*");
        operator.add("/");
        operator.add("+");
        operator.add("-");
        operator.add("(");
        operator.add(">");
        operator.add("<");
        operator.add("=");
        operator.add("!");
        operator.add("&");
        operator.add("|");
    }


    public void read() throws IOException {     //Readingfile
        sc1 = new Scanner(System.in);
        System.out.println("Enter the location files: I will ask for the input file then output file");
        System.out.println();
        System.out.println("Enter the location of the input file: ");
        String location = sc1.next();
        System.out.println("Output file Location: ");
        String output = sc1.next();
        FileReader reader = new FileReader(String.valueOf(location));
        BufferedReader bufferedReader = new BufferedReader(reader);
        ArrayList<Double> array = new ArrayList<>();
        String s = " ";
        //Reading from the file
        while(s != null){
            array.add(postFixCalulator(convertPostFix(s)));
            System.out.println(postFixCalulator(convertPostFix(s)));  //To see output for me on the console
            s = bufferedReader.readLine();
        }

        FileWriter writer = new FileWriter(output);
        BufferedWriter write = new BufferedWriter(writer);
        //Writing to the file
        try{
            for(int i=1;i<array.size();i++){
                write.write(array.get(i) + "\n");
            }
        } catch (IOException e) {
                System.out.println("Can't locate the Output file, Is it on the same place with this JAR file?");
        }
        write.close();
    }
    
    public Queue convertPostFix(String line){ //sentence from everyLine
        ArrayList<String> processedList = new ArrayList<>();
        if (!line.isEmpty()) {
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens()) {
                processedList.add(st.nextToken());
            }
        }
        Queue<String> queue = new Queue();
        for (int i = 0; i < processedList.size(); ++i) {
            String value = processedList.get(i);
            if (value.equals("(")) { //First if you see the bracket then just put it
                stack.push("(");
            }
            else if (value.equals(")")) {      //If you see ) pop all of them then enqueue them
                String operation = stack.peek();
                while (!operation.equals("(") && !(stack.isEmpty())) {
                    stack.pop();
                    queue.enqueue(operation);
                    if (!stack.isEmpty()) {
                        operation = stack.peek();
                    }
                }
                stack.pop();
            }
            else if (value.equals("+") ||value.equals("-")) { //Operation with same priority
                if (stack.isEmpty()) {
                    stack.push(value);
                }
                else {
                    String operation = stack.peek();
                    while (!(stack.isEmpty() || operation.equals(("(")) || operation.equals((")")))) {
                        operation = stack.pop();
                        queue.enqueue(operation);
                    }
                    stack.push(value);
                }
            }
            else if (value.equals("*") || value.equals("/")) {
                if (stack.isEmpty()) {
                    stack.push(value);
                }
                else {
                    String operation = stack.peek();
                    while (!operation.equals(("(")) && !operation.equals(("+")) && !operation.equals(("-")) && !stack.isEmpty()) {
                        operation = stack.pop();
                        queue.enqueue(operation);
                    }
                    stack.push(value);
                }
            }
            else if(value.equals(">") || value.equals("<")){
                stack.push(value);
            }
            else if(value.equals("!") || value.equals("&") || value.equals("=") || value.equals("|")){
                stack.push(value);
            }
            else {
                queue.enqueue(value);
            }

        }

        while (!stack.isEmpty()) {
            String operation = stack.peek();
            if (!operation.equals("(")) {
                stack.pop();
                queue.enqueue(operation);
            }
        }
        Queue<String> temp = new Queue<>();
        while(queue.isEmpty()==false){
            String d = queue.dequeue();
            if(!d.equals("(") && !d.equals(" ")){
                temp.enqueue(d);
            }
        }

//        temp.printQueue();     //If you want to see the postFix
        return temp;
    }
    public double postFixCalulator(Queue<String> evaluted) { //Evaluated the postfix notations
        Stacks<Double> newStack = new Stacks<>();
        while (evaluted.isEmpty()==false) {
            String operation = evaluted.dequeue();
            if (operator.contains(operation)) {
                if(operation.equals("!")){
                    double one = newStack.pop();
                    if(one == 1){
                        if(evaluted.isEmpty()){
                            return 0;
                        }
                        else{
                            newStack.push(0.0);
                        }
                    }
                    else{
                        if(evaluted.isEmpty()){
                            return 1;
                        }
                        else{
                            newStack.push(1.0);
                        }
                    }
                }
                else {
                    double one = newStack.pop();
                    double two = newStack.pop();
                    Double answer = calculation(two, one, operation);
                    if(evaluted.isEmpty()){
                        return answer;
                    }
                    else{
                        newStack.push(answer);
                    }
                }


            }
            else {
                Double pushed = Double.parseDouble(operation);
                newStack.push(pushed);
            }
        }
        return 0;
    }

    public double calculation(double x , double y, String operatoring){

        switch(operatoring){
            case "+": return (x + y);
            case "-": return (x - y);
            case "/": return (x/y);
            case "*": return (x*y);
            case ">": if(x>y){

                        return 1;
                    } else{
                       return 0;
                    }
            case"<":  if(x<y){
                        return 1;
                     }
                    else{
                        return 0;
                     }
            case "=": if(x==y) {
                        return 1;
                        }
                        else{
                            return 0;
                         }
            case "&": if(x == y && x==1){
                            return 1;
                        }
                        else{
                            return 0;
                         }
            case "|": if(x == 1 || y == 1){
                            return 1;
                        }
                        else{
                            return 0;
                         }
        }
        return 0;
    }

    public static void main(String[] args) throws IOException {
            Calculator calculator= new Calculator();
            calculator.read();
    }


}
