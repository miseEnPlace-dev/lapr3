#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

#include "generate_base_values.h"
#include "limits.h"
#include "print_result.h"
#include "random.h"
#include "sensors.h"
#include "sensor.h"
#include "shared.h"
#include "bootstrap.h"
#include "import_from_csv.h"
#include "dynamic_sensors.h"
#include "export_to_csv.h"
#include "readings_generate.h"
#include "sensor_summary.h"

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
  read_file(CONFIG_FILENAME, n_sensors, f_sensors);

  if (n_sensors[TEMPERATURE_SENSOR_TYPE] < 1 || n_sensors[PLUVIO_SENSOR_TYPE] < 1) {
    printf("Número de sensores inválido.\nVerifique se o número de sensores de temperatura e de pluviosidade são maiores que 0.\n");
    return -1;
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

  printf("\n-- Leituras dos sensores --\n\n");
  print_signed_result(data[TEMPERATURE_SENSOR_TYPE], n_sensors[TEMPERATURE_SENSOR_TYPE]);
  printf("\n");

  for (int i = 1; i < NUM_OF_SENSOR_TYPES; i++)
  {
    print_result(data[i], n_sensors[i]);
    printf("\n");
  }

  // summary
  print_summary(data, n_sensors);
  printf("\n\n");

  print_small(data, n_sensors);

  // add sensor
  Sensor new_sensor = bootstrap_temperature(TEMPERATURES_SENSOR_INTERVAL, ++count);
  add_sensor(new_sensor, data, n_sensors);
  printf("\nAdicionado 1 sensor do tipo %s.\n\n", new_sensor.name);

  print_small(data, n_sensors);

  // delete sensor
  Sensor *p_delete = &data[SOIL_HUMIDITY_SENSOR_TYPE][1];
  unsigned short deleted_id = p_delete->id;
  remove_sensor(p_delete, data, n_sensors);
  printf("\nRemovido 1 sensor com o id %hu.\n\n", deleted_id);

  print_small(data, n_sensors);

  // adjust sensor frequency
  Sensor *p_sens = &data[AIR_HUMIDITY_SENSOR_TYPE][0];
  unsigned long new_freq = p_sens->frequency * 2;
  adjust_sensor_freq(p_sens, new_freq);
  printf("\nAjustada a frequência do sensor c/ id %hu para %lu segundos.", p_sens->id, new_freq);

  p_sens = &data[WIND_DIRECTION_SENSOR_TYPE][2];
  new_freq = 5400;
  adjust_sensor_freq(p_sens, new_freq);
  printf("\nAjustada a frequência do sensor c/ id %hu para %lu segundos.\n\n", p_sens->id, new_freq);

  print_small(data, n_sensors);

  // export data
  //export_result(data, n_sensors);
  //export_summary(data, n_sensors);

  deallocate(data, n_sensors);

  return 0;
}

