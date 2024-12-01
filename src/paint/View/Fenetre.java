/**
 * Classe principale pour afficher une fenêtre Swing avec un panneau personnalisé.
 * Cette classe configure et affiche une fenêtre principale dimensionnée en fonction de la taille de l'écran,
 * et ajoute un panneau principal (`MainPannel`).
 *
 * @author Baptiste Borie
 */
package paint.View;

import java.awt.*;
import javax.swing.*;

public class Fenetre {

    /**
     * Point d'entrée de l'application.
     * Configure la fenêtre principale (`JFrame`), ajuste ses dimensions à deux
     * tiers de la taille de l'écran,
     * ajoute un panneau personnalisé et rend la fenêtre visible.
     *
     * @param args Arguments de la ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
        JFrame fen = new JFrame("Paint");

        // Calcul des dimensions de la fenêtre en fonction de la taille de l'écran
        Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
        int longueur = tailleMoniteur.width * 2 / 3;
        int hauteur = tailleMoniteur.height * 2 / 3;

        // Création et ajout du panneau principal
        MainPannel pan = new MainPannel();
        fen.add(pan);

        // Configuration de la fenêtre
        fen.pack();
        fen.setVisible(true);
        fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fen.setSize(longueur, hauteur);
    }
}
