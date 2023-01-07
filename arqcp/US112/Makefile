# A Makefile with variables and suffix rules

# Variables
INCLUDES = random.h sensor.h sensors.h generate_base_values.h print_result.h limits.h dynamic_sensors.h shared.h bootstrap.h import_from_csv.h export_to_csv.h readings_generate.h sensor_summary.h
SOURCES = main.c sens_dir_vento.s sens_temp.s sens_velc_vento.s sens_humd_solo.s sens_humd_atm.s sens_pluvio.s random.s dev_random.c generate_base_temp_values.c print_result.c limits.c dynamic_sensors.c shared.c bootstrap.c import_from_csv.c export_to_csv.c readings_generate.c sensor_summary.c
OBJFILES = main.o sens_dir_vento.o sens_temp.o sens_velc_vento.o sens_humd_solo.o sens_humd_atm.o sens_pluvio.o random.o dev_random.o generate_base_temp_values.o print_result.o limits.o dynamic_sensors.o shared.o bootstrap.o import_from_csv.o export_to_csv.o readings_generate.o sensor_summary.o
EXEC = prog

# Suffix rules
.SUFFIXES : .c .o .s

.s.o:
	gcc -Wall -g -c $<
# How to build an object .o from a code file .c ; $< -- file name
.c.o:
	gcc -Wall -g -c $<

${EXEC}: ${OBJFILES}
	gcc -Wall -g -o ${EXEC} ${OBJFILES} -lm

${OBJFILES}: ${SOURCES} ${INCLUDES}

run: ${EXEC}
	./${EXEC}

clean:
	rm -f ${OBJFILES} ${EXEC}
