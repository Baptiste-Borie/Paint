/**
 * Classe qui implémente un gestionnaire d'événements de souris pour le panneau de dessin.
 * Cette classe permet de gérer les actions liées à la souris, notamment le début et la fin
 * du dessin d'une forme sur le panneau.
 *
 * @author Baptiste Borie
 */
package paint.View;

import java.awt.event.*;

public class MousePannelInfo implements MouseListener {

    private PanneauDessin pan;

    /**
     * Constructeur de la classe `MousePannelInfo`.
     *
     * @param pan le panneau de dessin auquel les événements de souris sont
     *            associés.
     */
    public MousePannelInfo(PanneauDessin pan) {
        this.pan = pan;
    }

    /**
     * Méthode appelée lorsque le bouton de la souris est pressé.
     * Initialise les coordonnées de début du dessin et active le mode de dessin.
     *
     * @param e l'événement de souris associé.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        pan.startX = e.getX();
        pan.startY = e.getY();
        pan.isDrawing = true;
    }

    /**
     * Méthode appelée lorsque le bouton de la souris est relâché.
     * Finalise les coordonnées de la forme, dessine la forme sur le panneau,
     * et désactive le mode de dessin.
     *
     * @param e l'événement de souris associé.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        pan.endX = e.getX();
        pan.endY = e.getY();
        pan.drawOnCanvas();
        pan.isDrawing = false;

        // Création de la forme après le relâchement de la souris
        pan.createForme();
    }

    /**
     * Méthode appelée lorsque la souris entre dans la zone du panneau.
     * Non utilisée dans cette implémentation.
     *
     * @param e l'événement de souris associé.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Méthode appelée lorsque la souris quitte la zone du panneau.
     * Non utilisée dans cette implémentation.
     *
     * @param e l'événement de souris associé.
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Méthode appelée lorsque la souris est cliquée.
     * Non utilisée dans cette implémentation.
     *
     * @param e l'événement de souris associé.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }
}
