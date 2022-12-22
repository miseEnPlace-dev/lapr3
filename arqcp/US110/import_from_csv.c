#include <stdio.h>
#include <string.h>
//#include "import_from_csv.h"
#include "sensor.h"
#include "shared.h"

#define BUFFER_SIZE 1024

unsigned int* import_from_csv(char *filename, Sensor *sensors)
{
  unsigned int n_sensors[NUM_OF_SENSOR_TYPES];
  char buf[BUFFER_SIZE];

  FILE *fp = fopen(filename, "r");

  if (fp == NULL)
  {
    printf("Error opening file!");
    exit(1);
  }

  while (fgets(buf, BUFFER_SIZE, fp) != NULL)
  {
    char *token = strtok(buf, ",");
    int i = 0;

      n_sensors[i] = atoi(token);
      token = strtok(NULL, ",");
      i++;

      token = strtok(NULL, ",");
      sensors[i].frequency = atoi(token);


  }

  fclose(fp);

}

