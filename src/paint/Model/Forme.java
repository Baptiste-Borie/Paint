/**
 * Classe abstraite représentant une forme générique.
 * Elle définit les propriétés communes à toutes les formes,
 * comme les coordonnées de départ et de fin, la couleur, et le style de trait.
 *
 * @author Baptiste Borie
 */
package paint.Model;

import java.awt.*;

public abstract class Forme {
    /**
     * Coordonnée X du point de départ de la forme.
     */
    protected int startX;

    /**
     * Coordonnée Y du point de départ de la forme.
     */
    protected int startY;

    /**
     * Coordonnée X du point de fin de la forme.
     */
    protected int endX;

    /**
     * Coordonnée Y du point de fin de la forme.
     */
    protected int endY;

    /**
     * Couleur de la forme.
     */
    protected Color color;

    /**
     * Constructeur de la classe Forme.
     *
     * @param startX Coordonnée X du point de départ.
     * @param startY Coordonnée Y du point de départ.
     * @param endX   Coordonnée X du point de fin.
     * @param endY   Coordonnée Y du point de fin.
     * @param color  Couleur de la forme.
     */
    public Forme(int startX, int startY, int endX, int endY, Color color) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
    }

    /**
     * Retourne la couleur de la forme.
     *
     * @return Couleur de la forme.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Méthode abstraite pour dessiner la forme.
     *
     * @param g Objet Graphics utilisé pour dessiner la forme.
     */
    public abstract void draw(Graphics g);

    /**
     * Méthode abstraite pour vérifier si un point donné est contenu dans la forme.
     *
     * @param p Point à vérifier.
     * @return {@code true} si le point est contenu dans la forme, sinon
     *         {@code false}.
     */
    public abstract boolean contient(Point p);
}
