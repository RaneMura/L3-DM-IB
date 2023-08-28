// Fichier zombie.c
// Contient le main du programme qui éxécute le processus zombie durant 30 secondes
// Fait par Sharane K.Murali 3701883 Groupe 6 

#include <sys/types.h>
#include <sys/wait.h>
#include <sys/resource.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> 
#include <sys/time.h>

int main() {
    pid_t p,q;
    p = fork();
    q = fork();

    if(p == 0){ 
        wait(NULL);
        exit(0);
    } 

    if(q == 0){ 
        wait(NULL);
        exit(0);
    } 
    else { 
        sleep(10);
        wait(NULL);
    }
    return 0;
}
