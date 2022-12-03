#include <stdint.h>
#include <stdio.h>

#include "generate_base_values.h"
#include "limits.h"
#include "print_result.h"
#include "random.h"
#include "sensores.h"

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

int main(void) {
    reset_seed();

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

    char error_readings_temp[NUM_TEMPERATURE_REGISTERS];
    char base_temperatures[NUM_TEMPERATURE_REGISTERS];
    int temperatures[N_OF_TEMP_SENSORS][NUM_TEMPERATURE_REGISTERS];
    for (int j = 0; j < N_OF_TEMP_SENSORS; j++) {
        generate_base_temp_values(base_temperatures, NUM_TEMPERATURE_REGISTERS);
        char last_temp_read = TEMP_BASE_VALUE;
        int total_errors = 0;

        do {
          for (int i = 0; i < NUM_TEMPERATURE_REGISTERS; i++) {
              last_temp_read = sens_temp(last_temp_read, pcg32_random_r());
              char base_temp_read = (i == 0 ? TEMP_BASE_VALUE : base_temperatures[i - 1]);
              temperatures[j][i] = (int)((last_temp_read + base_temp_read) / 2);

              if (temperatures[i] > UPPER_LIMIT_TEMPERATURE || temperatures[i] < LOWER_LIMIT_TEMPERATURE) {
                  error_readings_temp[i] = 1;
              } else error_readings_temp[i] = 0;
          }
          total_errors = get_total_errors(error_readings_temp, NUM_TEMPERATURE_REGISTERS);
          printf("Temperature > Sensor %d: %d errors\n", j + 1, total_errors);
        } while (total_errors > MAX_INCORRECT_READS);

        temp_sensors[j] = temperatures[j];
        error_temp_sensors[j] = error_readings_temp;
    }

    char error_readings_vel[NUM_VEL_WIND_REGISTERS];
    int vel_wind[N_OF_VELOCITY_SENSORS][NUM_VEL_WIND_REGISTERS];
    unsigned char last_read = pcg32_random_r() % 30;
    for (int j = 0; j < N_OF_VELOCITY_SENSORS; j++) {
        last_read = pcg32_random_r() % 30;
        int total_errors = 0;

        do {
          for (int i = 0; i < NUM_VEL_WIND_REGISTERS; i++) {
              last_read = sens_velc_vento(last_read, pcg32_random_r());
              vel_wind[j][i] = (int)last_read;

              if (vel_wind[i] > UPPER_LIMIT_VELOCITY || vel_wind[i] < LOWER_LIMIT_VELOCITY) {
                  error_readings_vel[i] = 1;
              } else error_readings_vel[i] = 0;
          }
          total_errors = get_total_errors(error_readings_temp, NUM_VEL_WIND_REGISTERS);
          printf("Wind Velocity > Sensor %d: %d errors\n", j + 1, total_errors);
        } while (total_errors > MAX_INCORRECT_READS);

        vel_wind_sensors[j] = vel_wind[j];
        error_vel_wind_sensors[j] = error_readings_vel;
    }

    char error_readings_dir[NUM_DIR_WIND_REGISTERS];
    int dir_wind[N_OF_DIRECTION_SENSORS][NUM_DIR_WIND_REGISTERS];
    unsigned short last_read_wind = pcg32_random_r() % 360;
    for (int j = 0; j < N_OF_DIRECTION_SENSORS; j++) {
        last_read_wind = pcg32_random_r() % 360;
        int total_errors = 0;

        do {
          for (int i = 0; i < NUM_DIR_WIND_REGISTERS; i++) {
              last_read_wind = sens_dir_vento(last_read_wind, pcg32_random_r());
              dir_wind[j][i] = (int)last_read_wind;

              if (dir_wind[i] > UPPER_LIMIT_DIR_WIND || dir_wind[i] < LOWER_LIMIT_DIR_WIND) {
                  error_readings_dir[i] = 1;
              } else error_readings_dir[i] = 0;
          }
          total_errors = get_total_errors(error_readings_temp, NUM_DIR_WIND_REGISTERS);
          printf("Wind Direction > Sensor %d: %d errors\n", j + 1, total_errors);
        } while (total_errors > MAX_INCORRECT_READS);

        dir_wind_sensors[j] = dir_wind[j];
        error_dir_wind_sensors[j] = error_readings_dir;
    }

    char error_readings_pluvio[NUM_PLUVIO_REGISTERS];
    int pluvio[N_OF_PLUVIO_SENSORS][NUM_PLUVIO_REGISTERS];
    unsigned char last_temp_read = temperatures[N_OF_TEMP_SENSORS - 1][(TEMPERATURES_SENSOR_INTERVAL / PLUVIO_SENSOR_INTERVAL)];
    for (int j = 0; j < N_OF_PLUVIO_SENSORS; j++) {
        last_read = pcg32_random_r() % 5;
        int total_errors = 0;

        do {
          for (int i = 0; i < NUM_PLUVIO_REGISTERS; i++) {
              last_temp_read = temperatures[j][i * (TEMPERATURES_SENSOR_INTERVAL / PLUVIO_SENSOR_INTERVAL)];
              last_read = sens_pluvio(last_read, last_temp_read, pcg32_random_r());
              pluvio[j][i] = (int)last_read;

              if (pluvio[i] > UPPER_LIMIT_PLUVIO || pluvio[i] < LOWER_LIMIT_PLUVIO) {
                  error_readings_pluvio[i] = 1;
              } else error_readings_pluvio[i] = 0;
          }
          total_errors = get_total_errors(error_readings_temp, NUM_PLUVIO_REGISTERS);
          printf("Pluviosity > Sensor %d: %d errors\n", j + 1, total_errors);
        } while (total_errors > MAX_INCORRECT_READS);

        pluvio_sensors[j] = pluvio[j];
        error_pluvio_sensors[j] = error_readings_pluvio;
    }

    char error_readings_soil[NUM_SOIL_HUMIDITY_REGISTERS];
    int soil_humidity[N_OF_SOIL_HUMIDITY_SENSORS][NUM_SOIL_HUMIDITY_REGISTERS];
    unsigned char last_pluvio_read = pluvio[N_OF_PLUVIO_SENSORS - 1][(NUM_PLUVIO_REGISTERS / NUM_SOIL_HUMIDITY_REGISTERS)];
    for (int j = 0; j < N_OF_SOIL_HUMIDITY_SENSORS; j++) {
        last_read = 10;
        int total_errors = 0;

        do {
          for (int i = 0; i < NUM_SOIL_HUMIDITY_REGISTERS; i++) {
              last_pluvio_read = pluvio[j][i * (NUM_PLUVIO_REGISTERS / NUM_SOIL_HUMIDITY_REGISTERS)];
              last_read = sens_humd_solo(last_read, last_pluvio_read, pcg32_random_r());
              soil_humidity[j][i] = (int)last_read;

              if (soil_humidity[i] > UPPER_LIMIT_SOIL_HUMIDITY || soil_humidity[i] < LOWER_LIMIT_SOIL_HUMIDITY) {
                  error_readings_soil[i] = 1;
              } else error_readings_soil[i] = 0;
          }
          total_errors = get_total_errors(error_readings_temp, NUM_SOIL_HUMIDITY_REGISTERS);
          printf("Soil Humidity > Sensor %d: %d errors\n", j + 1, total_errors);
        } while (total_errors > MAX_INCORRECT_READS);

        soil_humidity_sensors[j] = soil_humidity[j];
        error_soil_humidity_sensors[j] = error_readings_soil;
    }

    char error_readings_humd[NUM_AIR_HUMIDITY_REGISTERS];
    int air_humidity[N_OF_AIR_HUMIDITY_SENSORS][NUM_AIR_HUMIDITY_REGISTERS];
    last_pluvio_read = pluvio[N_OF_PLUVIO_SENSORS - 1][(NUM_PLUVIO_REGISTERS / NUM_SOIL_HUMIDITY_REGISTERS)];
    for (int j = 0; j < N_OF_AIR_HUMIDITY_SENSORS; j++) {
        last_read = 10;
        int total_errors = 0;

        do {
          for (int i = 0; i < NUM_AIR_HUMIDITY_REGISTERS; i++) {
              last_pluvio_read = pluvio[j][i * (NUM_PLUVIO_REGISTERS / NUM_SOIL_HUMIDITY_REGISTERS)];
              last_read = sens_humd_atm(last_read, last_pluvio_read, pcg32_random_r());
              air_humidity[j][i] = (int)last_read;

              if (air_humidity[i] > UPPER_LIMIT_AIR_HUMIDITY || air_humidity[i] < LOWER_LIMIT_AIR_HUMIDITY) {
                  error_readings_humd[i] = 1;
              } else error_readings_humd[i] = 0;
          }
          total_errors = get_total_errors(error_readings_temp, NUM_AIR_HUMIDITY_REGISTERS);
          printf("Air Humidity > Sensor %d: %d errors\n", j + 1, total_errors);
        } while (total_errors > MAX_INCORRECT_READS);
        air_humidity_sensors[j] = air_humidity[j];
        error_air_humidity_sensors[j] = error_readings_humd;
    }

    printf("\n-- Leituras dos sensores --\n\n");
    print_result(data[TEMPERATURE_SENSORS_INDEX], NUM_TEMPERATURE_REGISTERS, "Temperatura", "ºC", N_OF_TEMP_SENSORS, errors[TEMPERATURE_SENSORS_INDEX]);
    printf("\n");
    print_result(data[VELOCITY_SENSORS_INDEX], NUM_VEL_WIND_REGISTERS, "Velocidade do Vento", "km/h", N_OF_VELOCITY_SENSORS, errors[VELOCITY_SENSORS_INDEX]);
    printf("\n");
    print_result(data[DIR_WIND_SENSORS_INDEX], NUM_DIR_WIND_REGISTERS, "Direção do Vento", "º", N_OF_DIRECTION_SENSORS, errors[DIR_WIND_SENSORS_INDEX]);
    printf("\n");
    print_result(data[PLUVIO_SENSORS_INDEX], NUM_PLUVIO_REGISTERS, "Pluviosidade", "mm", N_OF_PLUVIO_SENSORS, errors[PLUVIO_SENSORS_INDEX]);
    printf("\n");
    print_result(data[SOIL_HUMIDITY_SENSORS_INDEX], NUM_SOIL_HUMIDITY_REGISTERS, "Humidade do Solo", "%", N_OF_SOIL_HUMIDITY_SENSORS, errors[SOIL_HUMIDITY_SENSORS_INDEX]);
    printf("\n");
    print_result(data[AIR_HUMIDITY_SENSORS_INDEX], NUM_AIR_HUMIDITY_REGISTERS, "Humidade do Ar", "%", N_OF_AIR_HUMIDITY_SENSORS, errors[AIR_HUMIDITY_SENSORS_INDEX]);

    return 0;
}
