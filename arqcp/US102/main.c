#include <stdio.h>

#include "sensores.h"
#include "../US101/random.h"

int main(void)
{
  printf("-- Leituras dos sensores --\n\n");

  printf("Sensor de temperatura:\n");
  for (int i = 0; i < 255; i++) 
    printf("Leitura: %dºC\n", sens_temp(20, i));

  for(int i = 0; i < 100; i++)
  {
    int lastPluvio = i;
    int lastHumd = 10;
    int randomComp = i;
    int maxVariation = 5;

    int result = (lastHumd+lastPluvio*(randomComp % (maxVariation+1)))%100;
    printf("result = %d\n",result);
  }

  return 0;
}
