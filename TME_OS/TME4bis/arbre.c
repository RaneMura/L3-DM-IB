#include <sys/types.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> 


int main(int argc, char *argv[]){


    for (int i =0;i<atoi(argv[1]);i++){
        pid_t p;
        pid_t q;
        p = fork();
        if (p==0){            
            sleep(30);
            wait(NULL);
        }
        if(p>0){
            q = fork();
            if(q==0){
                sleep(30);
                wait(NULL);
            }
            if(q>0){
                sleep(30);
                wait(NULL);
            }
        }
    }
    exit(0);
    return 0;
}