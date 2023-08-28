// Fichier tab.h 
// Contient les différents prototypes de fonctions développés dans le fichier tab.c
// Fait par Sharane K.Murali 

#include <stdio.h>
#include <stdlib.h>

// Déclaration de la variable globale 

int NMAX;

// Déclaration des fonctions

void InitTab(int *tab, int size);
void PrintTab(int *tab, int size);
int SumTab(int *tab, int size);
int MinSumTab(int *min, int *tab, int size);
