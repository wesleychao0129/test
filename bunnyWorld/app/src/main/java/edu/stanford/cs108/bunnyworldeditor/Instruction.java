package edu.stanford.cs108.bunnyworldeditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Instruction {
    public String onDropWithShape;
    private Map<String, ArrayList<Action>> instructions = new HashMap<>();

    public static final String ON_ENTER = "on enter";
    public static final String ON_DROP = "on drop";
    public static final String ON_CLICK = "on click";
    public static final String GOTO = "goto";
    public static final String PLAY = "play";
    public static final String HIDE = "hide";
    public static final String SHOW = "show";



    Instruction(String script) {
        for (String instruction : script.split(";")) {
            instruction = instruction.trim();
            String[] words = instruction.split(" ");
            if (instruction.startsWith(ON_ENTER)) {
                ArrayList<Action> actions = new ArrayList<>();
                for (int i = 2; i < words.length; i += 2) {
                    Action newAction = new Action(words[i], words[i + 1]);
                    actions.add(newAction);
                }
                instructions.put(ON_ENTER, actions);
            } else if (instruction.startsWith(ON_CLICK)) {
                ArrayList<Action> actions = new ArrayList<>();
                for (int i = 2; i < words.length; i += 2) {
                    Action newAction = new Action(words[i], words[i + 1]);
                    actions.add(newAction);
                }
                instructions.put(ON_CLICK, actions);
            } else if (instruction.startsWith(ON_DROP)) {
                ArrayList<Action> actions = new ArrayList<>();
                for (int i = 3; i < words.length; i += 2) {
                    Action newAction = new Action(words[i], words[i + 1]);
                    actions.add(newAction);
                }
                onDropWithShape = words[2];
                instructions.put(ON_DROP, actions);
            }
        }
    }

    public Map<String, ArrayList<Action>> getMapping() {
        return instructions;
    }

    public class Action {
        public String actionType;
        public String pageName;
        public String soundName;
        public String shapeName;

        Action(String actionType, String modifier) {
            this.actionType = actionType;
            if (actionType.equals(GOTO)) {
                pageName = modifier;
            } else if (actionType.equals(PLAY)) {
                soundName = modifier;
            } else if (actionType.equals(HIDE) || actionType.equals(SHOW)) {
                shapeName = modifier;
            }
        }
    }
}