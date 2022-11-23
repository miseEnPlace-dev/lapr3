#include <stdio.h>

#include "sensores.h"
#include "../US101/random.h"

int main(void)
{
  printf("-- Leituras dos sensores --\n\n");

  // printf("Sensor de temperatura:\n");
  // for (int i = 0; i < 1000; i++)
  //   printf("Leitura: %dºC\n", sens_temp(20, i));

  // printf("\nSensor de velocidade vento:\n");
  // for (int i = 0; i < 1000; i++)
  //   printf("Leitura: %dkm\\h\n", sens_velc_vento(1, i));

  // printf("\nSensor de direção vento:\n");
  // for (int i = 0; i < 1000; i++)
  //   printf("Leitura: %dº\n", sens_dir_vento(1, i));

  printf("\nSensor de pluviosidade:\n");
  for (char i = 0; i < 127; i++)
    printf("Leitura: %dmm\n", sens_pluvio(1,25,i));



  return 0;
}
