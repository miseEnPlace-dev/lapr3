#include <stdio.h>

void print_result(char *arr, int size, char *name, char *units)
{
  printf("Registos %s:\n", name);
  for (int i = 0; i < size; i++)
    printf("Leitura: %d%s\n", arr[i], units);
}

void print_unsigned_result(unsigned char *arr, int size, char *name, char *units)
{
  printf("Registos %s:\n", name);
  for (int i = 0; i < size; i++)
    printf("Leitura: %u%s\n", (unsigned int)arr[i], units);
}
