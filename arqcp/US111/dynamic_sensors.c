#include <stdlib.h>

#include "sensor.h"
#include "sensors.h"

void add_sensor(Sensor s, Sensor **data, unsigned int *n_sensors) {
    Sensor *ptr = (Sensor *)realloc(data[s.sensor_type], (n_sensors[s.sensor_type] + 1) * sizeof(Sensor *));
    if (ptr == NULL) return;
    data[s.sensor_type] = ptr;
    n_sensors[s.sensor_type]++; // keep n_sensors updated
}

void remove_sensor(Sensor *s, Sensor **data, unsigned int *n_sensors) {
    
}

void adjust_sensor_freq(Sensor *s) {}

