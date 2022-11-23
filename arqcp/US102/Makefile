# A Makefile with variables and suffix rules

# Variables
INCLUDES = sensores.h
SOURCES = main.c sens_dir_vento.s sens_temp.s sens_velc_vento.s sens_humd_solo.s sens_humd_atm.s sens_pluvio.s random.s dev_random.c
OBJFILES = main.o sens_dir_vento.o sens_temp.o sens_velc_vento.o sens_humd_solo.o sens_humd_atm.o sens_pluvio.o random.o dev_random.o
EXEC = prog

# Suffix rules
.SUFFIXES : .c .o .s

.s.o:
	gcc -Wall -g -c $<
# How to build an object .o from a code file .c ; $< -- file name
.c.o:
	gcc -Wall -g -c $<

${EXEC}: ${OBJFILES}
	gcc -Wall -g -o ${EXEC} ${OBJFILES}

${OBJFILES}: ${SOURCES} ${INCLUDES}

run: ${EXEC}
	./${EXEC}

clean:
	rm -f ${OBJFILES} ${EXEC}
