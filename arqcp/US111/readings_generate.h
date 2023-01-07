#ifndef READINGS_GENERATE
#define READINGS_GENERATE

#include "sensor.h"

void generate_temperature(Sensor current_sensor);
void generate_general(Sensor current_sensor, int base_value, unsigned short (*sens_func)(unsigned short, short), char is_char);
void generate_dependant(Sensor current_sensor, Sensor dependant, int base_value, unsigned short (*sens_func)(unsigned short, unsigned short, short), char is_char);

#endif

