# Fonctionnement du Paint

Cette application fonctionne comme un paint normal. Vous pouvez librement dessiner des formes dans des couleurs et des tailles multiples.

# Menu

Chaque icone du menu à sa fonctionnalité :

- Dessin libre
- Dessin de ligne
- Dessin de rectangle
- Dessin de triangle
- Dessin de cercle
- Gomme
- Réinitialisation du canva en cours.
- Sauvegarder le canva en cours
- Ouvrir un canva précédemment sauvegarder
- Bouton d'export du canva en cours en mode png

# Commande ant :

- Pour (juste) compiler : `ant compile`

- Pour lancer le projet : `ant run`

- Pour générer la javadoc : `ant javadoc`

- Pour nettoyer les dossiers `build` et `doc` : `ant clean`

## Javadoc

Pour générer la javadoc, il faut utiliser la commande `ant javadoc`. Cette
commande génère la javadoc dans le dossier `doc`. Il suffit après de lancer le fichier `doc/index.html` pour naviguer dans la documentation du projet.

## Lancement

À l'aide de la commande `ant run` ou simplement `ant` l'application se lance.

# Si ant ne fonctionne pas/ n'est pas installé :

```
javac -d build src/paint/**/*.java
java -cp build paint.View.Fenetre
```
