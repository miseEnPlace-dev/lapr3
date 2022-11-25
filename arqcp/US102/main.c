#include <stdio.h>
#include <stdint.h>

#include "sensores.h"
#include "random.h"
#include "generate_base_values.h"

#define SEC_IN_DAY 86400
#define NUM_TEMP_VALUES 50

uint64_t state = 0;
uint64_t inc = 0;

int main(void)
{
  state = get_value_from_dev_random();
  inc = get_value_from_dev_random();

  char base_temp_values[NUM_TEMP_VALUES];
  generate_base_temp_values(base_temp_values,NUM_TEMP_VALUES);

  char temperatues[SEC_IN_DAY/TEMPERATUES_SENSOR_INTERVAL];
  char vel_wind[SEC_IN_DAY/VELOCITY_SENSOR_INTERVAL];
  char dir_wind[SEC_IN_DAY/DIRECTION_SENSOR_INTERVAL];
  char pluvio[SEC_IN_DAY/PLUVIOSITY_SENSOR_INTERVAL];
  char soil_humd[SEC_IN_DAY/SOIL_HUMIDITY_SENSOR_INTERVAL];
  char air_humd[SEC_IN_DAY/AIR_HUMIDITY_SENSOR_INTERVAL];

  printf("-- Leituras dos sensores --\n\n");

  //for(int i = 0; i < NUM_TEMP_VALUES; i++)
  //printf("base_temp_values[%d]=%d\n",i,base_temp_values[i]);

  printf("Sensor de temperatura:\n");
  char lastReadTemp = 10;
  for (int i = 0; i < 100; i++) {
      char result = sens_temp(lastReadTemp, pcg32_random_r());
      temperatues[i] = result;
      printf("Leitura: %dºC\n", result);
      lastReadTemp = result;
  }

  printf("\nSensor de velocidade vento:\n");
  unsigned char lastReadVel = 1;
  for (int i = 0; i < 100; i++) {
    unsigned char result = sens_velc_vento(lastReadVel, pcg32_random_r());
    vel_wind[i] = result;
    printf("Leitura: %dkm\\h\n", result);
    lastReadVel = result;
  }

  printf("\nSensor de direção vento:\n");
  unsigned char lastReadDir = 1;
  for (int i = 0; i < 100; i++) {
    unsigned char result = sens_dir_vento(lastReadDir, pcg32_random_r());
    dir_wind[i] = result;
    printf("Leitura: %dº\n", result);
    lastReadDir = result;
  }

  printf("\nSensor de pluviosidade:\n");
  unsigned char lastReadPluvio = 2;
  for (int i = 0; i < 100; i++) {
    unsigned char result = sens_pluvio(lastReadPluvio,20, pcg32_random_r());
    pluvio[i] = result;
    printf("Leitura: %dmm\n", result);
    lastReadPluvio = result;
  }

  printf("\nSensor de humidade Solo:\n");
  unsigned char lastReadSoil = 10;
  for (int i = 0; i < 100; i++) {
   unsigned char result = sens_humd_solo(lastReadSoil,1, pcg32_random_r());
   soil_humd[i] = result;
   printf("Leitura: %d%\n", result);
   lastReadSoil = result;
  }

  printf("\nSensor de humidade Ar:\n");
  unsigned char lastRead = 10;
  for (int i = 0; i < 100; i++) {
   unsigned char result = sens_humd_atm(lastRead,1, pcg32_random_r());
   air_humd[i] = result;
   printf("Leitura: %d%\n", result);
   lastRead = result;
  }
  return 0;
}
