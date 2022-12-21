#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

#include "generate_base_values.h"
#include "limits.h"
#include "print_result.h"
#include "random.h"
#include "sensores.h"
#include "sensor.h"
#include "shared.h"

uint64_t state = 0;
uint64_t inc = 0;

int main(void)
{
  reset_seed();

  Sensor *data[NUM_OF_SENSOR_TYPES];

  const unsigned char n_temp_sensors = N_OF_TEMP_SENSORS;                   // TODO: get this value from input (file or user)
  const unsigned char n_wind_velocity_sensors = N_OF_WIND_VELOCITY_SENSORS;      // TODO: get this value from input (file or user)
  const unsigned char n_wind_dir_sensors = N_OF_DIRECTION_SENSORS;          // TODO: get this value from input (file or user)
  const unsigned char n_pluvio_sensors = N_OF_PLUVIO_SENSORS;               // TODO: get this value from input (file or user)
  const unsigned char n_soil_humidity_sensors = N_OF_SOIL_HUMIDITY_SENSORS; // TODO: get this value from input (file or user)
  const unsigned char n_air_humidity_sensors = N_OF_AIR_HUMIDITY_SENSORS;   // TODO: get this value from input (file or user)

  if (n_temp_sensors == 0 || n_pluvio_sensors == 0) {
    printf("Número de sensores inválido.\nVerifique se o número de sensores de temperatura, humidade do solo e ar são iguais e diferentes de 0.\n");
    return -1;
  }

  // TODO: this array will come from import_config()
  unsigned int n_of_sensors[NUM_OF_SENSOR_TYPES] = {n_temp_sensors, n_wind_velocity_sensors, n_wind_dir_sensors, n_pluvio_sensors, n_soil_humidity_sensors, n_air_humidity_sensors};

  Sensor *temp_sensors = (Sensor *)malloc(n_temp_sensors * sizeof(Sensor));
  Sensor *wind_vel_sensors = (Sensor *)malloc(n_wind_velocity_sensors * sizeof(Sensor));
  Sensor *wind_dir_sensors = (Sensor *)malloc(n_wind_dir_sensors * sizeof(Sensor));
  Sensor *pluvio_sensors = (Sensor *)malloc(n_pluvio_sensors * sizeof(Sensor));
  Sensor *soil_humidity_sensors = (Sensor *)malloc(n_soil_humidity_sensors * sizeof(Sensor));
  Sensor *air_humidity_sensors = (Sensor *)malloc(n_air_humidity_sensors * sizeof(Sensor));

  data[TEMPERATURE_SENSOR_TYPE] = temp_sensors;
  data[WIND_VELOCITY_SENSOR_TYPE] = wind_vel_sensors;
  data[DIR_WIND_SENSOR_TYPE] = wind_dir_sensors;
  data[PLUVIO_SENSOR_TYPE] = pluvio_sensors;
  data[SOIL_HUMIDITY_SENSOR_TYPE] = soil_humidity_sensors;
  data[AIR_HUMIDITY_SENSOR_TYPE] = air_humidity_sensors;

  Sensor temperature_sensor;
  temperature_sensor.id = -1; // TODO: generate id
  temperature_sensor.name = "Temperatura";
  temperature_sensor.sensor_type = TEMPERATURE_SENSOR_TYPE;
  temperature_sensor.max_limit = UPPER_LIMIT_TEMPERATURE;
  temperature_sensor.min_limit = LOWER_LIMIT_TEMPERATURE;
  temperature_sensor.frequency = TEMPERATURES_SENSOR_INTERVAL;
  temperature_sensor.readings_size = NUM_TEMPERATURE_REGISTERS;
  temperature_sensor.units = "ºC";

  Sensor wind_vel_sensor;
  wind_vel_sensor.id = -1; // TODO: generate id
  wind_vel_sensor.name = "Velocidade do Vento";
  wind_vel_sensor.sensor_type = WIND_VELOCITY_SENSOR_TYPE;
  wind_vel_sensor.max_limit = UPPER_LIMIT_WIND_VELOCITY;
  wind_vel_sensor.min_limit = LOWER_LIMIT_WIND_VELOCITY;
  wind_vel_sensor.frequency = WIND_VELOCITY_SENSOR_INTERVAL;
  wind_vel_sensor.readings_size = NUM_VEL_WIND_REGISTERS;
  wind_vel_sensor.units = "km/h";

  Sensor wind_dir_sensor;
  wind_dir_sensor.id = -1; // TODO: generate id
  wind_dir_sensor.name = "Direção do Vento";
  wind_dir_sensor.sensor_type = DIR_WIND_SENSOR_TYPE;
  wind_dir_sensor.max_limit = UPPER_LIMIT_DIR_WIND;
  wind_dir_sensor.min_limit = LOWER_LIMIT_DIR_WIND;
  wind_dir_sensor.frequency = DIRECTION_SENSOR_INTERVAL;
  wind_dir_sensor.readings_size = NUM_DIR_WIND_REGISTERS;
  wind_dir_sensor.units = "º";

  Sensor pluvio_sensor;
  pluvio_sensor.id = -1; // TODO: generate id
  pluvio_sensor.name = "Pluviosidade";
  pluvio_sensor.sensor_type = PLUVIO_SENSOR_TYPE;
  pluvio_sensor.max_limit = UPPER_LIMIT_PLUVIO;
  pluvio_sensor.min_limit = LOWER_LIMIT_PLUVIO;
  pluvio_sensor.frequency = PLUVIO_SENSOR_INTERVAL;
  pluvio_sensor.readings_size = NUM_PLUVIO_REGISTERS;
  pluvio_sensor.units = "mm";

  Sensor soil_humidity_sensor;
  soil_humidity_sensor.id = -1; // TODO: generate id
  soil_humidity_sensor.name = "Humidade do Solo";
  soil_humidity_sensor.sensor_type = SOIL_HUMIDITY_SENSOR_TYPE;
  soil_humidity_sensor.max_limit = UPPER_LIMIT_SOIL_HUMIDITY;
  soil_humidity_sensor.min_limit = LOWER_LIMIT_SOIL_HUMIDITY;
  soil_humidity_sensor.frequency = SOIL_HUMIDITY_SENSOR_INTERVAL;
  soil_humidity_sensor.readings_size = NUM_SOIL_HUMIDITY_REGISTERS;
  soil_humidity_sensor.units = "%";

  Sensor air_humidity_sensor;
  air_humidity_sensor.id = -1; // TODO: generate id
  air_humidity_sensor.name = "Humidade do Ar";
  air_humidity_sensor.sensor_type = AIR_HUMIDITY_SENSOR_TYPE;
  air_humidity_sensor.max_limit = UPPER_LIMIT_AIR_HUMIDITY;
  air_humidity_sensor.min_limit = LOWER_LIMIT_AIR_HUMIDITY;
  air_humidity_sensor.frequency = AIR_HUMIDITY_SENSOR_INTERVAL;
  air_humidity_sensor.readings_size = NUM_AIR_HUMIDITY_REGISTERS;
  air_humidity_sensor.units = "%";

  // Temperature sensors
  char base_temperatures[temperature_sensor.readings_size];

  for (int j = 0; j < n_temp_sensors; j++)
  {
    temperature_sensor.id = j; // TODO: generate id

    generate_base_temp_values(base_temperatures, temperature_sensor.readings_size);
    int total_errors = 0;
    unsigned short *temperatures = (unsigned short *)malloc(sizeof(unsigned short) * temperature_sensor.readings_size);
    unsigned char *errors = (unsigned char *)malloc(sizeof(unsigned char) * temperature_sensor.readings_size);

    do
    {
      char last_temp_read = TEMP_BASE_VALUE;
      for (int i = 0; i < temperature_sensor.readings_size; i++)
      {
        last_temp_read = sens_temp(last_temp_read, pcg32_random_r());
        char base_temp_read = (i == 0 ? TEMP_BASE_VALUE : base_temperatures[i - 1]);
        temperatures[i] = (unsigned short)(last_temp_read + base_temp_read) / 2;

        if (temperatures[i] > temperature_sensor.max_limit || temperatures[i] < temperature_sensor.min_limit)
          errors[i] = 1;
        else
          errors[i] = 0;
      }

      total_errors = get_total_errors(errors, temperature_sensor.readings_size);
      printf("%s > Sensor %d: %d erros\n", temperature_sensor.name, j + 1, total_errors);

      if (total_errors > MAX_INCORRECT_READS)
      {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);

    temperature_sensor.readings = temperatures;
    temperature_sensor.errors = errors;
    temp_sensors[j] = temperature_sensor;
  }

  // Wind velocity sensors
  for (int j = 0; j < n_wind_velocity_sensors; j++)
  {
    int total_errors = 0;

    wind_vel_sensor.id = j; // TODO: generate id

    unsigned short *wind_vel = (unsigned short *)malloc(sizeof(unsigned short) * wind_vel_sensor.readings_size);
    unsigned char *errors = (unsigned char *)malloc(sizeof(unsigned char) * wind_vel_sensor.readings_size);

    do
    {
      unsigned char last_read = pcg32_random_r() % 30; // TODO change to constants
      for (int i = 0; i < wind_vel_sensor.readings_size; i++)
      {
        last_read = sens_velc_vento(last_read, pcg32_random_r());
        wind_vel[i] = last_read;

        if (wind_vel[i] > wind_vel_sensor.max_limit || wind_vel[i] < wind_vel_sensor.min_limit)
          errors[i] = 1;
        else
          errors[i] = 0;
      }
      total_errors = get_total_errors(errors, wind_vel_sensor.readings_size);
      printf("%s > Sensor %d: %d erros\n", errors, j + 1, total_errors);

      if (total_errors > MAX_INCORRECT_READS)
      {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);

    wind_vel_sensor.readings = wind_vel;
    wind_vel_sensor.errors = errors;
    wind_vel_sensors[j] = wind_vel_sensor;
  }

  // Wind direction sensors
  for (int j = 0; j < n_wind_dir_sensors; j++)
  {
    wind_dir_sensor.id = j; // TODO: generate id

    int total_errors = 0;
    unsigned short *wind_dir = (unsigned short *)malloc(sizeof(unsigned short) * wind_dir_sensor.readings_size);
    unsigned char *errors = (unsigned char *)malloc(sizeof(unsigned char) * wind_dir_sensor.readings_size);

    do
    {
      unsigned short last_read_wind = pcg32_random_r() % 360; // TODO Change to constant
      for (int i = 0; i < wind_dir_sensor.readings_size; i++)
      {
        last_read_wind = sens_dir_vento(last_read_wind, pcg32_random_r());
        wind_dir[i] = last_read_wind;

        if (wind_dir[i] > wind_dir_sensor.max_limit || wind_dir[i] < wind_dir_sensor.min_limit)
          errors[i] = 1;
        else
          errors[i] = 0;
      }
      total_errors = get_total_errors(errors, wind_dir_sensor.readings_size);
      printf("%s > Sensor %d: %d erros\n", wind_dir_sensor.name, j + 1, total_errors);

      if (total_errors > MAX_INCORRECT_READS)
      {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);

    wind_dir_sensor.readings = wind_dir;
    wind_dir_sensor.errors = errors;
    wind_dir_sensors[j] = wind_dir_sensor;
  }

  // Pluviosity sensors
  for (int j = 0; j < n_pluvio_sensors; j++)
  {
    pluvio_sensor.id = j; // TODO: generate id

    int total_errors = 0;
    unsigned short *pluvio = (unsigned short *)malloc(sizeof(unsigned short) * pluvio_sensor.readings_size);
    unsigned char *errors = (unsigned char *)malloc(sizeof(unsigned char) * pluvio_sensor.readings_size);

    do
    {
      unsigned short last_read = pcg32_random_r() % 5; // TODO change to constant

      for (int i = 0; i < pluvio_sensor.readings_size; i++)
      {
        unsigned char last_temp_read = temp_sensors[0].readings[i * (temperature_sensor.frequency / pluvio_sensor.frequency)];
        last_read = (unsigned short)sens_pluvio(last_read, last_temp_read, pcg32_random_r());
        pluvio[i] = last_read;

        if (pluvio[i] > pluvio_sensor.max_limit || pluvio[i] < pluvio_sensor.min_limit)
          errors[i] = 1;
        else
          errors[i] = 0;
      }
      total_errors = get_total_errors(errors, pluvio_sensor.readings_size);
      printf("%s > Sensor %d: %d erros\n", pluvio_sensor.name, j + 1, total_errors);

      if (total_errors > MAX_INCORRECT_READS)
      {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);

    pluvio_sensor.readings = pluvio;
    pluvio_sensor.errors = errors;
    pluvio_sensors[j] = pluvio_sensor;
  }

  // Soil humidity sensors
  for (int j = 0; j < n_soil_humidity_sensors; j++)
  {
    soil_humidity_sensor.id = j; // TODO: generate id

    unsigned short *soil_humidity = (unsigned short *)malloc(sizeof(unsigned short) * soil_humidity_sensor.readings_size);
    unsigned char *errors = (unsigned char *)malloc(sizeof(unsigned char) * soil_humidity_sensor.readings_size);
    int total_errors = 0;
    unsigned short last_read = 10;

    do
    {
      for (int i = 0; i < soil_humidity_sensor.readings_size; i++)
      {
        unsigned char last_pluvio_read = pluvio_sensors[0].readings[i * (pluvio_sensor.readings_size / soil_humidity_sensor.readings_size)];
        last_read = sens_humd_solo(last_read, last_pluvio_read, pcg32_random_r());
        soil_humidity[i] = (unsigned short)last_read;

        if (soil_humidity[i] > soil_humidity_sensor.max_limit || soil_humidity[i] < soil_humidity_sensor.min_limit)
          errors[i] = 1;
        else
          errors[i] = 0;
      }
      total_errors = get_total_errors(errors, soil_humidity_sensor.readings_size);
      printf("%s > Sensor %d: %d erros\n", soil_humidity_sensor.name, j + 1, total_errors);

      if (total_errors > MAX_INCORRECT_READS)
      {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);

    soil_humidity_sensor.readings = soil_humidity;
    soil_humidity_sensor.errors = errors;
    soil_humidity_sensors[j] = soil_humidity_sensor;
  }

  // Air humidity sensors
  for (int j = 0; j < N_OF_AIR_HUMIDITY_SENSORS; j++)
  {
    air_humidity_sensor.id = j; // TODO: generate id

    unsigned short *air_humidity = (unsigned short *)malloc(sizeof(unsigned short) * air_humidity_sensor.readings_size);
    unsigned char *errors = (unsigned char *)malloc(sizeof(unsigned char) * air_humidity_sensor.readings_size);
    int total_errors = 0;
    unsigned short last_read = 10;

    do
    {
      for (int i = 0; i < air_humidity_sensor.readings_size; i++)
      {
        unsigned char last_pluvio_read = pluvio_sensors[0].readings[i * (pluvio_sensor.readings_size / soil_humidity_sensor.readings_size)];
        last_read = sens_humd_atm(last_read, last_pluvio_read, pcg32_random_r());
        air_humidity[i] = (unsigned short)last_read;

        if (air_humidity[i] > air_humidity_sensor.max_limit || air_humidity[i] < air_humidity_sensor.min_limit)
          errors[i] = 1;
        else
          errors[i] = 0;
      }
      total_errors = get_total_errors(errors, air_humidity_sensor.readings_size);
      printf("%s > Sensor %d: %d erros\n", air_humidity_sensor.name, j + 1, total_errors);

      if (total_errors > MAX_INCORRECT_READS)
      {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);

    air_humidity_sensor.readings = air_humidity;
    air_humidity_sensor.errors = errors;
    air_humidity_sensors[j] = air_humidity_sensor;
  }

  printf("\n-- Leituras dos sensores --\n\n");
  print_signed_result(data[TEMPERATURE_SENSOR_TYPE], n_temp_sensors);
  printf("\n");

  for (int i = 1; i < NUM_OF_SENSOR_TYPES; i++)
  {
    print_result(data[i], n_of_sensors[i]);
    if (i < NUM_OF_SENSOR_TYPES - 1) // fix to avoid printing extra new line in last iteration
      printf("\n");
  }

  return 0;
}

