#ifndef SENSOR_SUMMARY_H
#define SENSOR_SUMMARY_H
#define SUMMARY_COLUMNS 3
#include "sensor.h"
void get_summary_matrix(Sensor **data, unsigned int *n_sensors, int result[][SUMMARY_COLUMNS]);
void print_summary(Sensor **data, unsigned int *n_sensors);
#endif

