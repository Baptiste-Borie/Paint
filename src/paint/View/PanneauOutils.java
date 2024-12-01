package paint.View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanneauOutils extends JPanel {

    public PanneauOutils(PanneauDessin pan) {
        super();

        this.setLayout(new GridLayout(2, 4)); // Ajusté à 6 boutons

        // Création des boutons avec des actions associées
        JButton resetButton = createButton("Reset", e -> pan.resetCanvas());
        JButton rubberButton = createButton("Gomme", e -> pan.setForme("Gomme"));
        JButton freeHandButton = createButton("FreeHand", e -> pan.setForme("FreeHand"));
        JButton circleButton = createButton("Cercle", e -> pan.setForme("Cercle"));
        JButton rectangleButton = createButton("Rectangle", e -> pan.setForme("Rectangle"));
        JButton triangleButton = createButton("Triangle", e -> pan.setForme("Triangle"));
        JButton lineButton = createButton("Ligne", e -> pan.setForme("Ligne"));
        JButton colorButton = createButton("Color", e -> {
            Color color = JColorChooser.showDialog(null, "Choose a color", Color.BLACK);
            if (color != null) {
                pan.setColor(color);
            }
        });

        // Ajout des boutons au panneau
        this.add(resetButton);
        this.add(rubberButton);
        this.add(freeHandButton);
        this.add(rectangleButton);
        this.add(circleButton);
        this.add(triangleButton);
        this.add(lineButton);
        this.add(colorButton);
    }

    /**
     * Méthode utilitaire pour créer un bouton avec une action spécifique.
     */
    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }
}
