package paint.View;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class PanneauOutils extends JPanel {
    /**
     * Map des boutons par mode de dessin.
     */
    private Map<String, JButton> modeButtons;

    /**
     * Constructeur pour initialiser le panneau d'outils.
     *
     * @param pan le panneau de dessin auquel les outils sont associés.
     */
    public PanneauOutils(PanneauDessin pan) {
        super();
        this.setLayout(new GridLayout(1, 10));

        modeButtons = new HashMap<>(); // Initialisation de la Map

        // Création des boutons avec des actions associées
        JButton resetButton = createIconButton("assets/reset.png", e -> pan.resetCanvas());
        JButton rubberButton = createModeButton("Gomme", "assets/eraser-solid-24.png", pan);
        JButton freeHandButton = createModeButton("FreeHand", "assets/pen.png", pan);
        JButton circleButton = createModeButton("Cercle", "assets/circle-regular-24.png", pan);
        JButton rectangleButton = createModeButton("Rectangle", "assets/rectangle-regular-24.png", pan);
        JButton triangleButton = createModeButton("Triangle", "assets/triangle.png", pan);
        JButton lineButton = createModeButton("Ligne", "assets/line.png", pan);

        JButton colorButton = createIconButton("assets/palette.png", e -> {
            Color color = JColorChooser.showDialog(null, "Choose a color", Color.BLACK);
            if (color != null) {
                pan.setColor(color);
            }
        });

        JButton saveButton = createIconButton("assets/save-solid-24.png", e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Project");
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                pan.sauvegarderProjet(file);
            }
        });

        JButton loadButton = createIconButton("assets/folder-open-solid-24.png", e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Load Project");
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                pan.chargerProjet(file);
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
        this.add(saveButton);
        this.add(loadButton);

        // Mise à jour de l'état initial
        updateActiveButton(pan.getForme());
    }

    /**
     * Méthode pour créer un bouton de mode spécifique.
     *
     * @param mode     le mode de dessin associé au bouton.
     * @param iconPath le chemin de l'icône à utiliser pour le bouton.
     * @param pan      le panneau de dessin.
     * @return un bouton JButton configuré pour le mode.
     */
    private JButton createModeButton(String mode, String iconPath, PanneauDessin pan) {
        JButton button = createIconButton(iconPath, e -> {
            pan.setForme(mode);
            updateActiveButton(mode); // Mettre à jour l'apparence des boutons
        });
        modeButtons.put(mode, button); // Ajouter à la Map
        return button;
    }

    /**
     * Méthode utilitaire pour créer un bouton avec une icône et une action.
     *
     * @param iconPath le chemin de l'icône à utiliser pour le bouton.
     * @param action   l'action associée au clic sur le bouton.
     * @return un bouton JButton configuré.
     */
    private JButton createIconButton(String iconPath, ActionListener action) {
        JButton button = new JButton();
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledImage = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
        button.addActionListener(action);

        // Styles de base
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);

        return button;
    }

    /**
     * Met à jour l'apparence du bouton actif pour refléter le mode sélectionné.
     *
     * @param activeMode le mode actuellement actif.
     */
    private void updateActiveButton(String activeMode) {
        for (Map.Entry<String, JButton> entry : modeButtons.entrySet()) {
            JButton button = entry.getValue();
            if (entry.getKey().equals(activeMode)) {
                // Style du bouton actif
                button.setBackground(new Color(200, 200, 200)); // Couleur grise
                button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            } else {
                // Style des autres boutons
                button.setBackground(Color.WHITE);
                button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            }
        }
    }
}
