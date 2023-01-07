#ifndef BOOTSTRAP_H
#define BOOTSTRAP_H

#include "sensor.h"
Sensor **bootstrap(unsigned int const *n_sensors);
void deallocate(Sensor **data, unsigned int const *n_sensors);
Sensor bootstrap_temperature(unsigned long frequency, unsigned int id);
Sensor bootstrap_wind_vel(unsigned long frequency, unsigned int id);
Sensor bootstrap_wind_dir(unsigned long frequency, unsigned int id);
Sensor bootstrap_pluvio(unsigned long frequency, unsigned int id, Sensor temp_sensor);
Sensor bootstrap_soil_humidity(unsigned long frequency, unsigned int id, Sensor pluvio_sensor);
Sensor bootstrap_air_humidity(unsigned long frequency, unsigned int id, Sensor pluvio_sensor);

#endif

