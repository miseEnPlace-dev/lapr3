#include <stdio.h>
#include <string.h>
#include <time.h>

#include "sensor.h"
#include "shared.h"
#include "limits.h"

void format_time(char *output);

void write_csv(char *buffer, const char* name) {
    FILE *file_ptr;
    char file_name[50];
    strcpy(file_name, "exported_");
    strcat(file_name, name);

    char date[25];
    format_time(date);
    strcat(file_name, date);
    // exported_result_2023-01-01_10-00-01.csv

    strcat(file_name, ".csv");

    file_ptr = fopen(file_name, "w");
    fprintf(file_ptr, "%s\n", buffer);
    fclose(file_ptr);
}

void export_result(Sensor **data, unsigned int *n_sensors) {
    char buffer[4096];
    strcpy(buffer, "ID,Leitura,Erro");

    //export_to_csv(buffer, "result");
}

void export_details(Sensor **data, unsigned int *n_sensors) {
    char buffer[4096];
    strcpy(buffer, "ID,Nome,Tipo,Unidades,Frequência,Num Leituras,Num Erros,Limite Max,Limite Min\n");

    char temp[256];
    for (int i = 0; i < NUM_OF_SENSOR_TYPES; i++) {
        for (int j = 0; j < n_sensors[i]; j++) {
            int errors = get_total_errors(data[i][j]);
            sprintf(temp, "%hu,%s,%hhu,%s,%lu,%lu,%d,%hu,%hu\n", data[i][j].id, data[i][j].name, data[i][j].sensor_type, data[i][j].units, data[i][j].frequency, data[i][j].readings_size, errors, data[i][j].min_limit, data[i][j].max_limit);
            strcat(buffer, temp);
        }
    }
    
    write_csv(buffer, "details");
}

void format_time(char *output){
    time_t rawtime;
    struct tm *timeinfo;
    
    time(&rawtime);
    timeinfo = localtime(&rawtime);
    
    sprintf(output, "_%d-%02d-%02d_%02d-%02d-%02d", timeinfo->tm_year + 1900,
            timeinfo->tm_mon + 1, timeinfo->tm_mday,
            timeinfo->tm_hour, timeinfo->tm_min, timeinfo->tm_sec);
}

