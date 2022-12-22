#ifndef LIMIT_H
#define LIMIT_H

#include "sensor.h"
void reset_seed();
int get_total_errors(Sensor s);
unsigned char exceeded_limits_signed(unsigned int index, Sensor s);
unsigned char exceeded_limits(unsigned int index, Sensor s);

#endif

