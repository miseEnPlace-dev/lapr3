#include <stdio.h>

void print_result(int **arr, int size, char *name, char *units, int n_of_sensors, char **errors)
{
  printf("-- Registos %s: --\n", name);
  for (int j = 0; j < n_of_sensors; j++)
  {
    printf("\nSensor %d:\n", j + 1);
    for (int i = 0; i < size; i++)
      printf("Leitura: %d%s %s\n", arr[j][i], units, errors[j][i] == 1 ? "(Erro)" : "");
  }
}
