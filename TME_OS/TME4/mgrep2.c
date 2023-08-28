// Fichier mgrep2.c
// Contient le programme qui éxécute multi-grep avec parallélisme contraint 
// Fait par Sharane K.Murali 3701883 Groupe 6 

#include <sys/types.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> 

#define MAXFILS 5

int min(int a, int b){
    if (a<b){return a;}
    return b;
}

int main(int argc, char *argv[]){

    int cpt_cours = 0;
    for(int i = 2; i<argc; i++){
        if (cpt_cours>=MAXFILS){
            wait(NULL);
        }

        int value = fork();
        cpt_cours++;
        if(value==0){
            printf("fils %d : %d avec value %d\n",i-2,getpid(),value);
            execlp("grep","grep",argv[1],argv[i],NULL);
            perror("Erreur exec");
            exit(1);
        }
        printf("père: %d avec value %d\n",getpid(),value);       
    }

    for(int i = 0; i<min(cpt_cours,MAXFILS);i++){
        wait(NULL);
    }

    return 0;
}