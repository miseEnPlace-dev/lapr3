#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

#include "generate_base_values.h"
#include "limits.h"
#include "print_result.h"
#include "random.h"
#include "sensores.h"
#include "sensor.h"

#define SEC_IN_DAY 86400

#define NUM_TEMPERATURE_REGISTERS SEC_IN_DAY / TEMPERATURES_SENSOR_INTERVAL
#define NUM_VEL_WIND_REGISTERS SEC_IN_DAY / VELOCITY_SENSOR_INTERVAL
#define NUM_DIR_WIND_REGISTERS SEC_IN_DAY / DIRECTION_SENSOR_INTERVAL
#define NUM_PLUVIO_REGISTERS SEC_IN_DAY / PLUVIO_SENSOR_INTERVAL
#define NUM_SOIL_HUMIDITY_REGISTERS SEC_IN_DAY / SOIL_HUMIDITY_SENSOR_INTERVAL
#define NUM_AIR_HUMIDITY_REGISTERS SEC_IN_DAY / AIR_HUMIDITY_SENSOR_INTERVAL

#define TEMPERATURE_SENSOR_TYPE 0
#define VELOCITY_SENSOR_TYPE 1
#define DIRECTION_SENSOR_TYPE 2
#define PLUVIO_SENSOR_TYPE 3
#define SOIL_HUMIDITY_SENSOR_TYPE 4
#define AIR_HUMIDITY_SENSOR_TYPE 5

#define NUM_OF_SENSORS 6

uint64_t state = 0;
uint64_t inc = 0;

