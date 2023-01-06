#ifndef PRINT_RESULT_H
#define PRINT_RESULT_H
#include "sensor.h"
void print_result(Sensor *arr, unsigned int size);
void print_signed_result(Sensor *arr, unsigned int size);
void print_small(Sensor **data, unsigned int const *n_sensors);
#endif
