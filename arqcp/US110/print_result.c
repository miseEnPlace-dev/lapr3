#include <stdio.h>

#include "sensor.h"

void print_result(Sensor *arr, int size, char *name, char *units, int n_of_sensors, char **errors)
{
  printf("-- Registos %s: --\n", name);
  for (int j = 0; j < n_of_sensors; j++)
  {
    printf("\nSensor %d:\n", j + 1);
    for (int i = 0; i < size; i++)
      printf("Leitura: %u%s %s\n", arr[j].readings[i], units, errors[j][i] == 1 ? "(Erro)" : "");
  }
}

void print_signed_result(Sensor *arr, int size, char *name, char *units, int n_of_sensors, char **errors)
{
  printf("-- Registos %s: --\n", name);
  for (int j = 0; j < n_of_sensors; j++)
  {
    printf("\nSensor %d:\n", j + 1);
    for (int i = 0; i < size; i++)
      printf("Leitura: %d%s %s\n", (char)arr[j].readings[i], units, errors[j][i] == 1 ? "(Erro)" : "");
  }
}
