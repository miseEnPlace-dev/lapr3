#include <stdio.h>
#include <stdint.h>

#include "sensores.h"
#include "random.h"
#include "generate_base_values.h"
#include "print_result.h"

#define SEC_IN_DAY 86400

#define NUM_TEMPERATURE_REGISTERS SEC_IN_DAY / TEMPERATURES_SENSOR_INTERVAL
#define NUM_VEL_WIND_REGISTERS SEC_IN_DAY / VELOCITY_SENSOR_INTERVAL
#define NUM_DIR_WIND_REGISTERS SEC_IN_DAY / DIRECTION_SENSOR_INTERVAL
#define NUM_PLUVIO_REGISTERS SEC_IN_DAY / PLUVIO_SENSOR_INTERVAL
#define NUM_SOIL_HUMIDITY_REGISTERS SEC_IN_DAY / SOIL_HUMIDITY_SENSOR_INTERVAL
#define NUM_AIR_HUMIDITY_REGISTERS SEC_IN_DAY / AIR_HUMIDITY_SENSOR_INTERVAL

#define NUM_OF_SENSORS 6

uint64_t state = 0;
uint64_t inc = 0;

int main(void)
{
  state = get_value_from_dev_random();
  inc = get_value_from_dev_random();

  char temperatures[NUM_TEMPERATURE_REGISTERS];
  unsigned char vel_wind[NUM_VEL_WIND_REGISTERS];
  unsigned char dir_wind[NUM_DIR_WIND_REGISTERS];
  unsigned char pluvio[NUM_PLUVIO_REGISTERS];
  unsigned char soil_humidity[NUM_SOIL_HUMIDITY_REGISTERS];
  unsigned char air_humidity[NUM_AIR_HUMIDITY_REGISTERS];

  char *matrix[NUM_OF_SENSORS];
  matrix[0] = temperatures;
  matrix[1] = vel_wind;
  matrix[2] = dir_wind;
  matrix[3] = pluvio;
  matrix[4] = soil_humidity;
  matrix[5] = air_humidity;

  generate_base_temp_values(temperatures, NUM_TEMPERATURE_REGISTERS);

  char last_temp_read = TEMP_BASE_VALUE;
  for (int i = 0; i < NUM_TEMPERATURE_REGISTERS; i++)
  {
    last_temp_read = sens_temp(last_temp_read, pcg32_random_r());
    temperatures[i] = (last_temp_read + temperatures[i]) / 2;
  }

  unsigned char last_read = 1;
  for (int i = 0; i < NUM_VEL_WIND_REGISTERS; i++)
  {
    last_read = sens_velc_vento(last_read, pcg32_random_r());
    vel_wind[i] = last_read;
  }

  last_read = 1;
  for (int i = 0; i < NUM_DIR_WIND_REGISTERS; i++)
  {
    last_read = sens_dir_vento(last_read, pcg32_random_r());
    dir_wind[i] = last_read;
  }

  last_read = 1;
  for (int i = 0; i < NUM_PLUVIO_REGISTERS; i++)
  {
    unsigned char last_temp_read = temperatures[i * (TEMPERATURES_SENSOR_INTERVAL / PLUVIO_SENSOR_INTERVAL)];
    last_read = sens_pluvio(last_read, last_temp_read, pcg32_random_r());
    pluvio[i] = last_read;
  }

  last_read = 10;
  for (int i = 0; i < NUM_SOIL_HUMIDITY_REGISTERS; i++)
  {
    unsigned char last_pluvio_read = pluvio[i * (NUM_PLUVIO_REGISTERS / NUM_SOIL_HUMIDITY_REGISTERS)];
    last_read = sens_humd_solo(last_read, last_pluvio_read, pcg32_random_r());
    soil_humidity[i] = last_read;
  }

  last_read = 10;
  for (int i = 0; i < NUM_AIR_HUMIDITY_REGISTERS; i++)
  {
    unsigned char last_pluvio_read = pluvio[i * (NUM_PLUVIO_REGISTERS / NUM_SOIL_HUMIDITY_REGISTERS)];
    last_read = sens_humd_atm(last_read, last_pluvio_read, pcg32_random_r());
    air_humidity[i] = last_read;
  }

  // printf("-- Leituras dos sensores --\n\n");
  // print_result(temperatures, NUM_TEMPERATURE_REGISTERS, "Temperatura", "ºC");
  // printf("\n");
  // print_unsigned_result(vel_wind, NUM_VEL_WIND_REGISTERS, "Velocidade do Vento", "km/h");
  // printf("\n");
  // print_unsigned_result(dir_wind, NUM_DIR_WIND_REGISTERS, "Direção do Vento", "º");
  // printf("\n");
  // print_unsigned_result(pluvio, NUM_PLUVIO_REGISTERS, "Pluviosidade", "mm");
  // printf("\n");
  // print_unsigned_result(soil_humidity, NUM_SOIL_HUMIDITY_REGISTERS, "Humidade do Solo", "%");
  // printf("\n");
  // print_unsigned_result(air_humidity, NUM_AIR_HUMIDITY_REGISTERS, "Humidade do Ar", "%");

  return 0;
}
