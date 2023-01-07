#ifndef UI_H
#define UI_H
#include "sensor.h"
void init_ui(Sensor **data, unsigned int *n_sensors, unsigned int *count);
void get_config(char *filename);
#endif

