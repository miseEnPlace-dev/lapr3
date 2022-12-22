#include<stdio.h>

#include "print_result.h"
#include "sensor.h"

void export_to_csv(Sensor **data, char ***errors, int *n_of_sensors, int NUM_OF_SENSORS){

    FILE *file_ptr;

    char *file_name = "test.csv";

    file_ptr = fopen(file_name,"w");



    fprintf(file_ptr, "\n-- Leituras dos sensores --\n\n");
    print_signed_result(file_ptr, data[0], data[0]->readings_size, data[0]->name, data[0]->units, n_of_sensors[0], errors[0]);
    fprintf(file_ptr, "\n");

    for (int i = 1; i < NUM_OF_SENSORS; i++)
    {
        print_result(file_ptr, data[i], data[i]->readings_size, data[i]->name, data[i]->units, n_of_sensors[i], errors[i]);
        if (i < NUM_OF_SENSORS - 1) // fix to avoid printing extra new line in last iteration
        fprintf(file_ptr, "\n");
    }

    fclose(file_ptr);
    
}
