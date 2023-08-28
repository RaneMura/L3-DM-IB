// Fichier tab.c
// Contient les fonctions nécessaire au programme
// Fait par Sharane K.Murali 3701883 Groupe 6 

#include "tab.h"

// Fonction InitTab : initialise le tableau en paramètre

void InitTab(int *tab, int size){
    for (int i=0;i<size;i++){
        tab[i] = rand()%10;
    }
}

// Fonction PrintTab : affiche le tableau en paramètre

void PrintTab(int *tab, int size){
    for (int i=0;i<size;i++){
        printf("%d ", tab[i]);
    }
    printf("\n\n");
}

// Fonction SumTab : retourne la somme des éléments du tableau en paramètre

int SumTab(int *tab, int size){
    int sum = 0;
    for (int i=0; i<size; i++){
        sum = sum + tab[i];
    }

    return sum;
}

// Fonction MinSumTab : retourne la somme des éléments du tableau en paramètre et stocke la valeur du minimum dans le pointeur en paramètre

int MinSumTab(int *pmin, int *tab, int size){
    
    for (int i = 0; i<size; i++){
        if (*pmin>tab[i]){
            *pmin = tab[i];    
        }
    }
    return SumTab(tab, size);
}