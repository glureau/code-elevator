package elevator.client.ui;

import elevator.client.Direction;
import elevator.client.Elevator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayDeque;
import java.util.Deque;

import static elevator.client.Direction.DOWN;
import static elevator.client.Direction.UP;
import static elevator.client.ElevatorState.OPEN;

public class InteractionPanel extends JPanel {

    private final Deque<JLabel> elevatorStack;
    private final Elevator elevator;

    InteractionPanel(Elevator elevator) {
        this.elevator = elevator;
        GridLayout layout = new GridLayout(0, 3);
        setLayout(layout);

        int maxLevel = 5;
        elevatorStack = new ArrayDeque<>(maxLevel);

        for (int i = maxLevel; i >= 0; i--) {
            if (i != maxLevel) {
                add(new JButton(new CallElevatorAction(elevator, i, UP)));
            } else {
                add(new JLabel());
            }
            if (i != 0) {
                add(new JButton(new CallElevatorAction(elevator, i, DOWN)));
            } else {
                add(new JLabel());
            }
            elevatorStack.addFirst(new JLabel(String.valueOf(i)));
            add(elevatorStack.getFirst());
            if (i == 0) {
                elevatorStack.getFirst().setText("[ | ]");
            }
        }
    }

    public InteractionPanel update() {
        Integer i = 0;
        for (JLabel jLabel : elevatorStack) {
            if (elevator.floor().equals(i)) {
                jLabel.setText(elevator.state().equals(OPEN) ? "[| |]" : "[ | ]");
            } else {
                jLabel.setText(i.toString());
            }
            i++;
        }
        return this;
    }

    private static class CallElevatorAction extends AbstractAction {

        private final Elevator elevator;
        private final int currentFloor;
        private final Direction direction;

        public CallElevatorAction(Elevator elevator, int currentFloor, Direction direction) {
            this.elevator = elevator;
            this.currentFloor = currentFloor;
            this.direction = direction;
            this.putValue(NAME, direction.toString());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            elevator.call(currentFloor, direction);
        }

    }

}
