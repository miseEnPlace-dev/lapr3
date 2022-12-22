#ifndef BOOTSTRAP_H
#define BOOTSTRAP_H

#include "sensor.h"
Sensor **bootstrap(unsigned int const *n_sensors);
void deallocate(Sensor *data, unsigned int const *n_sensors);
Sensor bootstrap_temperature();
Sensor bootstrap_wind_vel();
Sensor bootstrap_wind_dir();
Sensor bootstrap_pluvio();
Sensor bootstrap_soil_humidity();
Sensor bootstrap_air_humidity();

#endif

