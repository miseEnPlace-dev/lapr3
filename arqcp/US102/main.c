#include <stdio.h>

#include "sens_temp.s";
#include "../US101/random.s";

int main(void)
{
  printf("-- Leituras dos sensores --\n\n");

  printf("Sensor de temperatura:\n");
  printf("Leitura: %dºC\n", sens_temps(20, pcg32_random_r()));

  return 0;
}
