#include <stdlib.h>

#include "sensor.h"
#include "sensors.h"
#include "shared.h"

void add_sensor(Sensor s, Sensor **data, unsigned int *n_sensors) {
    Sensor *ptr = (Sensor *)realloc(data[s.sensor_type], (n_sensors[s.sensor_type] + 1) * sizeof(Sensor));
    if (ptr == NULL) return;
    ptr[n_sensors[s.sensor_type]] = s;
    data[s.sensor_type] = ptr;
    n_sensors[s.sensor_type]++; // keep n_sensors updated
}

void shift(Sensor *arr, unsigned int size, Sensor const *s);

void remove_sensor(Sensor *s, Sensor **data, unsigned int *n_sensors) {
    if (s == NULL) return;
    unsigned char type = s->sensor_type;
    if (s < data[type] || (data[type] + n_sensors[type] - 1) < s) return;

    free(s->readings);
    free(s->errors);
    shift(data[type], n_sensors[type], s);

    Sensor *ptr = (Sensor *)realloc(data[type], (n_sensors[type] - 1) * sizeof(Sensor));
    if (ptr == NULL) return;
    data[type] = ptr;

    n_sensors[type]--;
}

void adjust_sensor_freq(Sensor *s, unsigned long new_freq) {
    if (s == NULL || new_freq == 0) return;

    s->frequency = new_freq;
    unsigned long old_size = s->readings_size;
    s->readings_size = SECS_IN_DAY / new_freq;

    s->readings = (unsigned short *)realloc(s->readings, s->readings_size * sizeof(unsigned short));
    s->errors = (unsigned char *) realloc(s->errors, s->readings_size * sizeof(unsigned char));

    // initialize if realloced to a bigger size
    while (old_size < s->readings_size) {
        s->readings[old_size] = 0;
        s->errors[old_size] = 0;
        old_size++;
    }
}

void shift(Sensor *arr, unsigned int size, Sensor const *s) {
    unsigned int offset = s - arr;

    while (offset < size - 1) {
        arr[offset] = arr[offset + 1];
        offset++;
    }
}

