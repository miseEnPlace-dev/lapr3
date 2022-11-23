#include <stdio.h>

#include "sensores.h"
#include <stdint.h>

uint64_t state = 0;
uint64_t inc = 0;

int main(void)
{

  state = get_value_from_dev_random();
	inc = get_value_from_dev_random();

  printf("-- Leituras dos sensores --\n\n");

/*printf("Sensor de temperatura:\n");
 for (int i = 0; i < 1000; i++)
   printf("Leitura: %dºC\n", sens_temp(20, pcg32_random_r()));*/

  printf("\nSensor de velocidade vento:\n");
  for (int i = 0; i < 20; i++)
    printf("Leitura: %dkm\\h\n", sens_velc_vento(1, pcg32_random_r()));

  // printf("\nSensor de direção vento:\n");
  // for (int i = 0; i < 1000; i++)
  //   printf("Leitura: %dº\n", sens_dir_vento(1, i));

  //printf("\nSensor de pluviosidade:\n");
  //for (char i = 0; i < 1000; i++)
    //printf("Leitura: %dmm\n", sens_pluvio(2,20, i));



  return 0;
}
