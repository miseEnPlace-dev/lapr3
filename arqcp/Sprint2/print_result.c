#include <stdio.h>
#include <string.h>

#include "sensor.h"
#include "shared.h"
#include "limits.h"

void print_result(Sensor *arr, unsigned int size) {
    if (size == 0) return;
    for (int j = 0; j < size; j++) {
        printf("\nSensor id %d:\n", arr[j].id);
        for (int i = 0; i < arr[j].readings_size; i++) {
            printf("Leitura: %u%s %s\n", arr[j].readings[i], arr[j].units, arr[j].errors[i] == 1 ? "(Erro)" : "");
        }   
    }
}

void print_signed_result(Sensor *arr, unsigned int size) {
    if (size == 0) return;
    for (int j = 0; j < size; j++) {
        printf("\nSensor id %d:\n", arr[j].id);
        for (int i = 0; i < arr[j].readings_size; i++) {
            printf("Leitura: %d%s %s\n", (char)arr[j].readings[i], arr[j].units, arr[j].errors[i] == 1 ? "(Erro)" : "");
        }
    }
}

void print_small(Sensor **data, unsigned int const *n_sensors) {
    for (int i = 0; i < NUM_OF_SENSOR_TYPES; i++) {
        printf("Sensores do tipo %s:\n", SENSOR_TYPE_DESIGNATIONS[i]);

        for (int j = 0; j < n_sensors[i]; j++) {
            Sensor s = data[i][j];
            int errors = get_total_errors(s);
            printf(" - Sensor %d: %s (id %hu): %lu leituras [%d]\n", j+1, s.name, s.id, s.readings_size, errors);
        }
    }
}

void print_readings(Sensor **data, unsigned int const *n_sensors) {
    printf("-- Leituras dos sensores --\n\n");
    print_signed_result(data[TEMPERATURE_SENSOR_TYPE], n_sensors[TEMPERATURE_SENSOR_TYPE]);
    printf("\n");

    for (int i = 1; i < NUM_OF_SENSOR_TYPES; i++)
    {
        printf("-- Registos %s: --\n", SENSOR_TYPE_DESIGNATIONS[i]);
        print_result(data[i], n_sensors[i]);
	    if (i < NUM_OF_SENSOR_TYPES - 1) // fix to avoid printing extra new line in last iteration
            printf("\n");
    }
}

