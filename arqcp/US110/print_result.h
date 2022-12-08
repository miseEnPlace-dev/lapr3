#ifndef PRINT_RESULT_H
#define PRINT_RESULT_H
#include "sensor.h"
void print_result(Sensor *arr, int size, char *name, char *units, int n_of_sensors, char **errors);
void print_signed_result(Sensor *arr, int size, char *name, char *units, int n_of_sensors, char **errors);
#endif
