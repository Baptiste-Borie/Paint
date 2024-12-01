/**
 * Classe représentant le panneau principal de l'application.
 * Ce panneau utilise un layout `BorderLayout` et contient deux sous-panneaux :
 * un panneau central pour le dessin et un panneau supérieur pour les outils.
 *
 * @author Baptiste Borie
 */
package paint.View;

import java.awt.*;
import javax.swing.*;

public class MainPannel extends JPanel {

    /**
     * Constructeur par défaut de `MainPannel`.
     * Configure un layout `BorderLayout` et ajoute deux sous-panneaux :
     * - `PanneauDessin` au centre, pour les opérations de dessin.
     * - `PanneauOutils` en haut, pour les outils liés au dessin.
     */
    public MainPannel() {
        super();

        // Définition du layout principal
        this.setLayout(new BorderLayout());

        // Panneau de dessin ajouté au centre
        PanneauDessin pan = new PanneauDessin();
        this.add(pan, BorderLayout.CENTER);

        // Panneau d'outils ajouté en haut
        PanneauOutils outils = new PanneauOutils(pan);
        this.add(outils, BorderLayout.NORTH);
    }
}
