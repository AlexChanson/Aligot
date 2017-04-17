package demo;

import fsm.FiniteStateMachine;
import fsm.State;

/**
 * Created by ben on 11/04/17.
 */
public class FSMDemo {

    public static class PlayerState extends State{
        private String playerName;

        public PlayerState(String playerName){
            this.playerName = playerName;
        }


        @Override
        public void onEnter(){
            System.out.println("< "+this.playerName+" turn: >");
        }

        @Override
        public void onExit(){
            System.out.println("< End of "+playerName+" turn.>");
        }

        @Override
        public String onUpdate() {
            System.out.println(this.playerName+" is playing.");

            return "simulation";
        }

        @Override
        public String getStateName() {
            return "player";
        }
    }

    public static class SimulationState extends State{
        private int turnCount;

        public SimulationState(){
            turnCount = 0;
        }


        @Override
        public void onEnter(){
            System.out.println("< beginning simulation >");
        }

        @Override
        public void onExit(){
            turnCount++;
            System.out.println("< End of simulation >");
        }

        @Override
        public String onUpdate() {
            System.out.println("simulating physics");

            if ( turnCount <= 1){
                return "player";
            }

            return "end";
        }

        @Override
        public String getStateName() {
            return "simulation";
        }
    }

    public static class EndState extends State{

        public EndState(){
            finalState = true;
        }


        @Override
        public void onEnter(){
            System.out.println("< stopping game >");
        }

        @Override
        public String onUpdate() {
            System.out.println(" printing scores ");
            return "end";
        }

        @Override
        public String getStateName() {
            return "end";
        }
    }

    public static void main(String[] args){



        FiniteStateMachine fsm = new FiniteStateMachine();
        fsm.addState(new PlayerState("Ben"));
        fsm.addState(new SimulationState());
        fsm.addState(new EndState());
        fsm.setState("player");

        System.out.println(fsm.toString());

        Boolean end = false;
        while ( !end ){
            end = fsm.update();
            System.out.println();
        }


    }
}
