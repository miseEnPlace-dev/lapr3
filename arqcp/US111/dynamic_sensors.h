#ifndef DYNAMIC_SENSORS
#define DYNAMIC_SENSORS

#include "sensor.h"

void add_sensor(Sensor s, Sensor **data, unsigned int *n_sensors);
void remove_sensor(Sensor *s, Sensor **data, unsigned int *n_sensors);
void adjust_sensor_freq(Sensor *s);

#endif

