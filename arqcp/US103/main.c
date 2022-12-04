#include <stdio.h>
#include <stdint.h>

#include "sensores.h"
#include "random.h"
#include "generate_base_values.h"
#include "print_result.h"
#include "set_sensor_summary_register.h"

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

  if (N_OF_TEMP_SENSORS == 0 ||
      N_OF_TEMP_SENSORS != N_OF_PLUVIO_SENSORS ||
      N_OF_SOIL_HUMIDITY_SENSORS != N_OF_PLUVIO_SENSORS ||
      N_OF_AIR_HUMIDITY_SENSORS != N_OF_PLUVIO_SENSORS)
  {
    printf("Invalid number of sensors.\nMake sure the number of temperature, soil and air sensors are the same and different from 0.\n");
    return -1;
  }

  int **data[NUM_OF_SENSORS];

  int *temp_sensors[N_OF_TEMP_SENSORS];
  int *vel_wind_sensors[N_OF_VELOCITY_SENSORS];
  int *dir_wind_sensors[N_OF_DIRECTION_SENSORS];
  int *pluvio_sensors[N_OF_PLUVIO_SENSORS];
  int *soil_humidity_sensors[N_OF_SOIL_HUMIDITY_SENSORS];
  int *air_humidity_sensors[N_OF_AIR_HUMIDITY_SENSORS];

  data[TEMPERATURE_SENSORS_INDEX] = temp_sensors;
  data[VELOCITY_SENSORS_INDEX] = vel_wind_sensors;
  data[DIR_WIND_SENSORS_INDEX] = dir_wind_sensors;
  data[PLUVIO_SENSORS_INDEX] = pluvio_sensors;
  data[SOIL_HUMIDITY_SENSORS_INDEX] = soil_humidity_sensors;
  data[AIR_HUMIDITY_SENSORS_INDEX] = air_humidity_sensors;

  char base_temperatures[NUM_TEMPERATURE_REGISTERS];
  int temperatures[N_OF_TEMP_SENSORS][NUM_TEMPERATURE_REGISTERS];
  for (int j = 0; j < N_OF_TEMP_SENSORS; j++)
  {
    generate_base_temp_values(base_temperatures, NUM_TEMPERATURE_REGISTERS);

    char last_temp_read = TEMP_BASE_VALUE;
    for (int i = 0; i < NUM_TEMPERATURE_REGISTERS; i++)
    {
      last_temp_read = sens_temp(last_temp_read, pcg32_random_r());
      char base_temp_read = (i == 0 ? TEMP_BASE_VALUE : base_temperatures[i - 1]);
      temperatures[j][i] = (int)((last_temp_read + base_temp_read) / 2);
    }
    temp_sensors[j] = temperatures[j];
  }

  int vel_wind[N_OF_VELOCITY_SENSORS][NUM_VEL_WIND_REGISTERS];
  unsigned char last_read = pcg32_random_r() % 30;
  for (int j = 0; j < N_OF_VELOCITY_SENSORS; j++)
  {
    last_read = pcg32_random_r() % 30;

    for (int i = 0; i < NUM_VEL_WIND_REGISTERS; i++)
    {
      last_read = sens_velc_vento(last_read, pcg32_random_r());
      vel_wind[j][i] = (int)last_read;
    }
    vel_wind_sensors[j] = vel_wind[j];
  }

  int dir_wind[N_OF_DIRECTION_SENSORS][NUM_DIR_WIND_REGISTERS];
  unsigned short last_read_wind = pcg32_random_r() % 360;
  for (int j = 0; j < N_OF_DIRECTION_SENSORS; j++)
  {
    last_read_wind = pcg32_random_r() % 360;
    for (int i = 0; i < NUM_DIR_WIND_REGISTERS; i++)
    {
      last_read_wind = sens_dir_vento(last_read_wind, pcg32_random_r());
      dir_wind[j][i] = (int)last_read_wind;
    }
    dir_wind_sensors[j] = dir_wind[j];
  }

  int pluvio[N_OF_PLUVIO_SENSORS][NUM_PLUVIO_REGISTERS];
  unsigned char last_temp_read = temperatures[N_OF_TEMP_SENSORS - 1][(TEMPERATURES_SENSOR_INTERVAL / PLUVIO_SENSOR_INTERVAL)];
  for (int j = 0; j < N_OF_PLUVIO_SENSORS; j++)
  {
    last_read = pcg32_random_r() % 5;
    for (int i = 0; i < NUM_PLUVIO_REGISTERS; i++)
    {
      last_temp_read = temperatures[j][i * (TEMPERATURES_SENSOR_INTERVAL / PLUVIO_SENSOR_INTERVAL)];
      last_read = sens_pluvio(last_read, last_temp_read, pcg32_random_r());
      pluvio[j][i] = (int)last_read;
    }
    pluvio_sensors[j] = pluvio[j];
  }

  int soil_humidity[N_OF_SOIL_HUMIDITY_SENSORS][NUM_SOIL_HUMIDITY_REGISTERS];
  unsigned char last_pluvio_read = pluvio[N_OF_PLUVIO_SENSORS - 1][(NUM_PLUVIO_REGISTERS / NUM_SOIL_HUMIDITY_REGISTERS)];
  for (int j = 0; j < N_OF_SOIL_HUMIDITY_SENSORS; j++)
  {
    last_read = 10;
    for (int i = 0; i < NUM_SOIL_HUMIDITY_REGISTERS; i++)
    {
      last_pluvio_read = pluvio[j][i * (NUM_PLUVIO_REGISTERS / NUM_SOIL_HUMIDITY_REGISTERS)];
      last_read = sens_humd_solo(last_read, last_pluvio_read, pcg32_random_r());
      soil_humidity[j][i] = (int)last_read;
    }
    soil_humidity_sensors[j] = soil_humidity[j];
  }

  int air_humidity[N_OF_AIR_HUMIDITY_SENSORS][NUM_AIR_HUMIDITY_REGISTERS];
  last_pluvio_read = pluvio[N_OF_PLUVIO_SENSORS - 1][(NUM_PLUVIO_REGISTERS / NUM_SOIL_HUMIDITY_REGISTERS)];
  for (int j = 0; j < N_OF_AIR_HUMIDITY_SENSORS; j++)
  {
    last_read = 10;
    for (int i = 0; i < NUM_AIR_HUMIDITY_REGISTERS; i++)
    {
      last_pluvio_read = pluvio[j][i * (NUM_PLUVIO_REGISTERS / NUM_SOIL_HUMIDITY_REGISTERS)];
      last_read = sens_humd_atm(last_read, last_pluvio_read, pcg32_random_r());
      air_humidity[j][i] = (int)last_read;
    }
    air_humidity_sensors[j] = air_humidity[j];
  }

  printf("-- Leituras dos sensores --\n\n");
  print_result(data[TEMPERATURE_SENSORS_INDEX], NUM_TEMPERATURE_REGISTERS, "Temperatura", "ºC", N_OF_TEMP_SENSORS);
  printf("\n");
  print_result(data[VELOCITY_SENSORS_INDEX], NUM_VEL_WIND_REGISTERS, "Velocidade do Vento", "km/h", N_OF_VELOCITY_SENSORS);
  printf("\n");
  print_result(data[DIR_WIND_SENSORS_INDEX], NUM_DIR_WIND_REGISTERS, "Direção do Vento", "º", N_OF_DIRECTION_SENSORS);
  printf("\n");
  print_result(data[PLUVIO_SENSORS_INDEX], NUM_PLUVIO_REGISTERS, "Pluviosidade", "mm", N_OF_PLUVIO_SENSORS);
  printf("\n");
  print_result(data[SOIL_HUMIDITY_SENSORS_INDEX], NUM_SOIL_HUMIDITY_REGISTERS, "Humidade do Solo", "%", N_OF_SOIL_HUMIDITY_SENSORS);
  printf("\n");
  print_result(data[AIR_HUMIDITY_SENSORS_INDEX], NUM_AIR_HUMIDITY_REGISTERS, "Humidade do Ar", "%", N_OF_AIR_HUMIDITY_SENSORS);


  // US103 

  const int LINES = NUM_OF_SENSORS;
	const int COLUMNS = 3;
	int result[LINES][COLUMNS];
	
  set_sensor_summary_register(temperatures, N_OF_TEMP_SENSORS, NUM_TEMPERATURE_REGISTERS, *result);
  set_sensor_summary_register(vel_wind, N_OF_VELOCITY_SENSORS, NUM_VEL_WIND_REGISTERS, *(result + 1));
  set_sensor_summary_register(dir_wind, N_OF_DIRECTION_SENSORS, NUM_DIR_WIND_REGISTERS, *(result + 2));
  set_sensor_summary_register(pluvio, N_OF_PLUVIO_SENSORS, NUM_PLUVIO_REGISTERS, *(result + 3));
  set_sensor_summary_register(soil_humidity, N_OF_SOIL_HUMIDITY_SENSORS, NUM_SOIL_HUMIDITY_REGISTERS, *(result + 4));
  set_sensor_summary_register(air_humidity, N_OF_AIR_HUMIDITY_SENSORS, NUM_AIR_HUMIDITY_REGISTERS, *(result + 5));


  printf("        | Temperature | Wind Vel.   | Dir. Wind   | Pluvio.     | Soil Hum.   | Air Hum.    |");
  printf("\nMin     |");
  for(int i = 0; i<LINES; i++){
    printf(" %11d |", *(*(result + 0) + i));
  }
  printf("\nMax     |");
  for(int i = 0; i<LINES; i++){
    printf(" %11d |", *(*(result + 1) + i));
  }
  printf("\nAverage |");
  for(int i = 0; i<LINES; i++){
    printf(" %11d |", *(*(result + 2) + i));
  }

  return 0;
}
