#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

#include "limits.h"
#include "print_result.h"
#include "random.h"
#include "sensors.h"
#include "sensor.h"
#include "shared.h"
#include "bootstrap.h"
#include "import_from_csv.h"
#include "ui.h"

uint64_t state = 0;
uint64_t inc = 0;

int main(void)
{
  reset_seed();

  // array that stores the number of sensors of each type
  unsigned int n_sensors[NUM_OF_SENSOR_TYPES];

  // array that stores the frequency of each sensor type
  unsigned int f_sensors[NUM_OF_SENSOR_TYPES];

  // read the number of sensors and frequency from the config file
  char config_file[100];
  get_config(config_file);
  read_file(config_file, n_sensors, f_sensors);

  if (n_sensors[TEMPERATURE_SENSOR_TYPE] < 1 || n_sensors[PLUVIO_SENSOR_TYPE] < 1) {
    printf("Número de sensores inválido.\nVerifique se o número de sensores de temperatura e de pluviosidade são maiores que 0.\n");
    return 1;
  }

  Sensor **data = bootstrap(n_sensors);

  unsigned int count = 0;

  // Temperature sensors
  for (int j = 0; j < n_sensors[TEMPERATURE_SENSOR_TYPE]; j++) { // for every temperature sensor
    // bootstrap sensor
    Sensor current_sensor = bootstrap_temperature(f_sensors[TEMPERATURE_SENSOR_TYPE], ++count);
    data[TEMPERATURE_SENSOR_TYPE][j] = current_sensor;
  }

  // Wind velocity sensors
  for (int j = 0; j < n_sensors[WIND_VELOCITY_SENSOR_TYPE]; j++) { // for every sensor
    Sensor current_sensor = bootstrap_wind_vel(f_sensors[WIND_VELOCITY_SENSOR_TYPE], ++count);
    data[WIND_VELOCITY_SENSOR_TYPE][j] = current_sensor;
  }

  // Wind direction sensors
  for (int j = 0; j < n_sensors[WIND_DIRECTION_SENSOR_TYPE]; j++) {
    Sensor current_sensor = bootstrap_wind_dir(f_sensors[WIND_DIRECTION_SENSOR_TYPE], ++count);
    data[WIND_DIRECTION_SENSOR_TYPE][j] = current_sensor;
  }

  // Pluviosity sensors
  for (int j = 0; j < n_sensors[PLUVIO_SENSOR_TYPE]; j++) { // for every sensor
    Sensor temp_sensor = data[TEMPERATURE_SENSOR_TYPE][0]; // depends on a temperature sensor

    Sensor current_sensor = bootstrap_pluvio(f_sensors[PLUVIO_SENSOR_TYPE], ++count, temp_sensor);
    data[PLUVIO_SENSOR_TYPE][j] = current_sensor;
  }

  // Soil humidity sensors
  for (int j = 0; j < n_sensors[SOIL_HUMIDITY_SENSOR_TYPE]; j++) {
    Sensor pluvio_sensor = data[PLUVIO_SENSOR_TYPE][0]; // depends on a pluvio sensor

    Sensor current_sensor = bootstrap_soil_humidity(f_sensors[SOIL_HUMIDITY_SENSOR_TYPE], ++count, pluvio_sensor);
    data[SOIL_HUMIDITY_SENSOR_TYPE][j] = current_sensor;
  }

  // Air humidity sensors
  for (int j = 0; j < n_sensors[AIR_HUMIDITY_SENSOR_TYPE]; j++) {
    Sensor pluvio_sensor = data[PLUVIO_SENSOR_TYPE][0]; // depends on a pluvio sensor

    Sensor current_sensor = bootstrap_air_humidity(f_sensors[AIR_HUMIDITY_SENSOR_TYPE], ++count, pluvio_sensor);
    data[AIR_HUMIDITY_SENSOR_TYPE][j] = current_sensor;
  }

  init_ui(data, n_sensors, &count);

  deallocate(data, n_sensors);

  return 0;
}

