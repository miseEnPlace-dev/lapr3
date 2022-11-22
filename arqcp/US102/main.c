#include <stdio.h>

#include "sensores.h"
#include "../US101/random.h"

int main(void)
{
  printf("-- Leituras dos sensores --\n\n");

  printf("Sensor de temperatura:\n");
  for (int i = 0; i < 1000; i++) 
  printf("Leitura: %dºC\n", sens_temp(20, i));

  return 0;
}
