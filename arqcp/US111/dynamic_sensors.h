#ifndef DYNAMIC_SENSORS
#define DYNAMIC_SENSORS

#include "sensor.h"

void add_sensor(Sensor s, Sensor **data);
void remove_sensor(Sensor *s, Sensor **data);
void adjust_sensor_freq(Sensor *s);

#endif

