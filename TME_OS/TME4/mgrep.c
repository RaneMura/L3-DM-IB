// Fichier mgrep.c
// Contient le programme qui éxécute multi-grep
// Fait par Sharane K.Murali 3701883 Groupe 6 

#include <sys/types.h>
#include <sys/wait.h>
#include <sys/resource.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> 
#include <sys/time.h>


int main(int argc, char *argv[]){

    struct rusage r;

    for(int i = 2; i<argc; i++){
        int value = fork();
        if(value==0){
            
            printf("ID du fils n°%d : %d avec valeur %d\n\n",i-2,getpid(),value);
            printf("Chaine de caractère à chercher : %s\n\n",argv[1]);
            execlp("grep","grep",argv[1],argv[i],NULL);
            perror("Erreur exec");
            exit(1);
        }
        printf("ID du Père : %d avec valeur %d\n\n",getpid(),value);
    }

    for(int i = 2; i<argc; i++){
        wait3(NULL, 0, &r);
        printf("Temps Utilisateur du fils n°%d : %f\n",i-2,r.ru_utime.tv_sec + 1E-6*r.ru_utime.tv_usec);
        printf("Temps Système du fils n°%d : %f\n\n",i-2,r.ru_stime.tv_sec + 1E-6*r.ru_stime.tv_usec);
    }

    return 0;
}