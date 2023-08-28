CC = gcc
CFLAGS = -Wall
LDFLAGS =
EXEC=maintab

all: $(EXEC)

maintab : tab.o maintab.o
	$(CC) -o $@ $^ $(LDFLAGS)

tab.o : tab.c
	$(CC) -o $@ -c $< $(CFLAGS)

maintab.o : maintab.c tab.h
	$(CC) -o $@ -c $< $(CFLAGS)

clean:
	rm -rf *.o

rmproper: clean
	rm -rf $(EXEC)