int main(void)
{
  reset_seed();

  if (N_OF_TEMP_SENSORS == 0 || N_OF_PLUVIO_SENSORS == 0)
  {
    printf("Número de sensores inválido.\nVerifique se o número de sensores de temperatura, humidade do solo e ar são iguais e diferentes de 0.\n");
    return -1;
  }

  Sensor *data[NUM_OF_SENSORS];

  Sensor temp_sensors[N_OF_TEMP_SENSORS];
  Sensor vel_wind_sensors[N_OF_VELOCITY_SENSORS];
  Sensor dir_wind_sensors[N_OF_DIRECTION_SENSORS];
  Sensor pluvio_sensors[N_OF_PLUVIO_SENSORS];
  Sensor soil_humidity_sensors[N_OF_SOIL_HUMIDITY_SENSORS];
  Sensor air_humidity_sensors[N_OF_AIR_HUMIDITY_SENSORS];

  int n_of_sensors[NUM_OF_SENSORS] = {N_OF_TEMP_SENSORS, N_OF_VELOCITY_SENSORS, N_OF_DIRECTION_SENSORS, N_OF_PLUVIO_SENSORS, N_OF_SOIL_HUMIDITY_SENSORS, N_OF_AIR_HUMIDITY_SENSORS};

  data[TEMPERATURE_SENSORS_INDEX] = temp_sensors;
  data[VELOCITY_SENSORS_INDEX] = vel_wind_sensors;
  data[DIR_WIND_SENSORS_INDEX] = dir_wind_sensors;
  data[PLUVIO_SENSORS_INDEX] = pluvio_sensors;
  data[SOIL_HUMIDITY_SENSORS_INDEX] = soil_humidity_sensors;
  data[AIR_HUMIDITY_SENSORS_INDEX] = air_humidity_sensors;

  char **errors[NUM_OF_SENSORS];

  char *error_temp_sensors[N_OF_TEMP_SENSORS];
  char *error_vel_wind_sensors[N_OF_VELOCITY_SENSORS];
  char *error_dir_wind_sensors[N_OF_DIRECTION_SENSORS];
  char *error_pluvio_sensors[N_OF_PLUVIO_SENSORS];
  char *error_soil_humidity_sensors[N_OF_SOIL_HUMIDITY_SENSORS];
  char *error_air_humidity_sensors[N_OF_AIR_HUMIDITY_SENSORS];

  errors[TEMPERATURE_SENSORS_INDEX] = error_temp_sensors;
  errors[VELOCITY_SENSORS_INDEX] = error_vel_wind_sensors;
  errors[DIR_WIND_SENSORS_INDEX] = error_dir_wind_sensors;
  errors[PLUVIO_SENSORS_INDEX] = error_pluvio_sensors;
  errors[SOIL_HUMIDITY_SENSORS_INDEX] = error_soil_humidity_sensors;
  errors[AIR_HUMIDITY_SENSORS_INDEX] = error_air_humidity_sensors;

  char error_readings_temp[N_OF_TEMP_SENSORS][NUM_TEMPERATURE_REGISTERS];
  char base_temperatures[NUM_TEMPERATURE_REGISTERS];
  for (int j = 0; j < N_OF_TEMP_SENSORS; j++)
  {
    Sensor temperature_sensor;
    temperature_sensor.id = 0; // TODO: generate id
    temperature_sensor.name = "Temperatura";
    temperature_sensor.sensor_type = TEMPERATURE_SENSOR_TYPE;
    temperature_sensor.max_limit = UPPER_LIMIT_TEMPERATURE;
    temperature_sensor.min_limit = LOWER_LIMIT_TEMPERATURE;
    temperature_sensor.frequency = TEMPERATURES_SENSOR_INTERVAL;
    temperature_sensor.readings_size = NUM_TEMPERATURE_REGISTERS;
    temperature_sensor.units = "ºC";

    generate_base_temp_values(base_temperatures, temperature_sensor.readings_size);
    int total_errors = 0;
    unsigned short *temperatures = (unsigned short *)malloc(sizeof(unsigned short) * temperature_sensor.readings_size);

    do
    {
      char last_temp_read = TEMP_BASE_VALUE;
      for (int i = 0; i < temperature_sensor.readings_size; i++)
      {
        last_temp_read = sens_temp(last_temp_read, pcg32_random_r());
        char base_temp_read = (i == 0 ? TEMP_BASE_VALUE : base_temperatures[i - 1]);
        temperatures[i] = (unsigned short)(last_temp_read + base_temp_read) / 2;

        if (temperatures[i] > temperature_sensor.max_limit || temperatures[i] < temperature_sensor.min_limit)
          error_readings_temp[j][i] = 1;
        else
          error_readings_temp[j][i] = 0;
      }
      total_errors = get_total_errors(error_readings_temp[j], temperature_sensor.readings_size);
      printf("Temperatura > Sensor %d: %d erros\n", j + 1, total_errors);

      if (total_errors > MAX_INCORRECT_READS)
      {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);

    temperature_sensor.readings = temperatures;
    temp_sensors[j] = temperature_sensor;
    error_temp_sensors[j] = error_readings_temp[j];
  }

  char error_readings_vel[N_OF_VELOCITY_SENSORS][NUM_VEL_WIND_REGISTERS];
  for (int j = 0; j < n_of_sensors[VELOCITY_SENSORS_INDEX]; j++)
  {
    int total_errors = 0;
    Sensor vel_wind_sensor;
    vel_wind_sensor.id = 1; // TODO: generate id
    vel_wind_sensor.name = "Velocidade do Vento";
    vel_wind_sensor.sensor_type = VELOCITY_SENSOR_TYPE;
    vel_wind_sensor.max_limit = UPPER_LIMIT_VELOCITY;
    vel_wind_sensor.min_limit = LOWER_LIMIT_VELOCITY;
    vel_wind_sensor.frequency = VELOCITY_SENSOR_INTERVAL;
    vel_wind_sensor.readings_size = NUM_VEL_WIND_REGISTERS;
    vel_wind_sensor.units = "km/h";

    unsigned char last_read = pcg32_random_r() % 30;
    unsigned short *vel_wind = (unsigned short *)malloc(sizeof(unsigned short) * vel_wind_sensor.readings_size);

    do
    {
      last_read = pcg32_random_r() % 30; // TODO change to constants
      for (int i = 0; i < vel_wind_sensor.readings_size; i++)
      {
        last_read = sens_velc_vento(last_read, pcg32_random_r());
        vel_wind[i] = last_read;

        if (vel_wind[i] > vel_wind_sensor.max_limit || vel_wind[i] < vel_wind_sensor.min_limit)
          error_readings_vel[j][i] = 1;
        else
          error_readings_vel[j][i] = 0;
      }
      total_errors = get_total_errors(error_readings_vel[j], NUM_VEL_WIND_REGISTERS);
      printf("Velocidade Vento > Sensor %d: %d erros\n", j + 1, total_errors);

      if (total_errors > MAX_INCORRECT_READS)
      {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);

    vel_wind_sensor.readings = vel_wind;
    vel_wind_sensors[j] = vel_wind_sensor;
    error_vel_wind_sensors[j] = error_readings_vel[j];
  }

  char error_readings_dir[N_OF_DIRECTION_SENSORS][NUM_DIR_WIND_REGISTERS];
  unsigned short last_read_wind = pcg32_random_r() % 360; // TODO change to consntant

  for (int j = 0; j < N_OF_DIRECTION_SENSORS; j++)
  {
    Sensor dir_wind_sensor;
    dir_wind_sensor.id = 2; // TODO: generate id
    dir_wind_sensor.name = "Direção do Vento";
    dir_wind_sensor.sensor_type = DIRECTION_SENSOR_TYPE;
    dir_wind_sensor.max_limit = UPPER_LIMIT_DIR_WIND;
    dir_wind_sensor.min_limit = LOWER_LIMIT_DIR_WIND;
    dir_wind_sensor.frequency = DIRECTION_SENSOR_INTERVAL;
    dir_wind_sensor.readings_size = NUM_DIR_WIND_REGISTERS;
    dir_wind_sensor.units = "º";

    int total_errors = 0;
    unsigned short *dir_wind = (unsigned short *)malloc(sizeof(unsigned short) * dir_wind_sensor.readings_size);

    do
    {
      last_read_wind = pcg32_random_r() % 360; // TODO Change to constant
      for (int i = 0; i < dir_wind_sensor.readings_size; i++)
      {
        last_read_wind = sens_dir_vento(last_read_wind, pcg32_random_r());
        dir_wind[i] = last_read_wind;

        if (dir_wind[i] > dir_wind_sensor.max_limit || dir_wind[i] < dir_wind_sensor.min_limit)
          error_readings_dir[j][i] = 1;
        else
          error_readings_dir[j][i] = 0;
      }
      total_errors = get_total_errors(error_readings_dir[j], NUM_DIR_WIND_REGISTERS);
      printf("Direção Vento > Sensor %d: %d erros\n", j + 1, total_errors);

      if (total_errors > MAX_INCORRECT_READS)
      {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);

    dir_wind_sensor.readings = dir_wind;
    dir_wind_sensors[j] = dir_wind_sensor;
    error_dir_wind_sensors[j] = error_readings_dir[j];
  }

  char error_readings_pluvio[N_OF_PLUVIO_SENSORS][NUM_PLUVIO_REGISTERS];
  unsigned char last_temp_read = temp_sensors[0].readings[TEMPERATURES_SENSOR_INTERVAL / PLUVIO_SENSOR_INTERVAL];

  for (int j = 0; j < N_OF_PLUVIO_SENSORS; j++)
  {
    Sensor pluvio_sensor;
    pluvio_sensor.id = 3; // TODO: generate id
    pluvio_sensor.name = "Pluviosidade";
    pluvio_sensor.sensor_type = PLUVIO_SENSOR_TYPE;
    pluvio_sensor.max_limit = UPPER_LIMIT_PLUVIO;
    pluvio_sensor.min_limit = LOWER_LIMIT_PLUVIO;
    pluvio_sensor.frequency = PLUVIO_SENSOR_INTERVAL;
    pluvio_sensor.readings_size = NUM_PLUVIO_REGISTERS;
    pluvio_sensor.units = "mm";

    int total_errors = 0;

    unsigned short *pluvio = (unsigned short *)malloc(sizeof(unsigned short) * pluvio_sensor.readings_size);
    do
    {
      unsigned short last_read = pcg32_random_r() % 5; // TODO change to constant

      for (int i = 0; i < pluvio_sensor.readings_size; i++)
      {
        last_temp_read = temp_sensors[0].readings[i * (TEMPERATURES_SENSOR_INTERVAL / PLUVIO_SENSOR_INTERVAL)];
        last_read = (unsigned short)sens_pluvio(last_read, last_temp_read, pcg32_random_r());
        pluvio[i] = last_read;

        if (pluvio[i] > pluvio_sensor.max_limit || pluvio[i] < pluvio_sensor.min_limit)
          error_readings_pluvio[j][i] = 1;
        else
          error_readings_pluvio[j][i] = 0;
      }
      total_errors = get_total_errors(error_readings_pluvio[j], NUM_PLUVIO_REGISTERS);
      printf("Pluviosidade > Sensor %d: %d erros\n", j + 1, total_errors);

      if (total_errors > MAX_INCORRECT_READS)
      {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);

    pluvio_sensor.readings = pluvio;
    pluvio_sensors[j] = pluvio_sensor;
    error_pluvio_sensors[j] = error_readings_pluvio[j];
  }

  char error_readings_soil[N_OF_SOIL_HUMIDITY_SENSORS][NUM_SOIL_HUMIDITY_REGISTERS];
  unsigned char last_pluvio_read = pluvio_sensors[0].readings[(NUM_PLUVIO_REGISTERS / NUM_SOIL_HUMIDITY_REGISTERS)];

  for (int j = 0; j < N_OF_SOIL_HUMIDITY_SENSORS; j++)
  {
    Sensor soil_humidity_sensor;
    soil_humidity_sensor.id = 4; // TODO: generate id
    soil_humidity_sensor.name = "Humidade do Solo";
    soil_humidity_sensor.sensor_type = SOIL_HUMIDITY_SENSOR_TYPE;
    soil_humidity_sensor.max_limit = UPPER_LIMIT_SOIL_HUMIDITY;
    soil_humidity_sensor.min_limit = LOWER_LIMIT_SOIL_HUMIDITY;
    soil_humidity_sensor.frequency = SOIL_HUMIDITY_SENSOR_INTERVAL;
    soil_humidity_sensor.readings_size = NUM_SOIL_HUMIDITY_REGISTERS;
    soil_humidity_sensor.units = "%";

    unsigned short *soil_humidity = (unsigned short *)malloc(sizeof(unsigned short) * soil_humidity_sensor.readings_size);
    int total_errors = 0;
    unsigned short last_read = 10;

    do
    {
      for (int i = 0; i < soil_humidity_sensor.readings_size; i++)
      {
        last_pluvio_read = pluvio_sensors[0].readings[i * (NUM_PLUVIO_REGISTERS / NUM_SOIL_HUMIDITY_REGISTERS)];
        last_read = sens_humd_solo(last_read, last_pluvio_read, pcg32_random_r());
        soil_humidity[i] = (unsigned short)last_read;

        if (soil_humidity[i] > soil_humidity_sensor.max_limit || soil_humidity[i] < soil_humidity_sensor.min_limit)
          error_readings_soil[j][i] = 1;
        else
          error_readings_soil[j][i] = 0;
      }
      total_errors = get_total_errors(error_readings_soil[j], NUM_SOIL_HUMIDITY_REGISTERS);
      printf("Humidade Solo > Sensor %d: %d erros\n", j + 1, total_errors);

      if (total_errors > MAX_INCORRECT_READS)
      {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);

    soil_humidity_sensor.readings = soil_humidity;
    soil_humidity_sensors[j] = soil_humidity_sensor;
    error_soil_humidity_sensors[j] = error_readings_soil[j];
  }

  char error_readings_humd[N_OF_AIR_HUMIDITY_SENSORS][NUM_AIR_HUMIDITY_REGISTERS];
  last_pluvio_read = pluvio_sensors[0].readings[(NUM_PLUVIO_REGISTERS / NUM_SOIL_HUMIDITY_REGISTERS)];

  for (int j = 0; j < N_OF_AIR_HUMIDITY_SENSORS; j++)
  {
    Sensor air_humidity_sensor;
    air_humidity_sensor.id = 5; // TODO: generate id
    air_humidity_sensor.sensor_type = AIR_HUMIDITY_SENSOR_TYPE;
    air_humidity_sensor.max_limit = UPPER_LIMIT_AIR_HUMIDITY;
    air_humidity_sensor.min_limit = LOWER_LIMIT_AIR_HUMIDITY;
    air_humidity_sensor.frequency = AIR_HUMIDITY_SENSOR_INTERVAL;
    air_humidity_sensor.readings_size = NUM_AIR_HUMIDITY_REGISTERS;
    air_humidity_sensor.units = "%";

    unsigned short *air_humidity = (unsigned short *)malloc(sizeof(unsigned short) * air_humidity_sensor.readings_size);
    int total_errors = 0;
    unsigned short last_read = 10;

    do
    {
      for (int i = 0; i < air_humidity_sensor.readings_size; i++)
      {
        last_pluvio_read = pluvio_sensors[0].readings[i * (NUM_PLUVIO_REGISTERS / NUM_SOIL_HUMIDITY_REGISTERS)];
        last_read = sens_humd_atm(last_read, last_pluvio_read, pcg32_random_r());
        air_humidity[i] = (unsigned short)last_read;

        if (air_humidity[i] > air_humidity_sensor.max_limit || air_humidity[i] < air_humidity_sensor.min_limit)
          error_readings_humd[j][i] = 1;
        else
          error_readings_humd[j][i] = 0;
      }
      total_errors = get_total_errors(error_readings_humd[j], NUM_AIR_HUMIDITY_REGISTERS);
      printf("Humidade Ar > Sensor %d: %d erros\n", j + 1, total_errors);

      if (total_errors > MAX_INCORRECT_READS)
      {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);

    air_humidity_sensor.readings = air_humidity;
    air_humidity_sensors[j] = air_humidity_sensor;
    error_air_humidity_sensors[j] = error_readings_humd[j];
  }

  printf("\n-- Leituras dos sensores --\n\n");
  print_signed_result(data[TEMPERATURE_SENSORS_INDEX], NUM_TEMPERATURE_REGISTERS, "Temperatura", "ºC", N_OF_TEMP_SENSORS, errors[TEMPERATURE_SENSORS_INDEX]);
  printf("\n");

  for (int i = 1; i < NUM_OF_SENSORS; i++)
  {
    print_result(data[i], data[i]->readings_size, data[i]->name, data[i]->units, n_of_sensors[i], errors[i]);
    if (i < NUM_OF_SENSORS - 1) // fix to avoid printing extra new line in last iteration
      printf("\n");
  }

  return 0;
}
