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

//  printf("\nSensor de velocidade vento:\n");
 // for (int i = 0; i < 20; i++)
  //  printf("Leitura: %dkm\\h\n", sens_velc_vento(1, pcg32_random_r()));

  // printf("\nSensor de direção vento:\n");
  // for (int i = 0; i < 1000; i++)
  //   printf("Leitura: %dº\n", sens_dir_vento(1, i));

  // printf("\nSensor de pluviosidade:\n");
  // for (char i = 0; i < 1000; i++)
  // printf("Leitura: %dmm\n", sens_pluvio(2,20, i));

  char lastHumidityRead = 0;
  for(char i = 0; i < 100; i++)
  {
    if(i == 20) printf("--------\n");
    char random = (char)pcg32_random_r();
//    char lastPluvRead = random%20;
    char lastPluvRead = i > 20 ? 0 : 5;
    char pluvContribRate = 8; 
    char maxDiff = 3;
    char maxRainingDiff = 20;
    char pluvContrib = lastPluvRead*pluvContribRate * (random % (maxRainingDiff));

    if(lastPluvRead != 0 && pluvContrib < 0) pluvContrib = -pluvContrib; // module
    if(pluvContrib > maxRainingDiff) pluvContrib = maxRainingDiff;
    if(pluvContrib < -maxRainingDiff) pluvContrib = -maxRainingDiff;

    int result = 0;
    if(lastHumidityRead + pluvContrib + (random % (maxDiff+1)) >= 100){
      char inc = random%(maxDiff+1);
      if(inc < 0) inc = -inc; // module
      result = 100-inc;
    }
    else result = lastHumidityRead + pluvContrib + (random % (maxDiff+1));

    if(result < 0)result = 0;

    lastHumidityRead = result;
    printf("result = %d\n",result);
  }

  return 0;
}
