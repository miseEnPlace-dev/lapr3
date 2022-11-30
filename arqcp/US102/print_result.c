#include <stdio.h>

void print_result(int *arr, int size, char *name, char *units)
{
  printf("Registos %s:\n", name);
  for (int i = 0; i < size; i++)
    printf("Leitura: %d%s\n", arr[i], units);
}

void print_result_matrix(int *matrix[6], int sizeTemp, int sizeVel, int sizeDir, int sizePluvio, int sizeSoil, int sizeAir)
{
  print_result(matrix[0], sizeTemp, "Temperatura", "ºC");
  printf("\n");
  print_result(matrix[1], sizeVel, "Velocidade do Vento", "m/s");
  printf("\n");
  print_result(matrix[2], sizeDir, "Direção do Vento", "º");
  printf("\n");
  print_result(matrix[3], sizePluvio, "Pluviometria", "mm");
  printf("\n");
  print_result(matrix[4], sizeSoil, "Humidade do Solo", "%");
  printf("\n");
  print_result(matrix[5], sizeAir, "Humidade do Ar", "%");
}
