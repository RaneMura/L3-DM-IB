// Fichier maintab.c
// Contient le main du programme
// Fait par Sharane K.Murali 3701883 Groupe 6 

#include "tab.h"
#include <time.h>
#include <sys/time.h>
#include <sys/resource.h>

// Initialisation de la variable globale

int NMAX = 1000000;

// Fonction PrintMem : affiche la taille résidente maximale réservée par le programme

void PrintMem(){
    int who = RUSAGE_SELF;
    struct rusage usage;
    int val;

    val = getrusage(who,&usage);
    if (val == 0){
        printf("Taille résidente maximale : %ld\n\n", usage.ru_maxrss); 

    }
    else {
        printf("Erreur"); 
    }
}

int main(){
    
    srand(time(NULL));

    printf("Les appels de fonctions de PrintTab sont en commentaire : NMAX est très grand\n\n"); 

    printf("Avant initialisation du tableau tab1\n"); 
    PrintMem();

    // Déclaration du tableau tab en tant que variable locale    
    int tab[NMAX];

    // Initialisation et affichage du tableau tab 
    InitTab(tab,NMAX);

    printf("Après initialisation du tableau tab1 et avant initialisation du tableau tab2\n");
    PrintMem();

    //PrintTab(tab, NMAX);

    // Déclaration de min1 et du pointeur pmin1 et stockage du minimum du tableau tab dans min1 
    int min1 = 10;
    int *pmin1 = &min1;
    MinSumTab(pmin1, tab, NMAX);


    // Déclaration du tableau tab en allocation avec malloc   
    int *tab2 = malloc(NMAX*sizeof(int));

    // Initialisation et affichage du tableau tab2 
    InitTab(tab2,NMAX);
    
    printf("Après initialisation du tableau tab2\n");
    PrintMem();
    
    //PrintTab(tab2, NMAX);
    
    // Déclaration de min2 et du pointeur pmin2 et stockage du minimum du tableau tab2 dans min2 
    int min2 = 10;
    int *pmin2 = &min2;
    MinSumTab(pmin2, tab2, NMAX);

    // Affichage des sommes des valeurs des tableaux tab1 et tab2
    printf("Somme des valeurs du tableau 1 = %d\n", SumTab(tab,NMAX));
    printf("Somme des valeurs du tableau 2 = %d\n\n", SumTab(tab2,NMAX));

    // Affichage des minimums des valeurs des tableaux tab1 et tab2
    printf("Minimum du tableau 1 = %d\n", min1);
    printf("Minimum du tableau 2 = %d\n\n", min2);

    free(tab2);

    return 0;
}

// A NMAX = 1000000, la mémoire résidente occupée par le programme augmente à chaque appel de InitTab et plus précisément après l'appel de la fonction InitTab.

