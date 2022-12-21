#include <stdio.h>
#include <string.h>
#include "import_from_csv.h"

#define BUFFER_SIZE 1024

void import_from_csv(char *filename, sensor_n *sensors )
{
  char buf(BUFFER_SIZE);

  FILE *fp = fopen(filename, "r");

  if (fp == NULL)
  {
    printf("Error opening file!");
    exit(1);
  }

  while (fgets(buf, BUFFER_SIZE, fp) != NULL)
  {
    char *token = strtok(buf, ",");
    strcpy(sensors->name, token);
    token = strtok(NULL, ",");
    sensors->number_sensors = atoi(token);
  }

  fclose(fp);

}
