#include <stdio.h>

#include "sensores.h"
#include "../US101/random.h"

int main(void)
{
  printf("-- Leituras dos sensores --\n\n");

  printf("Sensor de temperatura:\n");
  printf("Leitura: %dºC\n", sens_temp(20, pcg32_random_r()));

  return 0;
}
