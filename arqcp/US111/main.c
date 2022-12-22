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

uint64_t state = 0;
uint64_t inc = 0;

int main(void)
{
  reset_seed();

  // this array will come from import_config()
  unsigned int n_sensors[NUM_OF_SENSOR_TYPES] = {2, 2, 2, 2, 2, 2};

  if (n_sensors[TEMPERATURE_SENSOR_TYPE] == 0 || n_sensors[PLUVIO_SENSOR_TYPE] == 0) {
    printf("Número de sensores inválido.\nVerifique se o número de sensores de temperatura e de pluviosidade são diferentes de 0.\n");
    return -1;
  }
 
  Sensor **data = bootstrap(n_sensors);

  unsigned int count = 0;

  // Temperature sensors
  for (int j = 0; j < n_sensors[TEMPERATURE_SENSOR_TYPE]; j++) { // for every temperature sensor                                                                
    // bootstrap sensor
    Sensor current_sensor = bootstrap_temperature();
    current_sensor.id = count++;

    char base_temperatures[current_sensor.readings_size];
    generate_base_temp_values(base_temperatures, current_sensor.readings_size);

    int total_errors = 0;

    do { // retry on excess of errors
      char last_read = TEMP_BASE_VALUE;

      for (int i = 0; i < current_sensor.readings_size; i++) {
        // generate readings
        last_read = sens_temp(last_read, pcg32_random_r());
        char base_temp_read = (i == 0 ? TEMP_BASE_VALUE : base_temperatures[i - 1]);
        current_sensor.readings[i] = (unsigned short)(last_read + base_temp_read) / 2;

        current_sensor.errors[i] = exceeded_limits_signed(i, current_sensor); // this can probably be made in a better way
      }

      total_errors = get_total_errors(current_sensor);
      printf("%s > Sensor %d: %d erros\n", current_sensor.name, j + 1, total_errors);

      if (total_errors > MAX_INCORRECT_READS) {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);

    data[TEMPERATURE_SENSOR_TYPE][j] = current_sensor;
  }

  // Wind velocity sensors
  for (int j = 0; j < n_sensors[WIND_VELOCITY_SENSOR_TYPE]; j++) { // for every sensor
    Sensor current_sensor = bootstrap_wind_vel();
    current_sensor.id = count++;

    int total_errors = 0;

    do { // retry on excess of errors
      unsigned short last_read = pcg32_random_r() % VELC_BASE_VALUE;

      for (int i = 0; i < current_sensor.readings_size; i++) {
        // generate readings
        last_read = sens_velc_vento(last_read, pcg32_random_r());
        current_sensor.readings[i] = last_read;

        current_sensor.errors[i] = exceeded_limits(i, current_sensor);
      }

      total_errors = get_total_errors(current_sensor);
      printf("%s > Sensor %d: %d erros\n", current_sensor.name, j + 1, total_errors);

      if (total_errors > MAX_INCORRECT_READS) {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);

    data[WIND_VELOCITY_SENSOR_TYPE][j] = current_sensor;
  }

  // Wind direction sensors
  for (int j = 0; j < n_sensors[WIND_DIRECTION_SENSOR_TYPE]; j++) {
    Sensor current_sensor = bootstrap_wind_dir();
    current_sensor.id = count++;

    int total_errors = 0;

    do { // retry on excess of errors
      unsigned short last_read = pcg32_random_r() % DIR_BASE_VALUE;

      for (int i = 0; i < current_sensor.readings_size; i++) {
        // generate readings
        last_read = sens_dir_vento(last_read, pcg32_random_r());
        current_sensor.readings[i] = last_read;

        current_sensor.errors[i] = exceeded_limits(i, current_sensor);
      }
      
      total_errors = get_total_errors(current_sensor);
      printf("%s > Sensor %d: %d erros\n", current_sensor.name, j + 1, total_errors);

      if (total_errors > MAX_INCORRECT_READS) {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);

    data[WIND_DIRECTION_SENSOR_TYPE][j] = current_sensor;
  }

  // Pluviosity sensors
  for (int j = 0; j < n_sensors[PLUVIO_SENSOR_TYPE]; j++) { // for every sensor
    Sensor current_sensor = bootstrap_pluvio();
    current_sensor.id = count++;

    int total_errors = 0;

    do { // retry on excess of errors
      unsigned short last_read = pcg32_random_r() % PLUVIO_BASE_VALUE;

      for (int i = 0; i < current_sensor.readings_size; i++) {
        // generate readings
        Sensor temp_sensor = data[TEMPERATURE_SENSOR_TYPE][0]; // depends on a temperature sensor
        unsigned char last_temp_read = temp_sensor.readings[i * (temp_sensor.frequency / current_sensor.frequency)];

        last_read = (unsigned short)sens_pluvio(last_read, last_temp_read, pcg32_random_r());
        current_sensor.readings[i] = last_read;

        current_sensor.errors[i] = exceeded_limits(i, current_sensor);
      }

      total_errors = get_total_errors(current_sensor);
      printf("%s > Sensor %d: %d erros\n", current_sensor.name, j + 1, total_errors);

      if (total_errors > MAX_INCORRECT_READS) {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);

    data[PLUVIO_SENSOR_TYPE][j] = current_sensor;
  }

  // Soil humidity sensors
  for (int j = 0; j < n_sensors[SOIL_HUMIDITY_SENSOR_TYPE]; j++) {
    Sensor current_sensor = bootstrap_soil_humidity();
    current_sensor.id = count++;

    int total_errors = 0;

    do { // retry on excess of errors
      unsigned short last_read = SOIL_BASE_VALUE;

      for (int i = 0; i < current_sensor.readings_size; i++) {
        // generate readings
        Sensor pluvio_sensor = data[PLUVIO_SENSOR_TYPE][0]; // depends on a pluvio sensor
        unsigned short last_pluvio_read = pluvio_sensor.readings[i * (pluvio_sensor.readings_size / current_sensor.readings_size)];

        last_read = (unsigned short)sens_humd_solo(last_read, last_pluvio_read, pcg32_random_r());
        current_sensor.readings[i] = last_read;

        current_sensor.errors[i] = exceeded_limits(i, current_sensor);
      }

      total_errors = get_total_errors(current_sensor);
      printf("%s > Sensor %d: %d erros\n", current_sensor.name, j + 1, total_errors);

      if (total_errors > MAX_INCORRECT_READS) {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);

    data[SOIL_HUMIDITY_SENSOR_TYPE][j] = current_sensor;
  }

  // Air humidity sensors
  for (int j = 0; j < n_sensors[AIR_HUMIDITY_SENSOR_TYPE]; j++) {
    Sensor current_sensor = bootstrap_air_humidity();
    current_sensor.id = count++;

    int total_errors = 0;

    do { // retry on excess of errors
      unsigned short last_read = AIR_BASE_VALUE;

      for (int i = 0; i < current_sensor.readings_size; i++) {
        // generate readings
        Sensor pluvio_sensor = data[PLUVIO_SENSOR_TYPE][0]; // depends on a pluvio sensor
        unsigned short last_pluvio_read = pluvio_sensor.readings[i * (pluvio_sensor.readings_size / current_sensor.readings_size)];

        last_read = (unsigned short)sens_humd_atm(last_read, last_pluvio_read, pcg32_random_r());
        current_sensor.readings[i] = last_read;

        current_sensor.errors[i] = exceeded_limits(i, current_sensor);
      }

      total_errors = get_total_errors(current_sensor);
      printf("%s > Sensor %d: %d erros\n", current_sensor.name, j + 1, total_errors);

      if (total_errors > MAX_INCORRECT_READS) {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);

    data[AIR_HUMIDITY_SENSOR_TYPE][j] = current_sensor;
  }

  printf("\n-- Leituras dos sensores --\n\n");
  print_signed_result(data[TEMPERATURE_SENSOR_TYPE], n_sensors[TEMPERATURE_SENSOR_TYPE]);
  printf("\n");

  for (int i = 1; i < NUM_OF_SENSOR_TYPES; i++)
  {
    print_result(data[i], n_sensors[i]);
    if (i < NUM_OF_SENSOR_TYPES - 1) // fix to avoid printing extra new line in last iteration
      printf("\n");
  }

  return 0;
}

