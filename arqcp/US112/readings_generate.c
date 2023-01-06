#include <stdio.h>

#include "sensor.h"
#include "generate_base_values.h"
#include "shared.h"
#include "random.h"
#include "sensors.h"
#include "limits.h"

void generate_temperature(Sensor current_sensor) {
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
      printf("%s > Sensor id %d: %d erros\n", current_sensor.name, current_sensor.id, total_errors);

      if (total_errors > MAX_INCORRECT_READS) {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);
}

void generate_general(Sensor current_sensor, int base_value, unsigned short (*sens_func)(unsigned short, short)) {
    int total_errors = 0;

    do { // retry on excess of errors
      unsigned short last_read = base_value;

      for (int i = 0; i < current_sensor.readings_size; i++) {
        // generate readings
        // sens_func: funtion pointer to the generator function
        last_read = sens_func(last_read, pcg32_random_r());
        current_sensor.readings[i] = last_read;

        current_sensor.errors[i] = exceeded_limits(i, current_sensor);
      }

      total_errors = get_total_errors(current_sensor);
      printf("%s > Sensor id %d: %d erros\n", current_sensor.name, current_sensor.id, total_errors);

      if (total_errors > MAX_INCORRECT_READS) {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);
}

void generate_dependant(Sensor current_sensor, Sensor dependant, int base_value, unsigned short (*sens_func)(unsigned short, unsigned short, short)) {
    int total_errors = 0;

    do { // retry on excess of errors
      unsigned short last_read = base_value;

      for (int i = 0; i < current_sensor.readings_size; i++) {
        // generate readings
        unsigned short last_dependant_read = dependant.readings[i * (dependant.frequency / current_sensor.frequency)];
        last_read = (unsigned short)sens_func(last_read, last_dependant_read, pcg32_random_r());
        current_sensor.readings[i] = last_read;

        current_sensor.errors[i] = exceeded_limits(i, current_sensor);
      }

      total_errors = get_total_errors(current_sensor);
      printf("%s > Sensor id %d: %d erros\n", current_sensor.name, current_sensor.id, total_errors);

      if (total_errors > MAX_INCORRECT_READS) {
        printf("Número máximo de erros permitidos excedido (%d). A reiniciar o sensor...\n", MAX_INCORRECT_READS);
        reset_seed();
      }
    } while (total_errors > MAX_INCORRECT_READS);
}

