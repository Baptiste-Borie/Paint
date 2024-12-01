/**
 * Classe représentant un triangle.
 * Cette classe permet de dessiner un triangle isocèle défini par deux points 
 * (les coins inférieur gauche et droit, et le sommet supérieur) 
 * et de vérifier si un point donné est contenu dans ce triangle.
 *
 * @author Baptiste Borie
 */
package paint.Model;

import java.awt.*;

public class TriangleForme extends Forme {

    /**
     * Constructeur de la classe TriangleForme.
     * Initialise un triangle avec ses coordonnées de départ, de fin et sa couleur.
     *
     * @param startX Coordonnée X du coin inférieur gauche.
     * @param startY Coordonnée Y du sommet supérieur.
     * @param endX   Coordonnée X du coin inférieur droit.
     * @param endY   Coordonnée Y des coins inférieurs.
     * @param color  Couleur du triangle.
     */
    public TriangleForme(int startX, int startY, int endX, int endY, Color color) {
        super(startX, startY, endX, endY, color);
    }

    /**
     * Dessine le triangle isocèle en utilisant les coordonnées fournies.
     *
     * @param g Objet Graphics utilisé pour dessiner le triangle.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        int[] xPoints = { startX, (startX + endX) / 2, endX };
        int[] yPoints = { endY, startY, endY };
        g.drawPolygon(xPoints, yPoints, 3);
    }

    /**
     * Vérifie si un point donné est contenu dans le triangle.
     *
     * @param p Point à vérifier.
     * @return {@code true} si le point est contenu dans le triangle, sinon
     *         {@code false}.
     */
    @Override
    public boolean contient(Point p) {
        Polygon triangle = new Polygon(
                new int[] { startX, (startX + endX) / 2, endX },
                new int[] { endY, startY, endY },
                3);
        return triangle.contains(p);
    }
}
